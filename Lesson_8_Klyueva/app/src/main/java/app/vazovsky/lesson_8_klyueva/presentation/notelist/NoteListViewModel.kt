package app.vazovsky.lesson_8_klyueva.presentation.notelist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.vazovsky.lesson_8_klyueva.data.db.DatabaseClient
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import app.vazovsky.lesson_8_klyueva.data.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoteListViewModel : ViewModel() {
    private val _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State> = _stateLiveData

    private val _notesLiveData = MutableLiveData<List<NoteEntity>>()
    val notesLiveData: LiveData<List<NoteEntity>> = _notesLiveData

    fun loadNotes(context: Context) {
        viewModelScope.launch {
            try {
                _stateLiveData.postValue(State.Loading())
                val notes = DatabaseClient.getInstance(context).getNotes()
                _stateLiveData.postValue(State.Data(notes))
            } catch (e: Exception) {
                _stateLiveData.postValue(State.Error(e))
            }
        }
    }

    fun subscribeToNotes(context: Context) {
        viewModelScope.launch {
            DatabaseClient.getInstance(context).getNotes().collect {
                _notesLiveData.postValue(it)
            }
        }
    }

    fun insert(context: Context, note: NoteEntity) {
        viewModelScope.launch {
            DatabaseClient.getInstance(context).insertNote(note)
        }
    }

    fun delete(context: Context, note: NoteEntity) {
        viewModelScope.launch {
            DatabaseClient.getInstance(context).deleteNote(note)
        }
    }
}