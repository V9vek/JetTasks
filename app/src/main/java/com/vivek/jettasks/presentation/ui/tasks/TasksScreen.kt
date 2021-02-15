package com.vivek.jettasks.presentation.ui.tasks

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vivek.jettasks.domain.model.Task
import com.vivek.jettasks.presentation.components.*

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TasksScreen(
    viewModel: TasksViewModel,
    onTaskClick: (task: Task) -> Unit
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()

    val activeTasks = viewModel.activeTasks.collectAsState(listOf()).value
    val completedTasks = viewModel.completedTasks.collectAsState(listOf()).value

    val arrowToggle = viewModel.arrowToggle.value
    val todoTask = viewModel.taskTodo.value
    val todoDetails = viewModel.taskDetails.value
    val taskDate = viewModel.taskDate.value
    val toggleDetails = viewModel.toggleTaskDetail.value
    val toggleDate = viewModel.toggleTaskDate.value

    // Back press handling if bottom sheet is open
    BackHandler(
        enabled = modalBottomSheetState.isVisible,
        onBack = { modalBottomSheetState.hide() }
    )

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        sheetElevation = 0.dp,
        scrimColor = MaterialTheme.colors.onSurface.copy(0.5f),
        sheetContent = {
            TaskInput(
                sheetState = modalBottomSheetState,
                taskTodo = todoTask,
                taskDetails = todoDetails,
                taskDate = taskDate,
                onClear = { viewModel.onClear() },
                onTodoChange = { viewModel.onTodoChange(it) },
                onDetailsChange = { viewModel.onDetailsChange(it) },
                onDateChange = { viewModel.onDateChange(it) },
                onSaveClick = { viewModel.insertTask() },
                showDetails = toggleDetails,
                showDate = toggleDate,
                onToggleShowDetails = { viewModel.onToggleDetails(it) },
                onToggleShowDate = { viewModel.onToggleDate(it) }
            )
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = { BottomBar() },
            floatingActionButton = { Fab(onClick = { modalBottomSheetState.show() }) },
            snackbarHost = {},
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
        ) { innerPadding ->

            FreshStart(visible = (activeTasks.isNullOrEmpty() && completedTasks.isNullOrEmpty()))

            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item { MyTasksTitle() }

                itemsIndexed(items = activeTasks) { index, task ->
                    TaskItem(
                        task = task,
                        modifier = Modifier.clickable(
                            onClick = { onTaskClick(task) },
                        ).indication(
                            interactionState = InteractionState(),
                            indication = null
                        ),
                        onTaskComplete = { taskToBeCompleted ->
                            viewModel.updateTask(taskToBeCompleted)
                        }
                    )
                }

                if (completedTasks.isNotEmpty()) {
                    item {
                        CompletedSubTitle(
                            completedTasksListSize = completedTasks.size,
                            arrowToggle = arrowToggle,
                            onArrowToggle = { viewModel.toggleArrow(it) }
                        )
                    }

                    if (arrowToggle) {
                        itemsIndexed(items = completedTasks) { index, task ->
                            TaskItem(
                                task = task,
                                modifier = Modifier.clickable(
                                    onClick = { onTaskClick(task) },
                                ).indication(
                                    interactionState = InteractionState(),
                                    indication = null
                                ),
                                onTaskComplete = { taskToBeActive ->
                                    viewModel.updateTask(task = taskToBeActive)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}