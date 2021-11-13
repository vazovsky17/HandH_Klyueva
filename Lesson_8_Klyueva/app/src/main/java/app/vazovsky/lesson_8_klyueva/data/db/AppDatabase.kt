package app.vazovsky.lesson_8_klyueva.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.vazovsky.lesson_8_klyueva.data.db.dao.NoteDao
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = AppDatabase.DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "app_database"
    }

    abstract fun getNoteDao(): NoteDao
}