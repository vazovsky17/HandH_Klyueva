package app.vazovsky.lesson_10_klyueva.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BridgeApi {
    private const val BASE_URL = "http://gdemost.handh.ru:1235/"

    val apiService: BridgeApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(BridgeApiService::class.java)
}