package app.oways.mykloud.reporitory

import androidx.lifecycle.MutableLiveData
import app.oways.mykloud.data.remote.DataState
import app.oways.mykloud.data.remote.ToDoDeleteResponse
import app.oways.mykloud.data.remote.ToDoItem
import app.oways.mykloud.data.ToDoResponse
import app.oways.mykloud.repository.remote.ToDoOperations
import kotlinx.coroutines.flow.Flow

class FakeToDoRepository: ToDoOperations {

    private val toDoItems = mutableListOf<ToDoItem>()
    private val observableToDoItems = MutableLiveData<List<ToDoItem>>(toDoItems)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableToDoItems.postValue(toDoItems)
    }

    override suspend fun addToDo(): DataState<ToDoItem?> {
        val toDoItem = ToDoItem(id = (toDoItems.size + 1).toString())
        toDoItems.add(toDoItem)
        refreshLiveData()
        return DataState.Success(toDoItem)
    }

    override suspend fun deleteToDo(id: String): DataState<ToDoDeleteResponse?> {
        TODO("Not yet implemented")
    }

    override suspend fun getToDoList(): Flow<DataState<ToDoResponse?>> {
        TODO("Not yet implemented")
    }

}

