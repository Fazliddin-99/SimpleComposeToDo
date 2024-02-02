package com.example.composemvvmtodo.data

import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(private val dao: ToDoDao) : ToDoReposityory {
    override suspend fun insertToDo(toDo: ToDo) {
        dao.insertToDo(toDo)
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        dao.deleteToDo(toDo)
    }

    override suspend fun getToDo(id: Long): ToDo? {
        return dao.getToDo(id)
    }

    override fun getAllToDos(): Flow<List<ToDo>> {
        return dao.getAllToDos()
    }
}