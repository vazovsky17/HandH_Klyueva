package app.vazovsky.lesson_8_klyueva.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.vazovsky.lesson_8_klyueva.data.db.dao.NoteDao

@Entity(tableName = NoteDao.TABLE_NAME)
data class NoteEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String
)