package com.vivek.jettasks.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routing(val route: String, val label: String) {

    object Main : Routing(route = "main", label = "Main")
    object Edit : Routing(route = "edit", label = "Edit") {
        val actions: List<ToolbarAction> = listOf(
            ToolbarAction.Complete,
            ToolbarAction.Delete
        )
    }
}

sealed class ToolbarAction(val icon: ImageVector) {
    object Complete : ToolbarAction(Icons.Default.Done)
    object Delete : ToolbarAction(Icons.Outlined.Delete)
}