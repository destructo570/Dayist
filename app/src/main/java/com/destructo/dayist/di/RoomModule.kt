package com.destructo.dayist.di

import android.content.Context
import androidx.room.Room
import com.destructo.dayist.repository.TaskDatabase
import com.destructo.dayist.repository.TaskDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule{

    @Singleton
    @Provides
    fun provideTaskDb(@ApplicationContext context: Context): TaskDatabase{
        return  Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java,
            "user_task_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDatabaseDao{
        return taskDatabase.taskDatabaseDao
    }
}