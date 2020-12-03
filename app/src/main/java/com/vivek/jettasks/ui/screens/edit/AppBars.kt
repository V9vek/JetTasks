package com.vivek.jettasks.ui.screens.edit

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.vivek.jettasks.ui.screens.Routing.Edit.actions
import com.vivek.jettasks.ui.screens.ToolbarAction

@Composable
fun TopBar(
    onActionClick: (action: ToolbarAction) -> Unit,
    onBackPress: () -> Unit,
    backgroundColor: Color = colors.surface,
    contentColor: Color = colors.onPrimary
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(Icons.Rounded.ArrowBack, tint = contentColor)
            }
        },
        actions = {
            actions.forEach { action ->
                IconButton(onClick = { onActionClick(action) }) {
                    Icon(action.icon, tint = contentColor)
                }
            }
        },
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = 0.dp
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHeader() {
    TopBar(onActionClick = {}, onBackPress = {})
}