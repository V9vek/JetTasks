package com.vivek.jettasks.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vivek.jettasks.utils.DateUtils
import com.vivek.jettasks.utils.getIcon

@ExperimentalFoundationApi
@Composable
fun EditDetails(
    input: String,
    onInputChange: (String) -> Unit
) {
    Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp)) {
        Icon(
            imageVector = vectorResource(id = getIcon("Details")),
            contentDescription = null,
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
            textColor = MaterialTheme.colors.onSurface.copy(0.7f),
            visible = true
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun EditDateChip(
    showDateChip: Boolean,
    onTextFieldClick: () -> Unit,
    onCrossClick: () -> Unit
) {
    Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp)) {
        Icon(
            imageVector = vectorResource(id = getIcon("Calendar")),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.size(16.dp))

        DateChip(
            chipText = DateUtils.longDateToString(DateUtils.getCurrentDate()),
            showCancelIcon = true,
            onCancelClick = onCrossClick,
            visible = showDateChip
        )
        if (!showDateChip) {
            Text(
                text = "Add date/time",
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onSurface.copy(
                        0.5f
                    )
                ),
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(onClick = onTextFieldClick)
            )
        }
    }
}