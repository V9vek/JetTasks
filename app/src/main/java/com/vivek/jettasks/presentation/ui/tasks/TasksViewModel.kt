package com.vivek.jettasks.presentation.ui.tasks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.jettasks.db.TaskDao
import com.vivek.jettasks.db.model.TaskEntity
import com.vivek.jettasks.db.model.TaskEntityMapper
import com.vivek.jettasks.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val dao: TaskDao,
    private val entityMapper: TaskEntityMapper
) : ViewModel() {

    init {
        println("taskviewmodel init")
    }

    val arrowToggle = mutableStateOf(false)

    fun toggleArrow(showing: Boolean) {
        this.arrowToggle.value = showing
    }

    val taskTodo = mutableStateOf("")
    val taskDetails = mutableStateOf("")
    val taskDate = mutableStateOf(0L)
    val toggleTaskDetail = mutableStateOf(false)
    val toggleTaskDate = mutableStateOf(false)

    fun onTodoChange(todo: String) {
        taskTodo.value = todo
    }

    fun onDetailsChange(details: String) {
        taskDetails.value = details
    }

    fun onDateChange(date: Long) {
        taskDate.value = date
    }

    fun onToggleDetails(visible: Boolean) {
        toggleTaskDetail.value = visible
    }

    fun onToggleDate(visible: Boolean) {
        toggleTaskDate.value = visible
    }

    fun onClear() {
        taskTodo.value = ""
        taskDetails.value = ""
        taskDate.value = 0L
        toggleTaskDetail.value = false
        toggleTaskDate.value = false
    }

    val activeTasks = dao.getActiveTasks()
        .map { entityMapper.fromEntityList(it) }

    val completedTasks = dao.getCompletedTasks()
        .map { entityMapper.fromEntityList(it) }

    fun insertTask() = viewModelScope.launch {
        val task = TaskEntity(
            name = taskTodo.value,
            details = taskDetails.value,
            dateAdded = taskDate.value
        )
        dao.insertTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        val taskEntity = entityMapper.mapFromDomainModel(task)
        dao.updateTaskOnCompletion(taskEntity)
    }
}













