package com.vivek.jettasks.ui.screens.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.vivek.jettasks.R
import com.vivek.jettasks.ui.screens.Routing
import com.vivek.jettasks.utils.getIcon

@Composable
fun Routing.Edit.Header(
    onComplete: () -> Unit,
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Rounded.ArrowBack, tint = MaterialTheme.colors.onPrimary)
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = {
            onComplete()
            onBack()
        }) {
            Icon(Icons.Rounded.Done, tint = MaterialTheme.colors.onPrimary)
        }

        IconButton(onClick = {
            onDelete()
            onBack()
        }) {
            Icon(vectorResource(id = getIcon("Delete")), tint = MaterialTheme.colors.onPrimary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeader() {
    Routing.Edit.Header(onComplete = {}, onBack = {}, onDelete = {})
}