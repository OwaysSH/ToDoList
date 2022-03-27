package app.oways.mykloud.data.remote

import com.google.gson.annotations.SerializedName

data class ToDoItem(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("priority")
    val priority: Int? = 0,
    @SerializedName("createdAt")
    val createdAt: Long? = null
)