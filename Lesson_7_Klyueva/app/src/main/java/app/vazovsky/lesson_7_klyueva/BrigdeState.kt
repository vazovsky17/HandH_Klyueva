package app.vazovsky.lesson_7_klyueva

import app.vazovsky.lesson_7_klyueva.data.model.Bridge

sealed class BrigdeState {
    class Loading : BrigdeState()
    class Data(val data: List<Bridge>) : BrigdeState()
    class Error(val error: Exception) : BrigdeState()
}

