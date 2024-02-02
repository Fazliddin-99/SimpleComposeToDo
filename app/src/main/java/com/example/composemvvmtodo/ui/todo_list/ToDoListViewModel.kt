package com.example.composemvvmtodo.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemvvmtodo.data.ToDo
import com.example.composemvvmtodo.data.ToDoReposityory
import com.example.composemvvmtodo.util.Routes
import com.example.composemvvmtodo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val repository: ToDoReposityory
) : ViewModel() {
    val todos = repository.getAllToDos()
    private var deletedToDo: ToDo? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: ToDoListEvent) {
        when (event) {
            is ToDoListEvent.DeleteToDo -> {
                deletedToDo = event.todo
                viewModelScope.launch(Dispatchers.IO) {
                    repository.deleteToDo(event.todo)
                }

                sendUiEvent(UiEvent.ShowSnackbar("ToDo is deleted!", "Undo"))
            }

            is ToDoListEvent.OnAddToDoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }

            is ToDoListEvent.OnDoneChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.insertToDo(event.todo.copy(isDone = event.isDone))
                }
            }

            is ToDoListEvent.OnUndoDeleteClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deletedToDo?.let {
                        repository.insertToDo(it)
                    }
                }
            }

            is ToDoListEvent.OnToDoClick -> {
                sendUiEvent(UiEvent.Navigate("${Routes.ADD_EDIT_TODO}?todoId=${event.todo.id}"))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}