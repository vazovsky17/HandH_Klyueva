package app.vazovsky.lesson_7_klyueva.data.remote

import app.vazovsky.lesson_7_klyueva.data.model.Bridge
import retrofit2.http.GET

interface BridgeApiService {
    @GET("bridges")
    suspend fun getBridges(): List<Bridge>
}