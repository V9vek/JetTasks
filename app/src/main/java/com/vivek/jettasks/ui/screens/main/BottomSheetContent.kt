package com.vivek.jettasks.ui.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.vivek.jettasks.data.model.Task
import com.vivek.jettasks.ui.components.DateChip
import com.vivek.jettasks.ui.theme.typography
import com.vivek.jettasks.utils.getCurrentDate
import com.vivek.jettasks.utils.getIcon

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TaskInput(
    onSaveClick: (task: Task) -> Unit,
    sheetState: ModalBottomSheetState
) {
    var todo by remember { mutableStateOf(TextFieldValue("")) }
    var details by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("")) }
    var showDetailsInput by remember { mutableStateOf(false) }
    var showDateChip by remember { mutableStateOf(false) }

    Column {
        com.vivek.jettasks.ui.components.TextField(
            input = todo,
            onInputChange = { todo = it },
            placeholder = "New Task",
            modifier = Modifier.padding(start = 24.dp, top = 20.dp, end = 24.dp, bottom = 8.dp),
            fontSize = 17,
            textColor = colors.onSecondary.copy(0.8f)
        )

        if (showDetailsInput) {
            com.vivek.jettasks.ui.components.TextField(
                input = details,
                onInputChange = { details = it },
                placeholder = "Add Details",
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
                fontSize = 15,
                textColor = colors.onSecondary.copy(0.5f)
            )
        }

        if (showDateChip) {
            DateChip(
                showCross = true,
                onCrossClick = {
                    showDateChip = false
                    date = TextFieldValue("")
                },
                modifier = Modifier.padding(start = 24.dp, top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.preferredHeight(40.dp))

        Row(
            modifier = Modifier
                .preferredHeight(56.dp)
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { showDetailsInput = true }) {
                Icon(
                    vectorResource(id = getIcon("Details")),
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(onClick = {
                showDateChip = true
                date = TextFieldValue(getCurrentDate())
            }) {
                Icon(
                    vectorResource(id = getIcon("Calendar")),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val buttonColors = ButtonConstants.defaultButtonColors(
                disabledBackgroundColor = MaterialTheme.colors.surface,
                disabledContentColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f)
            )

            Button(
                onClick = {
                    onSaveClick(Task(todo = todo.text, details = details.text, date = date.text))
                    todo = TextFieldValue("")
                    sheetState.hide()
                },
                enabled = todo.text.isNotEmpty(),
                modifier = Modifier
                    .padding(8.dp)
                    .preferredHeight(36.dp)
                    .clip(RoundedCornerShape(50)),
                colors = buttonColors,
                elevation = ButtonConstants.defaultElevation(0.dp)
            ) {
                Text(text = "Save", style = typography.h6)
            }
        }
    }

    // not showing details input when fab is clicked again, weird feature in Google Tasks app
    if (!sheetState.isVisible) {
        showDetailsInput = false
        showDateChip = false
        details = TextFieldValue("")
        date = TextFieldValue("")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun UserInputPreview() {
    TaskInput(
        onSaveClick = {},
        sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    )
}