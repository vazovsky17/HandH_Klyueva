package app.vazovsky.lesson_12_klyueva.data.repository

import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.data.remote.ApiService
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getBridges(): List<Bridge> {
        return apiService.getBridges()
    }

    suspend fun getBridgeDetail(id: Int): Bridge {
        return apiService.getBridgeDetail(id)
    }
}