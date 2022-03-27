package app.oways.mykloud.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.oways.mykloud.data.local.AppDatabase
import app.oways.mykloud.data.local.doa.ToDoDAO
import app.oways.mykloud.data.local.entity.ToDoEntity
import app.oways.mykloud.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ToDoDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: ToDoDAO


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.todoDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertToDoItem() = runBlockingTest {
        val item = ToDoEntity(1, "1", "name1", 80, createdAt = System.currentTimeMillis())
        dao.insetToDo(item)
        val allShoppingItems = dao.getAllTodo().getOrAwaitValue()
        assertThat(allShoppingItems).contains(item)
    }

    @Test
    fun deleteToDoItem() = runBlockingTest {
        val item = ToDoEntity(2, "2", "name2", 70, createdAt = System.currentTimeMillis())
        dao.insetToDo(item)
        dao.deleteToDoById(item.id!!)

        val allShoppingItems = dao.getAllTodo().getOrAwaitValue()
        assertThat(allShoppingItems).doesNotContain(item)
    }

    @Test
    fun insertToDoList() = runBlockingTest {
        val item1 = ToDoEntity(1, "1", "name1", 10, createdAt = System.currentTimeMillis())
        val item2 = ToDoEntity(2, "2", "name2", 80, createdAt = System.currentTimeMillis())
        val item3 = ToDoEntity(3, "3", "name3", 40, createdAt = System.currentTimeMillis())
        val list = ArrayList<ToDoEntity>()
        list.add(item1)
        list.add(item2)
        list.add(item3)
        dao.insertToDoList(list)
        val allShoppingItems = dao.getAllTodo().getOrAwaitValue()
        assertThat(allShoppingItems).contains(item1)
        assertThat(allShoppingItems).contains(item2)
        assertThat(allShoppingItems).contains(item3)
    }

    @Test
    fun deleteAllToDoList() = runBlockingTest {
        val item = ToDoEntity(2, "2", "name2", 70, createdAt = System.currentTimeMillis())
        dao.insetToDo(item)
        dao.deleteAllToDoList()

        val allShoppingItems = dao.getAllTodo().getOrAwaitValue()
        assertThat(allShoppingItems).isEmpty()
    }

}