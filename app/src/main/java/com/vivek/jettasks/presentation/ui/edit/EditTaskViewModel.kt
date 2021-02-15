package com.vivek.jettasks.presentation.ui.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vivek.jettasks.db.TaskDao
import com.vivek.jettasks.db.model.TaskEntityMapper
import com.vivek.jettasks.domain.model.Task
import com.vivek.jettasks.utils.DateUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditTaskViewModel @AssistedInject constructor(
    private val dao: TaskDao,
    private val entityMapper: TaskEntityMapper,
    @Assisted private val taskId: Int
) : ViewModel() {

    val selectedTask: MutableStateFlow<Task?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            selectedTask.value = dao.getTask(taskId = taskId)
                .firstOrNull()?.let {
                    entityMapper.mapToDomainModel(it)
                }
        }
    }

    val taskTodo = mutableStateOf("")
    val taskDetails = mutableStateOf("")
    val taskDate = mutableStateOf(0L)

    fun onTodoChange(todo: String) {
        taskTodo.value = todo
    }

    fun onDetailsChange(details: String) {
        taskDetails.value = details
    }

    fun onDateChange(date: Long) {
        taskDate.value = date
    }

    fun deleteTask() = viewModelScope.launch {
        dao.deleteTask(id = taskId)
    }

    fun updateTask() = viewModelScope.launch {
        dao.updateTask(
            id = taskId,
            name = taskTodo.value,
            details = taskDetails.value,
            dateAdded = taskDate.value,
        )
    }

    fun updateTaskOnCompletion(task: Task?) = viewModelScope.launch {
        if (task == null) return@launch
        val taskEntity = entityMapper.mapFromDomainModel(
            task.copy(
                name = taskTodo.value,
                details = taskDetails.value,
                dateAdded = taskDate.value,
                dateCompleted = DateUtils.getCurrentDate(),
                completed = true
            )
        )
        dao.updateTaskOnCompletion(taskEntity)
    }

    // Assisted Injection : https://dagger.dev/dev-guide/assisted-injection.html
    // [AssistedInject] Integration with @HiltViewModel : https://github.com/google/dagger/issues/2287

    @AssistedFactory
    interface Factory {
        fun create(taskId: Int): EditTaskViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            taskId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(taskId) as T
            }
        }
    }
}













