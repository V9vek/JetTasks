package com.vivek.jettasks.ui.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.vivek.jettasks.data.model.Task
import com.vivek.jettasks.ui.components.DateChip
import com.vivek.jettasks.ui.screens.Routing
import com.vivek.jettasks.ui.theme.typography
import com.vivek.jettasks.viewmodels.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Routing.Main.Content(
    tasksViewModel: TasksViewModel,
    onTaskClick: (task: Task) -> Unit
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val activeTasks: List<Task> = tasksViewModel.activeTasks.reversed()
    val completedTasks: List<Task> = tasksViewModel.completedTasks.reversed()

    var snackBarText by remember { mutableStateOf("1 Completed") }
    val (arrowToggle, onArrowToggle) = remember { mutableStateOf(false) }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topLeft = 4.dp, topRight = 4.dp),
        sheetElevation = 0.dp,
        scrimColor = Color.Black.copy(0.5f),
        sheetContent = {
            TaskInput(
                onSaveClick = { tasksViewModel.addTask(task = it) },
                sheetState = modalBottomSheetState
            )
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomBar()
            },
            floatingActionButton = {
                Fab(sheetState = modalBottomSheetState)
            },
            snackbarHost = {
                SnackbarHost(hostState = it) {
                    SnackBar(snackBarText = snackBarText)
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
        ) { innerPadding ->

            if (activeTasks.isEmpty() && completedTasks.isEmpty()) {
                FreshStart()
            }

            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item { MainHeader() }

                items(activeTasks) { task ->
                    TaskItem(
                        task = task,
                        modifier = Modifier.clickable(
                            onClick = { onTaskClick(task) },
                            indication = null
                        ),
                        onTaskComplete = {
                            tasksViewModel.completeTask(task = it)
                            snackBarText = "1 Completed"
                            showSnackBar(scope, scaffoldState)
                        }
                    )
                }

                if (completedTasks.isNotEmpty()) {
                    item {
                        SubHeader(
                            completedTasksListSize = completedTasks.size,
                            arrowToggle = arrowToggle,
                            onArrowToggle = onArrowToggle
                        )
                    }
                    if (arrowToggle) {
                        items(completedTasks) { task ->
                            TaskItem(
                                task = task,
                                modifier = Modifier.clickable(
                                    onClick = { onTaskClick(task) },
                                    indication = null
                                ),
                                onTaskComplete = {
                                    tasksViewModel.activeTask(task = it)
                                    snackBarText = "1 Active"
                                    showSnackBar(scope, scaffoldState)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier,
    onTaskComplete: (Task) -> Unit
) {
    Row(Modifier.padding(24.dp, 14.dp)) {
        Checkbox(
            colors = getCheckBoxColors(),
            checked = task.isCompleted,
            onCheckedChange = {
                onTaskComplete(task)
            },
            modifier = Modifier
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .preferredSize(20.dp)
                .border(2.dp, shape = CircleShape, color = Color.Gray)
        )

        Spacer(modifier = Modifier.padding(horizontal = 10.dp))

        Column(
            modifier = modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = task.todo,
                style = typography.body1.merge(
                    if (task.isCompleted) TextStyle(textDecoration = TextDecoration.LineThrough) else typography.body1
                ),
                color = colors.onPrimary.copy(0.8f),
                modifier = modifier.fillMaxSize()
            )
            if (task.details.isNotEmpty()) {
                Text(
                    text = task.details,
                    style = typography.body2,
                    color = colors.onPrimary.copy(0.5f),
                    modifier = modifier.fillMaxSize()
                )
            }
            if (task.date.isNotEmpty()) {
                DateChip(
                    showCross = false,
                    onCrossClick = {},
                    modifier = modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun getCheckBoxColors(): CheckboxColors {
    return CheckboxConstants.defaultColors(
        checkedColor = colors.onPrimary,
        checkmarkColor = colors.primary
    )
}

@ExperimentalMaterialApi
fun showSnackBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    scope.launch {
        scaffoldState.snackbarHostState.showSnackbar("")
    }
}