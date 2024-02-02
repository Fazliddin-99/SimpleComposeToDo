package com.example.composemvvmtodo.data

import kotlinx.coroutines.flow.Flow

interface ToDoReposityory {
    suspend fun insertToDo(toDo: ToDo)

    suspend fun deleteToDo(toDo: ToDo)

    suspend fun getToDo(id: Long): ToDo?

    fun getAllToDos(): Flow<List<ToDo>>
}