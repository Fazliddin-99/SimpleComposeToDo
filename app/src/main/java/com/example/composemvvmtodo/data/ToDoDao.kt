package com.example.composemvvmtodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDo: ToDo)

    @Delete
    suspend fun deleteToDo(toDo: ToDo)

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getToDo(id: Long): ToDo?

    @Query("SELECT * FROM todo")
    fun getAllToDos(): Flow<List<ToDo>>
}