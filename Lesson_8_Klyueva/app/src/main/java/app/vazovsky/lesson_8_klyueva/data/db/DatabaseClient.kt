package app.vazovsky.lesson_8_klyueva.data.db

import android.content.Context
import androidx.room.Room
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

class DatabaseClient private constructor(
    context: Context
) {
    companion object {
        private var instance: DatabaseClient? = null

        fun getInstance(context: Context): DatabaseClient {
            return instance ?: run {
                val newInstance = DatabaseClient(context)
                instance = newInstance
                newInstance
            }
        }
    }

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    ).build()

    suspend fun insertNote(note: NoteEntity) {
        db.getNoteDao().insertNote(note)
    }

    suspend fun deleteNote(note: NoteEntity){
        db.getNoteDao().deleteNote(note)
    }


    fun getNotes(): Flow<List<NoteEntity>> {
        return db.getNoteDao().getNotes()
    }
}