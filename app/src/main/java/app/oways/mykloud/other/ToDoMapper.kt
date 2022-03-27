package app.oways.mykloud.other

import app.oways.mykloud.data.local.entity.ToDoEntity
import app.oways.mykloud.data.remote.ToDoItem

object ToDoMapper {
    fun transform(toDoList: ArrayList<ToDoItem>?): ArrayList<ToDoEntity> {
        val toDoEntityList = arrayListOf<ToDoEntity>()
        toDoList?.forEach { toDoItem ->
            toDoItem.run {
                toDoEntityList.add(
                    ToDoEntity(
                        id = id,
                        name = name,
                        priority = priority,
                        createdAt = createdAt
                    )
                )
            }
        }
        return toDoEntityList
    }

    fun transformItem(toDoItem: ToDoItem): ToDoEntity {

        var toDoEntity: ToDoEntity
        toDoItem.run {
            toDoEntity = ToDoEntity(
                id = id,
                name = name,
                priority = priority,
                createdAt = createdAt
            )

        }
        return toDoEntity
    }

}