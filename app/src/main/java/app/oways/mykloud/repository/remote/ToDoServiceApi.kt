package app.oways.mykloud.repository.remote

import app.oways.mykloud.data.remote.ToDoItem
import retrofit2.Response
import retrofit2.http.*


interface ToDoServiceApi {

    @PUT("lists/{id}")
    suspend fun addToDo(@Path("id") id: String): Response<ToDoItem?>

    @DELETE("lists/{id}")
    suspend fun deleteToDo(@Path("id") id: String): Response<ToDoItem?>

    @GET("lists")
    suspend fun getToDoList(): Response<ArrayList<ToDoItem>?>

    @POST("lists")
    suspend fun getToDoListPost(): Response<ToDoItem?>

    @GET("lists/{id}")
    suspend fun getToDoById(@Path("id") id: String): Response<ToDoItem?>
}