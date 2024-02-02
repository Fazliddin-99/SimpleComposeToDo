package com.example.composemvvmtodo.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemvvmtodo.data.ToDo
import com.example.composemvvmtodo.data.ToDoReposityory
import com.example.composemvvmtodo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    val repository: ToDoReposityory,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todo by mutableStateOf<ToDo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        val todoId = savedStateHandle.get<Long>("todoId")

        todoId?.let {
            if (it != -1L) {
                viewModelScope.launch(Dispatchers.IO) {
                    todo = repository.getToDo(it)
                    todo?.let { foundTodo ->
                        title = foundTodo.title
                        description = foundTodo.description
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditToDoEvent) {
        when (event) {
            is AddEditToDoEvent.OnTitleChange -> {
                title = event.title
            }

            is AddEditToDoEvent.OnDescriptionChange -> {
                description = event.description
            }

            is AddEditToDoEvent.OnSaveToDoClick -> {
                if (title.isNotBlank()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.insertToDo(
                            ToDo(
                                title = title,
                                description = description,
                                isDone = todo?.isDone ?: false,
                                id = todo?.id
                            )
                        )
                    }
                    sendUiEvent(UiEvent.PopBackstack)
                } else {
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Title can't be empty!"
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}