package app.vazovsky.lesson_12_klyueva.data.remote

import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("bridges")
    suspend fun getBridges(): List<Bridge>

    @GET("bridges/{id}")
    suspend fun getBridgeDetail(@Path("id") id: Int): Bridge
}