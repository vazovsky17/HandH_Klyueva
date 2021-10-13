package app.vazovsky.lesson_1_klyueva

import java.util.UUID

data class Student(
    var id: String,
    val name: String,
    val surname: String,
    val grade: String,
    val birthdayYear: String
) {
    override fun toString(): String {
        return "$id $name $surname $grade $birthdayYear"
    }
}

fun String.toStudent(): Student {
    val splitList = this.split(" ")
    return Student(
        id = UUID.randomUUID().mostSignificantBits.toString(),
        name = splitList[0],
        surname = splitList[1],
        grade = splitList[2],
        birthdayYear = splitList[3]
    )
}
