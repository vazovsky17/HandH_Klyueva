package app.vazovsky.lesson_9_klyueva

import app.vazovsky.lesson_9_klyueva.data.model.WeatherResponse

interface ServiceCallbacks {
    fun setWeather(weatherResponse: WeatherResponse)
    fun setImage()
}