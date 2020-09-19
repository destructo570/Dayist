package com.destructo.dayist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.destructo.dayist.databinding.TaskListItemViewBinding
import com.destructo.dayist.repository.Task

class SimpleAdapter (private val taskListener: SimpleTaskClickListener):
    ListAdapter<Task, SimpleAdapter.ViewHolder>(SimpleTaskDiffUtil()) {

        class ViewHolder(val binding: TaskListItemViewBinding) :
            RecyclerView.ViewHolder(binding.root){
            companion object{
                fun from(parent: ViewGroup): ViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = TaskListItemViewBinding.inflate(layoutInflater, parent, false)
                    return ViewHolder(binding)
                }
            }
            fun bind(
                task: Task,
                taskListener: SimpleTaskClickListener
            ) {
                binding.task = task
                //binding.taskListener = taskListener
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleAdapter.ViewHolder {
            return ViewHolder.from(parent)
        }

        override fun onBindViewHolder(holder: SimpleAdapter.ViewHolder, position: Int) {
            val currentTask = getItem(position)
            holder.bind(currentTask, taskListener)
        }
    }

    class SimpleTaskDiffUtil: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    class SimpleTaskClickListener(val taskListener: (taskId:Long)-> Unit){
        fun onClick(task: Task) = taskListener(task.taskId)
    }
