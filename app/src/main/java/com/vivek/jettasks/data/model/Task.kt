package com.vivek.jettasks.data.model

import java.util.*

data class Task(
    val id: String = UUID.randomUUID().toString(),
    var todo: String = "",
    var details: String = "",
    var date: String = "",
    var isCompleted: Boolean = false
)