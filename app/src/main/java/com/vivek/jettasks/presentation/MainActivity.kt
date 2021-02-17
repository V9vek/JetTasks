package com.vivek.jettasks.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.vivek.jettasks.presentation.navigation.Screen.*
import com.vivek.jettasks.presentation.navigation.hiltNavGraphViewModel
import com.vivek.jettasks.presentation.theme.JetTasksTheme
import com.vivek.jettasks.presentation.ui.edit.EditTaskScreen
import com.vivek.jettasks.presentation.ui.edit.EditTaskViewModel
import com.vivek.jettasks.presentation.ui.edit.provideEditTaskViewModel
import com.vivek.jettasks.presentation.ui.splash.SplashScreenAnimation
import com.vivek.jettasks.presentation.ui.tasks.TasksScreen
import com.vivek.jettasks.presentation.ui.tasks.TasksViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun editTaskViewModelFactory(): EditTaskViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetTasksMain()
        }
    }

    @Composable
    private fun JetTasksMain() {
        JetTasksTheme {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Splash.route) {
                composable(route = Splash.route) {
                    SplashScreenAnimation(navController = navController)
                }

                composable(route = Tasks.route) { navBackStackEntry ->
                    val viewModel: TasksViewModel = navBackStackEntry.hiltNavGraphViewModel()
                    TasksScreen(
                        viewModel = viewModel,
                        onTaskClick = { task ->
                            navController.navigate("${Edit.route}/${task.id}")
                        }
                    )
                }

                composable(
                    route = "${Edit.route}/{taskId}",
                    arguments = listOf(navArgument("taskId") {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    navBackStackEntry.arguments?.getInt("taskId")?.let { taskId ->
                        EditTaskScreen(
                            viewModel = provideEditTaskViewModel(taskId = taskId),
                            onBackPress = { navController.popBackStack() },
                        )
                    }
                }
            }
        }
    }
}






