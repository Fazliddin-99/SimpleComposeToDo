package com.example.composemvvmtodo.ui.todo_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composemvvmtodo.data.ToDo

@Composable
fun ToDoItem(
    toDo: ToDo, onEvent: (ToDoListEvent) -> Unit, modifier: Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween) {
            Row {
                Text(text = toDo.title, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { onEvent(ToDoListEvent.DeleteToDo(toDo)) }) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = "Delete"
                    )
                }
            }
            Text(text = toDo.description)
        }

        Checkbox(checked = toDo.isDone, onCheckedChange = {
            onEvent(ToDoListEvent.OnDoneChange(toDo, it))
        })
    }
}