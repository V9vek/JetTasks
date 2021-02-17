package com.vivek.jettasks.utils

import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import com.vivek.jettasks.R
import com.vivek.jettasks.domain.model.Task

fun getIcon(icon: String): Int {
    return when (icon) {
        "ArrowUp" -> R.drawable.ic_arrow_up
        "ArrowDown" -> R.drawable.ic_arrow_down
        "Add" -> R.drawable.ic_add
        "Details" -> R.drawable.ic_details
        "Calendar" -> R.drawable.ic_calendar
        else -> R.color.white
    }
}

@Composable
@ExperimentalMaterialApi
fun getCheckBoxColors(): CheckboxColors {
    return CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colors.onPrimary,
        checkmarkColor = MaterialTheme.colors.primary
    )
}



