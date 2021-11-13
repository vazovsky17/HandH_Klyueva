package app.vazovsky.lesson_7_klyueva.data

import app.vazovsky.lesson_7_klyueva.data.model.Bridge

sealed class BridgeState {
    class Loading : BridgeState()
    class Data(val data: List<Bridge>) : BridgeState()
    class Error(val error: Exception) : BridgeState()
}

