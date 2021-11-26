package app.vazovsky.lesson_10_klyueva.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import app.vazovsky.lesson_10_klyueva.data.State
import app.vazovsky.lesson_10_klyueva.data.model.Bridge
import app.vazovsky.lesson_10_klyueva.databinding.ActivityMapsBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import app.vazovsky.lesson_10_klyueva.data.model.Bridge.Companion.STATE_LATE
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val STATE_LOADING = 0
        const val STATE_MAP = 1
        const val STATE_ERROR = 2
    }

    private val viewModel: MapsViewModel by viewModels()

    //Вьюхи
    private val binding by viewBinding(ActivityMapsBinding::bind)
    private val viewFlipper by lazy { binding.viewFlipper }
    private val textViewError by lazy { binding.textViewError }
    private val buttonUpdate by lazy { binding.buttonUpdate }

    //Карты, мосты, маркеры
    private var userMarker: Marker? = null
    private var map: GoogleMap? = null
    private var markers: Map<Marker, Bridge> = emptyMap()

    //Геолокация
    private val fusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy {
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5000L)
            .setFastestInterval(1000L)
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            updateUserMarker(result.lastLocation.latitude, result.lastLocation.longitude)
            fusedLocationProviderClient.removeLocationUpdates(this)
        }
    }
    private val locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            enableLocation()
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(this, "Нужны разрешения", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(app.vazovsky.lesson_10_klyueva.R.layout.activity_maps)
        configureMapFragment()
        viewModel.loadBridges()
        viewModel.stateLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> setStateLoading()
                is State.Data -> trySetData()
                is State.Error -> setStateError(state.error)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.uiSettings.apply {
            isMapToolbarEnabled = false
        }
        enableLocation()
        googleMap.setOnMarkerClickListener { marker ->
            markers[marker]?.let { configureBottomSheet(it) }
            true
        }
    }

    /**
     * Настройка BottomSheet
     */
    private fun configureBottomSheet(bridge: Bridge) = with(binding) {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.isHideable = false

        textViewBridgeTitle.text = bridge.name
        textViewBridgeDivorce.text = bridge.getDivorceString()
        textViewBridgeDescription.text = bridge.description
        imageViewBridgeState.setImageResource(bridge.getState())
        Glide.with(this@MapsActivity)
            .asBitmap()
            .load(if (bridge.getState() == STATE_LATE) bridge.photoCloseUrl else bridge.photoOpenUrl)
            .centerCrop()
            .into(imageViewBridgePicture)
    }

    /**
     * Настройка состояний загрузки мостов
     */
    private fun trySetData() {
        val data = (viewModel.stateLiveData.value as? State.Data)?.data
        val map = map
        if (data != null && map != null) {
            setStateData(data)
        }
    }

    private fun setStateLoading() {
        viewFlipper.displayedChild = STATE_LOADING
    }

    private fun setStateData(data: List<Bridge>) {
        if (data.isEmpty()) {
            viewFlipper.displayedChild = STATE_ERROR
            textViewError.text = "Пусто"
            buttonUpdate.setOnClickListener { viewModel.loadBridges() }
        } else {
            viewFlipper.displayedChild = STATE_MAP
            configureMarkers(data)
            configureMoveCameraBounds()
        }
    }

    private fun setStateError(error: Exception) {
        viewFlipper.displayedChild = STATE_ERROR
        textViewError.text = error.message
        buttonUpdate.setOnClickListener { viewModel.loadBridges() }
    }

    /**
     *
     */
    private fun configureMapFragment() {
        val mapFragment =
            supportFragmentManager.findFragmentById(app.vazovsky.lesson_10_klyueva.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun configureMarkers(bridges: List<Bridge>) {
        markers = bridges.map { bridge ->
            bridge.toMarker()!! to bridge
        }.toMap()
    }

    /**
    Настройка первоначальной камеры карты
     */
    private fun configureMoveCameraBounds() {
        val bounds = LatLngBounds.builder()
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.12).toInt()
        markers.forEach { bounds.include(it.key.position) }
        map?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), width, height, padding))
    }

    /**
    Преобразование моста в маркер
     */
    private fun Bridge.toMarker(): Marker? {
        return map?.addMarker(
            MarkerOptions()
                .position(getLatLng())
                .title(name)
                .icon(bitmapDescriptorFromVector(this@MapsActivity, getState()))
                .anchor(0.5F, 0.5F)
        )
    }

    /**
    Преобразование векторного изображения в BitmapDescriptor
     */

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun enableLocation() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    Log.d("LOCATION", "${location?.latitude} ${location?.longitude}")
                    if (location != null) {
                        updateUserMarker(location.latitude, location.longitude)
                    } else {
                        startLocationUpdates()
                    }
                }
            }
            else -> {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    private fun updateUserMarker(lat: Double, lng: Double) {
        Log.d("LOL", "updateUserMarker $lat $lng")
        userMarker?.remove()
        userMarker = map?.addMarker(MarkerOptions().position(LatLng(lat, lng)))
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }


}