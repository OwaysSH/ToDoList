package app.oways.mykloud.repository.remote

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.oways.mykloud.data.local.doa.ToDoDAO
import app.oways.mykloud.data.local.entity.ToDoEntity
import app.oways.mykloud.data.remote.DataState
import app.oways.mykloud.data.remote.ToDoItem
import app.oways.mykloud.other.ToDoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEmpty
import java.io.IOException


class ToDoRepository constructor(private val toDoDAO: ToDoDAO, private val serviceApi: ToDoServiceApi) :
    ToDoOperations {
    override suspend fun insertToDo(toDoEntity: ToDoEntity) {
      toDoDAO.insetToDo(toDoEntity)
    }

    override suspend fun insertToDoList(list: ArrayList<ToDoEntity>) {
        toDoDAO.insertToDoList(list)
    }

    override suspend fun deleteToDoById(id: String) {
        toDoDAO.deleteToDoById(id)
    }

    override fun getTodoPagingSource(): Flow<DataState<Flow<PagingData<ToDoEntity>>>> = flow {
        emit(DataState.Loading)
        emit(
            DataState.Success(
                Pager(
                    config = PagingConfig(pageSize = 10),
                    pagingSourceFactory = toDoDAO.getTodoListPagingSource().asPagingSourceFactory()
                ).flow.onEmpty {
                    emit(DataState.Empty)
                }
            )
        )
    }

    override fun getToDoCount(): LiveData<Int>  = toDoDAO.getToDoCount()

    override suspend fun addToDo(): DataState<ToDoItem?> {
        return try {
            val result = serviceApi.getToDoListPost()
            if (result.isSuccessful) {
                result.body()?.let { toDoItem->
                    insertToDo(ToDoMapper.transformItem(toDoItem))
                    DataState.Success(toDoItem)
                }?: DataState.Empty
            } else {
                DataState.Empty
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> DataState.NetworkError(throwable)
                else -> DataState.GenericError(throwable)
            }
        }
    }

    override suspend fun deleteToDo(id: String): Boolean {
        return try {
            val response = serviceApi.deleteToDo(id)
            if (response.isSuccessful) {
                deleteToDoById(id)
                response.body() != null
            } else
                false
        } catch (throwable: Throwable) {
            false
        }
    }

    override suspend fun getToDoList(): Flow<DataState<ArrayList<ToDoItem>?>> = flow {
        try {
            val result = serviceApi.getToDoList()
            if (result.isSuccessful) {
                result.body()?.let {
                    insertToDoList(ToDoMapper.transform(it))
                    emit(DataState.Success(result.body()))
                } ?: emit(DataState.Empty)
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> emit(DataState.NetworkError(throwable))
                else -> emit(DataState.GenericError(throwable))
            }
        }
    }

    /* override suspend fun addToDo(id: String): Flow<DataState<ToDoItem?>> = flow {
         try {
             val result = serviceApi.addToDo(id)
             if (result.isSuccessful) {
                 emit(DataState.Success(result.body(), result.code()))
             } else {
                 DataState.GenericError(Exception(message = "Failed to Add ToDo"))
             }
         } catch (throwable: Throwable) {
             emit(
                 when (throwable) {
                     is IOException -> DataState.NetworkError(throwable)
                     else -> DataState.GenericError(throwable)
                 }
             )
         }
     }

     override suspend fun deleteToDo(id: String): Flow<DataState<ToDoDeleteResponse?>> = flow {
         try {
             val result = serviceApi.deleteToDo(id)
             if (result.isSuccessful) {
                 result.body()?.apply {
                     todo?.let {
                         emit(DataState.Success(result.body(), result.code()))
                     }?:DataState.GenericError(Exception(message = "Already deleted"))
                 }
             } else {
                 DataState.GenericError(Exception(message = "Failed to Delete ToDo"))
             }
         } catch (throwable: Throwable) {
             emit(
                 when (throwable) {
                     is IOException -> DataState.NetworkError(throwable)
                     else -> DataState.GenericError(throwable)
                 }
             )
         }
     }

     override suspend fun getToDoList(): Flow<DataState<ToDoResponse?>> = flow {
         try {
             emit(DataState.Loading)
             val result = serviceApi.getToDoList()
             if (result.isSuccessful) {
                 result.body()?.let {
                     emit(DataState.Success(result.body(), result.code()))
                 } ?: emit(DataState.Empty)
             } else {
                 DataState.GenericError(Exception(message = "Failed to get ToDo List"))
             }
         } catch (throwable: Throwable) {
             emit(
                 when (throwable) {
                     is IOException -> DataState.NetworkError(throwable)
                     else -> DataState.GenericError(throwable)
                 }
             )
         }
     }

     override suspend fun getToDoListPost(): DataState<ToDoItem?> {
         try {
             val result = serviceApi.getToDoListPost()
             if (result.isSuccessful) {
                 result.body()?.let {
                     return DataState.Success(result.body(), result.code())
                 } ?: return (DataState.Empty)
             } else {
                 return DataState.GenericError(Exception(message = "Failed to get ToDo list"))
             }
         } catch (throwable: Throwable) {
             return (
                 when (throwable) {
                     is IOException -> DataState.NetworkError(throwable)
                     else -> DataState.GenericError(throwable)
                 }
             )
         }
     }


     override suspend fun getToDoById(id: String): Flow<DataState<ToDoItem?>> = flow {
         try {
             emit(DataState.Loading)
             val result = serviceApi.getToDoById(id)
             if (result.isSuccessful) {
                 result.body()?.let {
                     emit(DataState.Success(result.body(), result.code()))
                 } ?: emit(DataState.Empty)
             } else {
                 DataState.GenericError(Exception(message = "Failed to get ToDo by Id"))
             }
         } catch (throwable: Throwable) {
             emit(
                 when (throwable) {
                     is IOException -> DataState.NetworkError(throwable)
                     else -> DataState.GenericError(throwable)
                 }
             )
         }
     }*/
}


