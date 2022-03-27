package app.oways.mykloud.di

import android.app.Application
import androidx.room.Room
import app.oways.mykloud.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application) =
        Room.databaseBuilder(application, AppDatabase::class.java, "todo_db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesToDoDAO(appDatabase: AppDatabase) = appDatabase.todoDAO()

}