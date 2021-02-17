package com.vivek.jettasks.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vivek.jettasks.R
import com.vivek.jettasks.presentation.navigation.Screen.Edit
import com.vivek.jettasks.presentation.navigation.Screen.ToolbarAction
import com.vivek.jettasks.presentation.theme.typography
import com.vivek.jettasks.utils.getIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TopBar(
    onActionClick: (action: ToolbarAction) -> Unit,
    onBackPress: () -> Unit,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onPrimary
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = contentColor
                )
            }
        },
        actions = {
            Edit.actions.forEach { action ->
                IconButton(onClick = { onActionClick(action) }) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = null,
                        tint = contentColor
                    )
                }
            }
        },
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = 0.dp
    )
}

@Composable
fun BottomBar() {
    BottomAppBar(cutoutShape = RoundedCornerShape(50), elevation = 20.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.Menu, contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Fab(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(50)
    ) {
        Icon(
            painter = painterResource(id = getIcon("Add")),
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
        )
    }
}

@Composable
fun SnackBar(
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
fun FreshStart(visible: Boolean) {
    if (visible) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_freshstart),
                contentDescription = null,
                modifier = Modifier.size(240.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))

            Text(text = "A fresh start", style = typography.body1)
            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Anything to add ?",
                style = typography.body2,
                color = MaterialTheme.colors.onPrimary.copy(0.5f)
            )
        }
    }
}

@Composable
fun MyTasksTitle() {
    Text(
        text = "My Tasks", style = typography.h4,
        modifier = Modifier.padding(horizontal = 64.dp, vertical = 24.dp)
    )
}

@Composable
fun CompletedSubTitle(
    completedTasksListSize: Int,
    arrowToggle: Boolean,
    onArrowToggle: (Boolean) -> Unit,
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
                style = typography.body1
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(
                    id = if (arrowToggle) getIcon("ArrowUp") else getIcon("ArrowDown")
                ),
                contentDescription = null
            )
        }
    }
}