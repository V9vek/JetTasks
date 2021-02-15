package com.vivek.jettasks.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.vivek.jettasks.domain.model.Task
import com.vivek.jettasks.presentation.theme.typography
import com.vivek.jettasks.utils.DateUtils
import com.vivek.jettasks.utils.getCheckBoxColors

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier,
    onTaskComplete: (Task) -> Unit
) {
    Row(modifier.padding(24.dp, 14.dp)) {
        Checkbox(
            colors = getCheckBoxColors(),
            checked = task.completed,
            onCheckedChange = {
                onTaskComplete(
                    task.copy(completed = it, dateCompleted = DateUtils.getCurrentDate())
                )
            },
            modifier = modifier
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .preferredSize(20.dp)
                .border(2.dp, shape = CircleShape, color = Color.Gray)
        )

        Spacer(modifier = modifier.padding(horizontal = 10.dp))

        Column(
            modifier = modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = task.name,
                style = typography.body1.merge(
                    if (task.completed) TextStyle(textDecoration = TextDecoration.LineThrough) else typography.body1
                ),
                color = MaterialTheme.colors.onPrimary.copy(0.8f),
                modifier = modifier.fillMaxSize()
            )
            if (task.details.isNotEmpty()) {
                Text(
                    text = task.details,
                    style = typography.body2,
                    color = MaterialTheme.colors.onPrimary.copy(0.5f),
                    modifier = modifier.fillMaxSize()
                )
            }

            DateChip(
                chipText = DateUtils.longDateToString(task.dateAdded),
                showCancelIcon = false,
                visible = DateUtils.longDateToString(task.dateAdded).isNotEmpty(),
                onCancelClick = {},
                modifier = modifier.padding(top = 8.dp)
            )
        }
    }
}