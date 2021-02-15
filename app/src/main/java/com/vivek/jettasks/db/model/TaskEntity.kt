package com.vivek.jettasks.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val details: String,
    val dateCreated: Long = System.currentTimeMillis(),
    val dateAdded: Long = 0L,
    val dateCompleted: Long = 0L,
    val completed: Boolean = false
)