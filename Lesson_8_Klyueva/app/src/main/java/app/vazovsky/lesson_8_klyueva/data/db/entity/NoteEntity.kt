package app.vazovsky.lesson_8_klyueva.data.db.entity

import android.graphics.Color
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.vazovsky.lesson_8_klyueva.data.db.dao.NoteDao
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = NoteDao.TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "color") var color: Int = Color.WHITE,
    @ColumnInfo(name = "is_archive") var isArchive: Boolean = false
) : Parcelable