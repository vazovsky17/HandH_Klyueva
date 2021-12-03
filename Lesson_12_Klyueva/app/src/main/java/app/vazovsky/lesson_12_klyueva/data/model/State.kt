package app.vazovsky.lesson_12_klyueva.data.model

import java.lang.Exception

sealed class State {
    object Loading : State()
    class Data<T>(val data: T) : State()
    class Error(val error: Exception) : State()
}