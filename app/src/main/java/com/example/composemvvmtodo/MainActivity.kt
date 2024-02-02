package com.example.composemvvmtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composemvvmtodo.ui.add_edit_todo.AddEditToDoScreen
import com.example.composemvvmtodo.ui.theme.ComposeMVVMToDoTheme
import com.example.composemvvmtodo.ui.todo_list.ToDoListScreen
import com.example.composemvvmtodo.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeMVVMToDoTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Routes.TODO_LIST) {
                    composable(Routes.TODO_LIST) {
                        ToDoListScreen(onNavigate = {
                            navController.navigate(it.route)
                        })
                    }

                    composable(
                        route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                        arguments = listOf(navArgument(name = "todoId") {
                            type = NavType.LongType
                            defaultValue = -1L
                        })
                    ) {
                        AddEditToDoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeMVVMToDoTheme {
        Greeting("Android")
    }
}