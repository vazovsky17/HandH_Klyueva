package app.vazovsky.lesson_8_klyueva.data.model

import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

sealed class State {
    class Loading : State()
    class Data(val data: List<NoteEntity>, val count :Int) : State()
    class Error(val error: Exception) : State()
}