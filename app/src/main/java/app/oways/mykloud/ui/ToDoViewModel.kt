package app.oways.mykloud.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import app.oways.mykloud.data.local.entity.ToDoEntity
import app.oways.mykloud.data.remote.DataState
import app.oways.mykloud.data.remote.ToDoItem
import app.oways.mykloud.other.Event
import app.oways.mykloud.repository.remote.ToDoOperations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repository: ToDoOperations
) : ViewModel() {

    val toDoCount = repository.getToDoCount()

    private val _todoList = MutableLiveData<Event<DataState<ArrayList<ToDoItem>?>>>()
    val todoList: LiveData<Event<DataState<ArrayList<ToDoItem>?>>> = _todoList

    private val _toDoLiveData: MutableLiveData<DataState<Flow<PagingData<ToDoEntity>>>> =
        MutableLiveData()
    val toDoLiveData: LiveData<DataState<Flow<PagingData<ToDoEntity>>>> get() = _toDoLiveData


    fun insertToDoItem() {
        viewModelScope.launch {
            repository.addToDo()
            /* _todoItem.value = Event(response)
             getToDoList()*/
        }
    }

    fun removeToDoItem(id: String) {
        viewModelScope.launch {
            repository.deleteToDo(id)
        }
    }

    fun getToDoList() {
        viewModelScope.launch {
            val response = repository.getToDoList()
            response.collectLatest {
                _todoList.value = Event(it)
            }
        }
    }

    fun getLocalToDoList() {
        viewModelScope.launch {
            repository.getTodoPagingSource()
                .onEmpty {
                    _toDoLiveData.postValue(DataState.Empty)
                }
                .collectLatest {
                    _toDoLiveData.postValue(it)
                }
        }
    }
}