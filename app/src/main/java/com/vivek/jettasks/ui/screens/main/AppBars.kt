package com.vivek.jettasks.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.vivek.jettasks.R
import com.vivek.jettasks.ui.screens.Routing
import com.vivek.jettasks.ui.theme.typography
import com.vivek.jettasks.utils.getIcon

val fabShape = RoundedCornerShape(50)

@Composable
fun Routing.Main.BottomBar() {
    BottomAppBar(cutoutShape = fabShape, elevation = 20.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.Rounded.Menu, tint = MaterialTheme.colors.onPrimary)
            }

            IconButton(onClick = {}) {
                Icon(Icons.Rounded.MoreVert, tint = MaterialTheme.colors.onPrimary)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Routing.Main.Fab(
    sheetState: ModalBottomSheetState
) {
    FloatingActionButton(
        onClick = { sheetState.show() },
        shape = fabShape
    ) {
        Icon(
            vectorResource(id = getIcon("Add")),
            tint = MaterialTheme.colors.onPrimary,
        )
    }
}

@Composable
fun Routing.Main.SnackBar(
    snackBarText: String
) {
    Snackbar(
        text = { Text(text = snackBarText) },
        action = {
            Text(
                text = "Undo",
                modifier = Modifier.clickable(onClick = {
                    // TODO: Undo Feature
                })
            )
        },
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun Routing.Main.MainHeader() {
    Text(
        text = "My Tasks", style = typography.h4,
        modifier = Modifier.padding(horizontal = 64.dp, vertical = 24.dp)
    )
}

@Composable
fun Routing.Main.SubHeader(
    completedTasksListSize: Int,
    arrowToggle: Boolean,
    onArrowToggle: (Boolean) -> Unit
) {
    Column {
        Divider(
            thickness = 0.8.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
        )

        Row(
            Modifier
                .clickable(onClick = {
                    onArrowToggle(!arrowToggle)
                })
                .padding(24.dp, 10.dp)
        ) {
            Text(
                text = "Completed ( $completedTasksListSize )",
                style = typography.body2
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                vectorResource(id = if (arrowToggle) getIcon("ArrowUp") else getIcon("ArrowDown"))
            )
        }
    }
}

@Composable
fun Routing.Main.FreshStart() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(vectorResource(id = R.drawable.ic_freshstart), modifier = Modifier.size(240.dp))
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "A fresh start", style = typography.body1)
        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = "Anything to add ?",
            style = typography.body2,
            color = colors.onPrimary.copy(0.5f)
        )
    }
}
















