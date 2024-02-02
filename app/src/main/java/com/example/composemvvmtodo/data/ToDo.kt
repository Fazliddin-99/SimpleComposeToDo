package com.example.composemvvmtodo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val description: String,
    val isDone: Boolean = false
)
