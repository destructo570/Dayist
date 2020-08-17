package com.destructo.dayist.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskDatabaseDao: TaskDatabaseDao

    companion object{

    }
}