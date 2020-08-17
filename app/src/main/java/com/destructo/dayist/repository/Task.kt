package com.destructo.dayist.repository

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tasks_table")
data class Task (
    @PrimaryKey(autoGenerate = true)
    val taskId:Long=0,
    @ColumnInfo(name="task_title")
    val taskTitle:String,
    @ColumnInfo(name = "task_description")
    val taskDescription:String
):Parcelable{
}