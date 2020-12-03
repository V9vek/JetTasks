package com.vivek.jettasks.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.vivek.jettasks.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): String {
    val current = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("E dd MMM")
    return current.format(formatter)
}