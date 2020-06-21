package com.destructo.dayist.repository

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDatabaseDao {

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM tasks_table WHERE taskId = :key")
    fun getTask(key: Long): Task

    @Query("DELETE FROM tasks_table WHERE taskId = :key")
    fun deleteTask(key: Long)

    @Query("DELETE FROM tasks_table")
    fun clearTable()

    @Query("SELECT * FROM tasks_table ORDER BY taskId DESC")
    fun getAllTask(): LiveData<List<Task>>


}