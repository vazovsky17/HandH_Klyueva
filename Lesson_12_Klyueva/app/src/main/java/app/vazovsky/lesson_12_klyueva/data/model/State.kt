package app.vazovsky.lesson_12_klyueva.data.model

import java.lang.Exception

sealed class State<T> {
    class Loading<T> : State<T>()
    class Data<T>(val data: T) : State<T>()
    class Error<T>(val error: Exception) : State<T>()
}