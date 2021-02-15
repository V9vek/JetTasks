package com.vivek.jettasks.db

import androidx.room.*
import com.vivek.jettasks.db.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Query("DELETE FROM task_table where id = :id")
    suspend fun deleteTask(id: Int)

    @Update
    suspend fun updateTaskOnCompletion(task: TaskEntity)

    @Query("UPDATE task_table set name = :name, details = :details, dateAdded = :dateAdded WHERE id = :id")
    suspend fun updateTask(name: String, details: String, dateAdded: Long, id: Int)

    @Query("SELECT * FROM task_table WHERE completed = 1 ORDER BY dateCompleted DESC")
    fun getCompletedTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE completed = 0 ORDER BY dateCreated DESC")
    fun getActiveTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    fun getTask(taskId: Int): Flow<TaskEntity>
}
