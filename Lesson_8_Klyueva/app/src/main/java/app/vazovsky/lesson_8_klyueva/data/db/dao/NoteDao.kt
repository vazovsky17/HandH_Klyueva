package app.vazovsky.lesson_8_klyueva.data.db.dao

import androidx.room.Dao
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: NoteEntity)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getNotes(): Flow<List<NoteEntity>>
}