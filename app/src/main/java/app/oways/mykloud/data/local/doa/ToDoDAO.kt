package app.oways.mykloud.data.local.doa

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.oways.mykloud.data.local.entity.ToDoEntity

@Dao
interface ToDoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetToDo(toDoEntity: ToDoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDoList(list: List<ToDoEntity>)

    @Query("Delete from todo where id =:todoId")
    suspend fun deleteToDoById(todoId: String)

    @Query("DELETE FROM todo")
    suspend fun deleteAllToDoList()

    @Query("SELECT * FROM todo")
    fun getAllTodo(): LiveData<List<ToDoEntity>?>

    @Query("SELECT * FROM todo ORDER BY createdAt DESC")
    fun getTodoListPagingSource(): DataSource.Factory<Int, ToDoEntity>

    @Query("SELECT count(*) FROM todo ")
    fun getToDoCount(): LiveData<Int>

}