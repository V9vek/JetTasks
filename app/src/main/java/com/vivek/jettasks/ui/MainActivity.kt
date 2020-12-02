package com.vivek.jettasks.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.vivek.jettasks.ui.screens.Routing
import com.vivek.jettasks.ui.screens.edit.Content
import com.vivek.jettasks.ui.screens.main.Content
import com.vivek.jettasks.ui.theme.JetTasksTheme
import com.vivek.jettasks.viewmodels.TasksViewModel

@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTasksTheme {
                Root()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Root() {
    val tasksViewModel: TasksViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routing.Main.route) {
        composable(route = Routing.Main.route) {
            Routing.Main.Content(
                tasksViewModel,
                onTaskClick = { task ->
                    navController.navigate("${Routing.Edit.route}/${task.id}")
                }
            )
        }

        composable(
            route = "${Routing.Edit.route}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("taskId")?.let { taskId ->
                Routing.Edit.Content(
                    tasksViewModel,
                    taskId = taskId,
                    onBackPress = { navController.popBackStack() }
                )
            }
        }
    }
}