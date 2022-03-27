package app.oways.mykloud.di

import app.oways.mykloud.data.local.doa.ToDoDAO
import app.oways.mykloud.repository.remote.ToDoRepository
import app.oways.mykloud.repository.remote.ToDoServiceApi
import app.oways.mykloud.repository.remote.ToDoOperations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRemoteNoteRepository(toDoDAO: ToDoDAO, toDoServiceApi: ToDoServiceApi): ToDoOperations {
        return ToDoRepository(toDoDAO, toDoServiceApi)
    }
}