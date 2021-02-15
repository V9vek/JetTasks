package com.vivek.jettasks.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val sdf = SimpleDateFormat("E dd MMM")

    fun longDateToString(long: Long): String {
        return if (long == 0L) {
            ""
        } else {
            sdf.format(Date(long))
        }
    }

    fun getCurrentDate(): Long = System.currentTimeMillis()

    fun longToDate(long: Long): Date {
        return Date(long)
    }

    fun dateToLong(date: Date): Long {
        return date.time / 1000 // return seconds
    }

    // Ex: November 4, 2021
    fun dateToString(date: Date): String {
        println("date: $date, string: ${sdf.format(date)}")
        return sdf.format(date)
    }

    fun stringToDate(string: String): Date {
        return sdf.parse(string)
            ?: throw NullPointerException("Could not convert date string to Date object.")
    }

    fun createTimestamp(): Date {
        return Date()
    }
}














