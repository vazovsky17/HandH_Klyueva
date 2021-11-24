package app.vazovsky.lesson_10_klyueva.data

import app.vazovsky.lesson_10_klyueva.data.model.Bridge

sealed class State {
    class Loading : State()
    class Data(val data: List<Bridge>) : State()
    class Error(val error: Exception) : State()
}