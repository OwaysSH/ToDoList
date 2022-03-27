package app.oways.mykloud.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.oways.mykloud.data.local.doa.ToDoDAO
import app.oways.mykloud.data.local.entity.ToDoEntity

@Database(
    entities = [
        ToDoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDAO(): ToDoDAO
}