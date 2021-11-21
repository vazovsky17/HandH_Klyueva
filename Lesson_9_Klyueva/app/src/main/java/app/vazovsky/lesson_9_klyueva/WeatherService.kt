package app.vazovsky.lesson_9_klyueva

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import app.vazovsky.lesson_9_klyueva.data.model.WeatherResponse
import app.vazovsky.lesson_9_klyueva.data.remote.weather.WeatherApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherService : Service() {

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, WeatherService::class.java)
        }
    }

    private var serviceCallbacks: ServiceCallbacks? = null
    private var weatherJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        weatherJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val weatherResponse = WeatherApi.apiService.getWeather()
                Log.d("LOL", weatherResponse.toString())
                setWeather(weatherResponse)
                delay(60000)
            }
        }
    }


    override fun onBind(intent: Intent?): IBinder {
        return WeatherBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        weatherJob?.cancel()
        super.onDestroy()
    }


    internal inner class WeatherBinder : Binder() {
        val service: WeatherService
            get() = this@WeatherService
    }

    fun setCallbacks(callbacks: ServiceCallbacks?) {
        serviceCallbacks = callbacks
    }

    private fun setWeather(weatherResponse: WeatherResponse) {
        Log.d("LOL", "setWeather")
        serviceCallbacks?.setWeather(weatherResponse)
    }
}