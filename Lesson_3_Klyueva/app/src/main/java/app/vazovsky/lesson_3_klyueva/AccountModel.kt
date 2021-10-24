package app.vazovsky.lesson_3_klyueva

data class AccountModel(
    val name: String,
    val surname: String,
    val email: String,
    val login: String,
    val region: String,
    val cardNumber: String,
    val job: String
)

val account = AccountModel(
    name = "Марина",
    surname = "Клюева",
    email = "hecchuck17@gmail.com",
    login = "VA30VSKY1",
    region = "Саратов",
    cardNumber = "8833061",
    job = "Android Developer"
)