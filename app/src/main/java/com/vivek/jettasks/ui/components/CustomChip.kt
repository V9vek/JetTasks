package com.vivek.jettasks.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.vivek.jettasks.ui.theme.typography
import com.vivek.jettasks.utils.getCurrentDate
import com.vivek.jettasks.utils.getIcon

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateChip(
    showCross: Boolean,
    onCrossClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colors.onSecondary.copy(0.5f)
            ).padding(start = 8.dp, top = 2.dp, bottom = 2.dp, end = 8.dp)
    ) {
        Row {
            Text(
                text = getCurrentDate(),
                modifier = Modifier.padding(4.dp).align(Alignment.CenterVertically),
                style = typography.body2,
                color = MaterialTheme.colors.onSecondary.copy(0.5f)
            )
            if (showCross) {
                Icon(
                    Icons.Outlined.Close,
                    tint = colors.onPrimary,
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.CenterVertically)
                        .clickable(onClick = onCrossClick)
                )
            }
        }
    }
}