package app.vazovsky.lesson_12_klyueva.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import app.vazovsky.lesson_12_klyueva.R
import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.data.model.Bridge.Companion.STATE_LATE
import app.vazovsky.lesson_12_klyueva.data.model.State
import app.vazovsky.lesson_12_klyueva.databinding.FragmentMapBinding
import app.vazovsky.lesson_12_klyueva.presentation.CustomViewFlipper
import app.vazovsky.lesson_12_klyueva.presentation.base.BaseFragment
import app.vazovsky.lesson_12_klyueva.presentation.detail.DetailFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MapFragment : BaseFragment(R.layout.fragment_map), OnMapReadyCallback {

    private val viewModel: MapViewModel by appViewModels()
    private val binding by viewBinding(FragmentMapBinding::bind)

    private val customViewFlipper by lazy { binding.customViewFlipper }

    private var map: GoogleMap? = null
    private var markers: Map<Marker, Bridge> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadBridges()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureMapFragment()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_listFragment)
        }
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            customViewFlipper.setState(state)
            if (customViewFlipper.displayedChild == CustomViewFlipper.STATE_DATA) {
                val data = (state as State.Data<List<Bridge>>).data
                val map = map
                if (map != null) {
                    configureMarkers(data)
                    configureMoveCameraBounds()
                }
            } else if (customViewFlipper.displayedChild == CustomViewFlipper.STATE_ERROR) {
                customViewFlipper.setOnErrorClickListener {
                    viewModel.loadBridges()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.uiSettings.apply {
            isMapToolbarEnabled = false
        }
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
        binding.customViewBridge.bindBridge(bridge)
        textViewBridgeDescription.text = bridge.description
        Glide.with(requireContext())
            .asBitmap()
            .load(if (bridge.getState() == STATE_LATE) bridge.photoCloseUrl else bridge.photoOpenUrl)
            .centerCrop()
            .into(imageViewBridgePicture)
    }

    /**
     * Настройка карты
     */
    private fun configureMapFragment() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun configureMarkers(bridges: List<Bridge>) {
        markers = bridges.map { bridge ->
            bridge.toMarker()!! to bridge
        }.toMap()
    }

    /**
     * Настройка первоначальной камеры карты
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
     * Преобразование моста в маркер
     */
    private fun Bridge.toMarker(): Marker? {
        return map?.addMarker(
            MarkerOptions()
                .position(getLatLng())
                .title(name)
                .icon(bitmapDescriptorFromVector(requireContext(), getState()))
                .anchor(0.5F, 0.5F)
        )
    }

    /**
     * Преобразование векторного изображения в BitmapDescriptor
     */
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

}