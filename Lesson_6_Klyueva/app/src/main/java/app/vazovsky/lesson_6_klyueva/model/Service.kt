package app.vazovsky.lesson_6_klyueva.model

sealed class Service(
    open val title: String,
    open val code: String,
    open val icon: Int,
    open val info: String,
    open val desc: String,
    open var isWarning: Boolean = false
)
