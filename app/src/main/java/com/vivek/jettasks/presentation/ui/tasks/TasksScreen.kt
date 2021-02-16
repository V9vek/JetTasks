package com.vivek.jettasks.presentation.ui.tasks

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vivek.jettasks.domain.model.Task
import com.vivek.jettasks.presentation.components.*
import kotlinx.coroutines.launch

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

    val currentCompletedTaskId = remember { mutableStateOf(-1) }
    val currentTaskIsCompleted = remember { mutableStateOf(false) }

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
            bottomBar = { BottomBar() },
            floatingActionButton = { Fab(onClick = { modalBottomSheetState.show() }) },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                LazyColumn {
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
                                currentCompletedTaskId.value = taskToBeCompleted.id
                                currentTaskIsCompleted.value = !taskToBeCompleted.completed
                                // showing snackbar
                                viewModel.snackbarController.getScope().launch {
                                    viewModel.snackbarController.showSnackbar(
                                        snackbarHostState = scaffoldState.snackbarHostState,
                                        message = "1 Task Completed",
                                        actionLabel = "Undo"
                                    )
                                }
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
                                        currentCompletedTaskId.value = taskToBeActive.id
                                        currentTaskIsCompleted.value = !taskToBeActive.completed
                                        // showing snackbar
                                        viewModel.snackbarController.getScope().launch {
                                            viewModel.snackbarController.showSnackbar(
                                                snackbarHostState = scaffoldState.snackbarHostState,
                                                message = "1 Task Active",
                                                actionLabel = "Undo"
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                FreshStart(visible = (activeTasks.isNullOrEmpty() && completedTasks.isNullOrEmpty()))

                DefaultSnackbar(
                    modifier = Modifier.align(Alignment.BottomCenter)
                        .padding(horizontal = 16.dp, vertical = 32.dp),
                    snackbarHostState = scaffoldState.snackbarHostState,
                    onUndo = {
                        viewModel.undoTask(
                            id = currentCompletedTaskId.value,
                            completed = currentTaskIsCompleted.value
                        )
                    }
                )
            }
        }
    }
}