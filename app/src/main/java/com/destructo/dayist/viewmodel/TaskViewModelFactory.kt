package com.destructo.dayist.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.destructo.dayist.repository.TaskDatabaseDao
import java.lang.IllegalArgumentException

class TaskViewModelFactory (
    private val dataSource: TaskDatabaseDao,
    private val application: Application): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

       if (modelClass.isAssignableFrom(TaskViewModel::class.java)){
           return TaskViewModel(dataSource, application) as T
       }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}