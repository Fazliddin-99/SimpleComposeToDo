package com.example.composemvvmtodo.di

import android.app.Application
import androidx.room.Room
import com.example.composemvvmtodo.data.ToDoDatabase
import com.example.composemvvmtodo.data.ToDoRepositoryImpl
import com.example.composemvvmtodo.data.ToDoReposityory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideToDoDatabase(app: Application): ToDoDatabase {
        return Room.databaseBuilder(
            app, ToDoDatabase::class.java, "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideToDoRepository(db: ToDoDatabase): ToDoReposityory {
        return ToDoRepositoryImpl(db.dao)
    }
}