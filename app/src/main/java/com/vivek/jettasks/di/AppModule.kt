package com.vivek.jettasks.di

import android.content.Context
import androidx.room.Room
import com.vivek.jettasks.db.TaskDao
import com.vivek.jettasks.db.TaskDatabase
import com.vivek.jettasks.db.TaskDatabase.Companion.DATABASE_NAME
import com.vivek.jettasks.db.model.TaskEntityMapper
import com.vivek.jettasks.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideDatabase(app: BaseApplication): TaskDatabase {
        return Room
            .databaseBuilder(app, TaskDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTaskDao(db: TaskDatabase): TaskDao {
        return db.taskDao()
    }

    @Singleton
    @Provides
    fun provideEntityMapper(): TaskEntityMapper {
        return TaskEntityMapper()
    }
}