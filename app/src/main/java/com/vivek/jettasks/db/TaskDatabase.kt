package com.vivek.jettasks.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vivek.jettasks.db.model.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "task_db"
    }
}