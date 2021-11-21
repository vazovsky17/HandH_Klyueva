package app.vazovsky.lesson_9_klyueva.presentation

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_9_klyueva.DownloadService
import app.vazovsky.lesson_9_klyueva.DownloadService.Companion.DATA_PROGRESS
import app.vazovsky.lesson_9_klyueva.DownloadService.Companion.EXTRA_IMAGE
import app.vazovsky.lesson_9_klyueva.DownloadService.Companion.EXTRA_URL
import app.vazovsky.lesson_9_klyueva.R
import app.vazovsky.lesson_9_klyueva.ServiceCallbacks
import app.vazovsky.lesson_9_klyueva.WeatherService
import app.vazovsky.lesson_9_klyueva.WeatherService.WeatherBinder
import app.vazovsky.lesson_9_klyueva.data.model.WeatherResponse
import app.vazovsky.lesson_9_klyueva.databinding.ActivityMainBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import java.io.File

class MainActivity : AppCompatActivity(), ServiceCallbacks {

    companion object {
        const val ACTION_DOWNLOAD = "app.vazovsky.lesson_9_klyueva.ACTION_DOWNLOAD"
        const val ACTION_IMAGE = "app.vazovsky.lesson_9_klyueva.ACTION_IMAGE"
    }

    private val binding by viewBinding(ActivityMainBinding::bind)
    private var bound = false
    private val weatherConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bound = true
            weatherBinder = service as WeatherBinder
            weatherService = weatherBinder?.service
            weatherService?.setCallbacks(this@MainActivity)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
            weatherBinder = null
        }
    }
    private val weatherIntent by lazy { WeatherService.createStartIntent(this) }
    private var weatherBinder: WeatherBinder? = null
    private var weatherService: WeatherService? = null

    private val downloadIntent by lazy { DownloadService.createStartIntent(this) }
    private val progressBar by lazy { binding.progressBar }
    private val imageView by lazy { binding.imageViewPicture }
    private val buttonStartService by lazy { binding.buttonStartService }

    private val downloadReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val progress = intent?.getIntExtra(DATA_PROGRESS, 0) ?: 0
            progressBar.progress = progress
            buttonStartService.text = "$progress %"
            if (progress == 100) {
                progressBar.visibility = View.GONE
                buttonStartService.text = "Запуск"
            } else {
                progressBar.visibility = View.VISIBLE
            }
        }

    }
    private val imageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("LOL", "Картинка тут ")
            val path = intent?.getStringExtra(EXTRA_IMAGE)
            Log.d("LOL", path!!)
            val imgFile = File(path!!)
            if (imgFile.exists()) {
                val bitmap  = BitmapFactory.decodeFile(imgFile.absolutePath)
                imageView.setImageBitmap(bitmap)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        bindService(weatherIntent, weatherConnection, BIND_AUTO_CREATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.buttonStartService.setOnClickListener {
            registerReceiver(downloadReceiver, IntentFilter(ACTION_DOWNLOAD))
            registerReceiver(imageReceiver, IntentFilter(ACTION_IMAGE))
            progressBar.visibility = View.GONE
            progressBar.progress = 0
            downloadIntent.apply {
                putExtra(EXTRA_URL, "https://vk.com/doc450021022_620099497")
            }
            startService(downloadIntent)
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            weatherService?.setCallbacks(null)
            unbindService(weatherConnection)
            bound = false
        }
    }

    override fun setWeather(weatherResponse: WeatherResponse) {
        runOnUiThread {
            binding.textViewWeather.text = buildString {
                clear()
                val main = weatherResponse.main!!
                append("Температура: ${main.temp}\n")
                append("Ощущается: ${main.feelsLike}\n")
                append("Минимальная температура: ${main.tempMin}\n")
                append("Максимальная температура: ${main.tempMax}\n")
                append("Давление: ${main.pressure}\n")
                append("Влажность: ${main.humidity}")
            }
        }
    }

    override fun onDestroy() {
        unregisterReceiver(downloadReceiver)
        super.onDestroy()
    }

}