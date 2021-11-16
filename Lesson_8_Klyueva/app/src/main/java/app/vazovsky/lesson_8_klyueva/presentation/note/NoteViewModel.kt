package app.vazovsky.lesson_8_klyueva.presentation.note

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.vazovsky.lesson_8_klyueva.data.db.DatabaseClient
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import kotlinx.coroutines.launch

class NoteViewModel : ViewModel() {

    fun insert(context: Context, note: NoteEntity) {
        viewModelScope.launch{
            DatabaseClient.getInstance(context).insertNote(note)
        }
    }
}