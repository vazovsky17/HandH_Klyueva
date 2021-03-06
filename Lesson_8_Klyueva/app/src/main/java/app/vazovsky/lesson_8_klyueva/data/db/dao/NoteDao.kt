package app.vazovsky.lesson_8_klyueva.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    companion object {
        const val TABLE_NAME = "notes"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM $TABLE_NAME WHERE is_archive = 0")
    fun getNotes(): Flow<List<NoteEntity>>
}