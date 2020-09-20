package com.destructo.dayist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.destructo.dayist.databinding.TaskListItemViewBinding
import com.destructo.dayist.repository.Task

class TaskAdapter(private val taskEditListener:TaskEditListener,
                  private val taskListener: TaskClickListener):
    ListAdapter<Task, TaskAdapter.ViewHolder>(TaskDiffUtil()) {

    class ViewHolder(val binding: TaskListItemViewBinding, private val taskEditListener:TaskEditListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        companion object{
            fun from(parent: ViewGroup,taskEditListener:TaskEditListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskListItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding,taskEditListener)
            }
        }
         fun bind(
             task: Task,
             taskListener: TaskClickListener
         ) {
            binding.task = task
            binding.taskListener = taskListener
            binding.itemViewId.setOnClickListener(this)
             if(binding.checkBox.isChecked) binding.checkBox.isChecked=false
        }

        override fun onClick(v: View?) {
            taskEditListener.onClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        return ViewHolder.from(parent,taskEditListener)
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), taskListener)

    }
}

class TaskDiffUtil: DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}

class TaskClickListener(val taskListener: (taskId:Long)-> Unit){
    fun onClick(task: Task) = taskListener(task.taskId)
}

interface TaskEditListener{
    fun onClick(position: Int)
}