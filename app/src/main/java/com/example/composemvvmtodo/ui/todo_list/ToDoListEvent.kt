package com.example.composemvvmtodo.ui.todo_list

import com.example.composemvvmtodo.data.ToDo

sealed class ToDoListEvent {
    data class DeleteToDo(val todo: ToDo) : ToDoListEvent()
    data class OnDoneChange(val todo: ToDo, val isDone: Boolean) : ToDoListEvent()
    data class OnToDoClick(val todo: ToDo) : ToDoListEvent()
    object OnUndoDeleteClick : ToDoListEvent()
    object OnAddToDoClick : ToDoListEvent()
}
