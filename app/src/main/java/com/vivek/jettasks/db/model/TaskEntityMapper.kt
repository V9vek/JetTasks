package com.vivek.jettasks.db.model

import android.util.Log
import com.vivek.jettasks.domain.model.Task
import com.vivek.jettasks.utils.DomainMapper

class TaskEntityMapper : DomainMapper<TaskEntity, Task> {

    override fun mapToDomainModel(model: TaskEntity): Task {
        Log.e("mapToDomainModel", "mapToDomainModel$model")
        return Task(
            id = model.id,
            name = model.name,
            details = model.details,
            dateCreated = model.dateCreated,
            dateAdded = model.dateAdded,
            dateCompleted = model.dateCompleted,
            completed = model.completed
        )
    }

    override fun mapFromDomainModel(domainModel: Task): TaskEntity {
        return TaskEntity(
            id = domainModel.id,
            name = domainModel.name,
            details = domainModel.details,
            dateCreated = domainModel.dateCreated,
            dateAdded = domainModel.dateAdded,
            dateCompleted = domainModel.dateCompleted,
            completed = domainModel.completed
        )
    }

    fun fromEntityList(initial: List<TaskEntity>): List<Task> {
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Task>): List<TaskEntity> {
        return initial.map { mapFromDomainModel(it) }
    }
}