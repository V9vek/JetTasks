package com.vivek.jettasks.presentation.ui.edit

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vivek.jettasks.presentation.MainActivity
import com.vivek.jettasks.presentation.components.EditDateChip
import com.vivek.jettasks.presentation.components.EditDetails
import com.vivek.jettasks.presentation.components.TextField
import com.vivek.jettasks.presentation.components.TopBar
import com.vivek.jettasks.presentation.navigation.Screen.ToolbarAction
import com.vivek.jettasks.utils.DateUtils
import dagger.hilt.android.EntryPointAccessors

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun EditTaskScreen(
    viewModel: EditTaskViewModel,
    onBackPress: () -> Unit
) {
    val selectedTask by viewModel.selectedTask.collectAsState(initial = null)

    val taskName = viewModel.taskTodo.value
    val taskDetails = viewModel.taskDetails.value
    val taskDateAdded = viewModel.taskDate.value

    Scaffold(
        topBar = {
            TopBar(
                onActionClick = { action ->
                    when (action) {
                        ToolbarAction.Complete -> viewModel.updateTaskOnCompletion(task = selectedTask)
                        ToolbarAction.Delete -> viewModel.deleteTask()
                    }
                    onBackPress()
                },
                onBackPress = {
                    viewModel.updateTask()
                    onBackPress()
                }
            )
        }
    ) { innerPadding ->

        AnimatedVisibility(visible = selectedTask != null) {
            selectedTask?.let { task ->
                if (taskName.isEmpty()) {       // setting selected task to text fields only for first time
                    viewModel.onTodoChange(todo = task.name)
                    viewModel.onDetailsChange(details = task.details)
                    viewModel.onDateChange(date = task.dateAdded)
                }

                Column(Modifier.padding(innerPadding)) {
                    TextField(
                        modifier = Modifier.padding(top = 24.dp, start = 24.dp),
                        input = taskName,
                        onInputChange = {
                            viewModel.onTodoChange(todo = it)
                        },
                        placeholder = "Enter Title",
                        fontSize = 22,
                        textColor = colors.onSurface,
                        visible = true
                    )

                    EditDetails(
                        input = taskDetails,
                        onInputChange = {
                            viewModel.onDetailsChange(details = it)
                        }
                    )

                    EditDateChip(
                        showDateChip = taskDateAdded != 0L,
                        onTextFieldClick = {
                            viewModel.onDateChange(DateUtils.getCurrentDate())
                        },
                        onCrossClick = {
                            viewModel.onDateChange(date = 0L)
                        }
                    )
                }
            }
        }
    }

    BackHandler(onBack = {
        viewModel.updateTask()
        onBackPress()
    })
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun provideEditTaskViewModel(taskId: Int): EditTaskViewModel {
    // creating assisted factory
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).editTaskViewModelFactory()

    return viewModel(factory = EditTaskViewModel.provideFactory(factory, taskId))
}













