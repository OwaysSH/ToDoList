package app.oways.mykloud.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "index")
    val index: Int? = null,
    @ColumnInfo(name = "id")
    val id: String? = null,
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("priority")
    val priority: Int? = null,
    @ColumnInfo("createdAt")
    val createdAt: Long? = null
)
