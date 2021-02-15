package com.vivek.jettasks.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String
) {
    object Tasks : Screen(route = "tasks")

    object Add : Screen(route = "add")

    object Edit : Screen(route = "edit") {
        val actions: List<ToolbarAction> = listOf(
            ToolbarAction.Complete,
            ToolbarAction.Delete
        )
    }

    // toolbar actions in edit screen
    sealed class ToolbarAction(val icon: ImageVector) {
        object Complete : ToolbarAction(Icons.Default.Done)
        object Delete : ToolbarAction(Icons.Outlined.Delete)
    }
}