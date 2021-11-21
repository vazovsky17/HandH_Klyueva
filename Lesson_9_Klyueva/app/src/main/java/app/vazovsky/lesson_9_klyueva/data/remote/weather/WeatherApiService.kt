package app.vazovsky.lesson_9_klyueva.data.remote.weather

import app.vazovsky.lesson_9_klyueva.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") q: String? = "saratov",
        @Query("units") units: String? = "metric",
        @Query("appid") appId: String? = "a924f0f5b30839d1ecb95f0a6416a0c2"
    ): WeatherResponse
}

//http://api.openweathermap.org/data/2.5/weather?q=saratov&units=metric&appid=a924f0f5b30839d1ecb95f0a6416a0c2