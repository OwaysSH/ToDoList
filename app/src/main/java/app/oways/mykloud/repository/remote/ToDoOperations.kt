package app.oways.mykloud.repository.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import app.oways.mykloud.data.local.entity.ToDoEntity
import app.oways.mykloud.data.remote.DataState
import app.oways.mykloud.data.remote.ToDoItem
import kotlinx.coroutines.flow.Flow

interface ToDoOperations {

    suspend fun insertToDo(toDoEntity: ToDoEntity)

    suspend fun insertToDoList(list: ArrayList<ToDoEntity>)

    suspend fun deleteToDoById(id: String)

    fun getTodoPagingSource(): Flow<DataState<Flow<PagingData<ToDoEntity>>>>

    fun getToDoCount(): LiveData<Int>

    suspend fun addToDo(): DataState<ToDoItem?>

    suspend fun deleteToDo(id: String): Boolean/*DataState<ToDoDeleteResponse?>*/

    suspend fun getToDoList(): Flow<DataState<ArrayList<ToDoItem>?>>
}