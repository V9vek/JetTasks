package com.vivek.jettasks.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vivek.jettasks.presentation.theme.typography

@Composable
fun DateChip(
    chipText: String,
    showCancelIcon: Boolean,
    visible: Boolean,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (visible) {
        Box(
            modifier = modifier
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colors.onSecondary.copy(0.5f)
                )
                .padding(start = 8.dp, top = 2.dp, bottom = 2.dp, end = 8.dp)
        ) {
            Row {
                Text(
                    text = chipText,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterVertically),
                    style = typography.body2,
                    color = MaterialTheme.colors.onSecondary.copy(0.5f)
                )
                if (showCancelIcon) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = colors.onPrimary,
                        modifier = Modifier
                            .size(18.dp)
                            .align(Alignment.CenterVertically)
                            .clickable(onClick = onCancelClick)
                    )
                }
            }
        }
    }
}