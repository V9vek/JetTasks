package com.vivek.jettasks.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.vivek.jettasks.data.model.Task

class TasksViewModel : ViewModel() {

    val activeTasks = mutableStateListOf<Task>()
    val completedTasks = mutableStateListOf<Task>()

    fun addTask(task: Task) {
        activeTasks.add(task)
    }

    fun getTask(taskId: String): Task {
        return activeTasks.find { task ->
            task.id == taskId
        } ?: completedTasks.find { task ->
            task.id == taskId
        } ?: Task(todo = "")
    }

    fun completeTask(task: Task) {
        activeTasks.remove(task)
        completedTasks.add(task.copy(isCompleted = true))
    }

    fun activeTask(task: Task) {
        activeTasks.add(task.copy(isCompleted = false))
        completedTasks.remove(task)
    }

    fun updateTask(taskId: String, updatedTask: Task) {
        val task = getTask(taskId = taskId)
        if (task.isCompleted) {
            val index = completedTasks.indexOf(task)
            completedTasks[index].apply {
                todo = updatedTask.todo
                details = updatedTask.details
                date = updatedTask.date
            }
        } else {
            val index = activeTasks.indexOf(task)
            activeTasks[index].apply {
                todo = updatedTask.todo
                details = updatedTask.details
                date = updatedTask.date
            }
        }
    }

    fun deleteTask(taskId: String) {
        val task = getTask(taskId = taskId)
        if (task.isCompleted)
            completedTasks.remove(task)
        else
            activeTasks.remove(task)
    }
}













