package com.vivek.jettasks.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.vivek.jettasks.presentation.theme.typography
import com.vivek.jettasks.utils.DateUtils
import com.vivek.jettasks.utils.getIcon

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TaskInput(
    sheetState: ModalBottomSheetState,
    taskTodo: String,
    taskDetails: String,
    taskDate: Long,
    onClear: () -> Unit,
    onTodoChange: (String) -> Unit,
    onDetailsChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onSaveClick: () -> Unit,
    showDetails: Boolean,
    showDate: Boolean,
    onToggleShowDetails: (Boolean) -> Unit,
    onToggleShowDate: (Boolean) -> Unit
) {
    Column {
        // Input Fields
        TextField(
            input = taskTodo,
            onInputChange = { onTodoChange(it) },
            placeholder = "New Task",
            modifier = Modifier.padding(start = 24.dp, top = 20.dp, end = 24.dp, bottom = 8.dp),
            fontSize = 17,
            textColor = MaterialTheme.colors.onSecondary.copy(0.8f),
            visible = true
        )

        TextField(
            input = taskDetails,
            onInputChange = { onDetailsChange(it) },
            placeholder = "Add Details",
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
            fontSize = 15,
            textColor = MaterialTheme.colors.onSecondary.copy(0.5f),
            visible = showDetails
        )

        DateChip(
            chipText = DateUtils.longDateToString(taskDate),
            showCancelIcon = true,
            visible = showDate,
            onCancelClick = {
                onToggleShowDate(false)
                onDateChange(0L)
            },
            modifier = Modifier.padding(start = 24.dp, top = 8.dp)
        )

        Spacer(modifier = Modifier.preferredHeight(40.dp))

        // Buttons
        Row(
            modifier = Modifier
                .preferredHeight(56.dp)
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onToggleShowDetails(!showDetails) }) {
                Icon(
                    imageVector = vectorResource(id = getIcon("Details")),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = {
                    onToggleShowDate(true)
                    onDateChange(System.currentTimeMillis())
                }
            ) {
                Icon(
                    imageVector = vectorResource(id = getIcon("Calendar")),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val buttonColors = ButtonDefaults.buttonColors(
                disabledBackgroundColor = MaterialTheme.colors.surface,
                disabledContentColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f)
            )

            Button(
                onClick = {
                    sheetState.hide(onHidden = { onClear() })
                    onSaveClick()
                },
                enabled = taskTodo.isNotEmpty(),
                modifier = Modifier
                    .padding(8.dp)
                    .preferredHeight(36.dp)
                    .clip(RoundedCornerShape(50)),
                colors = buttonColors,
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(text = "Save", style = typography.h6)
            }
        }
    }
}
