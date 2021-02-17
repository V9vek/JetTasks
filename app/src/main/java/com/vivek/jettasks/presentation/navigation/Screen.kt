package com.vivek.jettasks.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry

sealed class Screen(
    val route: String
) {
    object Splash: Screen(route = "splash")

    object Tasks : Screen(route = "tasks")

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

@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.hiltNavGraphViewModel(): VM {
    val viewmodelFactory = HiltViewModelFactory(LocalContext.current, this)
    return ViewModelProvider(this, viewmodelFactory).get(VM::class.java)
}