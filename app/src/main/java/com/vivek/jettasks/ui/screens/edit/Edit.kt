package com.vivek.jettasks.ui.screens.edit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.vivek.jettasks.data.model.Task
import com.vivek.jettasks.ui.components.DateChip
import com.vivek.jettasks.ui.components.TextField
import com.vivek.jettasks.ui.screens.Routing
import com.vivek.jettasks.ui.screens.ToolbarAction
import com.vivek.jettasks.utils.getCurrentDate
import com.vivek.jettasks.utils.getIcon
import com.vivek.jettasks.viewmodels.TasksViewModel

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
fun Routing.Edit.Content(
    tasksViewModel: TasksViewModel,
    taskId: String,
    onBackPress: () -> Unit
) {
    val selectedTask = tasksViewModel.getTask(taskId)
    var todo by remember { mutableStateOf(TextFieldValue(selectedTask.todo)) }
    var details by remember { mutableStateOf(TextFieldValue(selectedTask.details)) }
    var date by remember { mutableStateOf(TextFieldValue(selectedTask.date)) }
    var showDateChip by remember { mutableStateOf(selectedTask.date.isNotEmpty()) }

    Scaffold(
        topBar = {
            TopBar(
                onActionClick = { action ->
                    when (action) {
                        ToolbarAction.Complete -> tasksViewModel.completeTask(selectedTask)
                        ToolbarAction.Delete -> tasksViewModel.deleteTask(selectedTask.id)
                    }
                    onBackPress()
                },
                onBackPress = onBackPress
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            TextField(
                modifier = Modifier.padding(top = 24.dp, start = 24.dp),
                input = todo,
                onInputChange = {
                    todo = it
                    tasksViewModel.updateTask(
                        taskId = taskId,
                        updatedTask = createTask(todo.text, details.text, date.text)
                    )
                },
                placeholder = "Enter Title",
                fontSize = 22,
                textColor = colors.onSurface
            )

            EditDetails(
                input = details,
                onInputChange = {
                    details = it
                    tasksViewModel.updateTask(
                        taskId = taskId,
                        updatedTask = createTask(todo.text, details.text, date.text)
                    )
                }
            )

            EditDateChip(
                showDateChip = showDateChip,
                onTextFieldClick = {
                    showDateChip = true
                    date = TextFieldValue(getCurrentDate())
                    tasksViewModel.updateTask(
                        taskId = taskId,
                        updatedTask = createTask(todo.text, details.text, date.text)
                    )
                },
                onCrossClick = {
                    showDateChip = false
                    date = TextFieldValue("")
                    tasksViewModel.updateTask(
                        taskId = taskId,
                        updatedTask = createTask(todo.text, details.text, date.text)
                    )
                }
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun EditDetails(
    input: TextFieldValue,
    onInputChange: (TextFieldValue) -> Unit
) {
    Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp)) {
        Icon(
            vectorResource(id = getIcon("Details")),
            tint = Color.Gray,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.size(16.dp))

        TextField(
            modifier = Modifier.align(Alignment.CenterVertically),
            input = input,
            onInputChange = onInputChange,
            placeholder = "Add details",
            fontSize = 18,
            textColor = colors.onSurface.copy(0.7f)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
fun EditDateChip(
    showDateChip: Boolean,
    onTextFieldClick: () -> Unit,
    onCrossClick: () -> Unit
) {
    Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp)) {
        Icon(
            vectorResource(id = getIcon("Calendar")),
            tint = Color.Gray,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.size(16.dp))

        if (showDateChip) {
            DateChip(showCross = true, onCrossClick = onCrossClick)
        } else {
            val disableContentColor =
                AmbientEmphasisLevels.current.disabled.applyEmphasis(colors.onSurface.copy(0.5f))
            Text(
                text = "Add date/time",
                style = MaterialTheme.typography.body1.copy(color = disableContentColor),
                fontSize = TextUnit.Sp(18),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(onClick = onTextFieldClick)
            )
        }
    }
}

fun createTask(
    todo: String,
    details: String,
    date: String
) = Task(todo = todo, details = details, date = date)
