package com.vivek.jettasks.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val id: Int,
    var name: String,
    var details: String,
    var dateCreated: Long,
    var dateAdded: Long,
    var dateCompleted: Long,
    var completed: Boolean
) : Parcelable