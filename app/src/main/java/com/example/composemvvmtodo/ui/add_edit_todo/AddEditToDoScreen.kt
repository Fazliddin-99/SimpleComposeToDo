package com.example.composemvvmtodo.ui.add_edit_todo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composemvvmtodo.util.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditToDoScreen(
    onPopBackStack: () -> Unit, viewModel: AddEditViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackstack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message, event.action)
                }

                else -> Unit
            }
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(AddEditToDoEvent.OnSaveToDoClick) }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        }) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = viewModel.title,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Title")
                }, onValueChange = {
                    viewModel.onEvent(AddEditToDoEvent.OnTitleChange(it))
                })
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = viewModel.description,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Description")
                },
                onValueChange = {
                    viewModel.onEvent(AddEditToDoEvent.OnDescriptionChange(it))
                },
                singleLine = false,
                maxLines = 5
            )
        }
    }
}