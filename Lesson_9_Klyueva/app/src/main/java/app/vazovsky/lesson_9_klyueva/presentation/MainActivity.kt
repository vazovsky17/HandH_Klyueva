package app.vazovsky.lesson_9_klyueva.presentation

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_9_klyueva.DownloadService
import app.vazovsky.lesson_9_klyueva.R
import app.vazovsky.lesson_9_klyueva.ServiceCallbacks
import app.vazovsky.lesson_9_klyueva.WeatherService
import app.vazovsky.lesson_9_klyueva.WeatherService.WeatherBinder
import app.vazovsky.lesson_9_klyueva.data.model.WeatherResponse
import app.vazovsky.lesson_9_klyueva.databinding.ActivityMainBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class MainActivity : AppCompatActivity(), ServiceCallbacks {


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

    override fun onStart() {
        super.onStart()
        bindService(weatherIntent, weatherConnection, BIND_AUTO_CREATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.buttonStartService.setOnClickListener {
            startService(downloadIntent)
//            startService()

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

    override fun setImage() {

    }
}