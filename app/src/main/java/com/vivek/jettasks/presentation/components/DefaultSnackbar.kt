package com.vivek.jettasks.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun DefaultSnackbar(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onUndo: () -> Unit
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
    ) { snackbarData ->
        Snackbar(
            action = {
                snackbarData.actionLabel?.let {
                    Text(
                        text = it,
                        modifier = Modifier.clickable { onUndo() }
                    )
                }
            }
        ) {
            Text(text = snackbarData.message)
        }
    }
}