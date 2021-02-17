package com.vivek.jettasks.presentation.ui.splash

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.vivek.jettasks.R
import com.vivek.jettasks.presentation.navigation.Screen
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000

enum class SplashState {
    SHOWN, COMPLETED
}

@Composable
fun SplashScreenAnimation(
    navController: NavController
) {
    val transitionState = remember { MutableTransitionState(SplashState.SHOWN) }
    val transition = updateTransition(transitionState = transitionState)
    val splashAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 100) }
    ) {
        if (it == SplashState.SHOWN) 1f else 0f
    }

    SplashScreen(
        modifier = Modifier.alpha(splashAlpha),
        onTimeout = { transitionState.targetState = SplashState.COMPLETED },
        navController = navController
    )
}

@Composable
fun SplashScreen(
    modifier: Modifier,
    navController: NavController,
    onTimeout: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val currentOnTimeout by rememberUpdatedState(newValue = onTimeout)

        LaunchedEffect(Unit) {
            delay(SplashWaitTime)
            currentOnTimeout()
            navController.popBackStack()
            navController.navigate(Screen.Tasks.route)
        }
        Image(painterResource(id = R.drawable.splash_image), contentDescription = null)
    }
}












