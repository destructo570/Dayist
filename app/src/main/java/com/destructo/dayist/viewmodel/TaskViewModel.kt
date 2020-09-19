package com.destructo.dayist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.destructo.dayist.repository.Task
import com.destructo.dayist.repository.TaskDatabaseDao
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class TaskViewModel(

    private val database: TaskDatabaseDao,
    application: Application): AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val userTaskList = database.getAllTask()

    private val _editTaskNavigation = MutableLiveData<Task?>()
    val editTaskNavigation:LiveData<Task?>
    get() = _editTaskNavigation

    fun addTaskToDatabase(task: Task){
        uiScope.launch {
            insertTask(task)
        }
    }

    fun deleteTaskFromDatabase(taskId: Long){
        uiScope.launch {
            deleteTask(taskId)
        }
    }

    fun updateTaskInDatabase(task: Task){
        uiScope.launch {
            updateTask(task)
        }
    }

    private suspend fun updateTask(task: Task) {
        withContext(IO){
            database.update(task)
        }
    }

    private suspend fun deleteTask(taskId: Long) {
        withContext(IO){
            database.deleteTask(taskId)
        }
    }

    private suspend fun insertTask(task: Task) {
        withContext(IO){
            if(checkTask(task)){
                database.insert(task)
            }

        }
    }

    private fun checkTask(task: Task): Boolean {
        return !task.taskTitle.isBlank() && !task.taskDescription.isBlank()
    }

    fun onEditNavigation(position: Int){
        val tasklist = userTaskList.value
        _editTaskNavigation.value = tasklist?.get(position)
    }

    fun doneEditNavigation(){
        _editTaskNavigation.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}