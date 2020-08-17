package com.destructo.dayist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.destructo.dayist.R
import com.destructo.dayist.databinding.TaskListItemViewBinding
import com.destructo.dayist.repository.Task
import kotlinx.android.synthetic.main.task_list_item_view.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

const val TYPE_HEADER = 0
const val TYPE_ITEM = 1

class TaskAdapter(private val taskEditListener:TaskEditListener,
                  private val taskListener: TaskClickListener):
    ListAdapter<DataItem, RecyclerView.ViewHolder>(TaskDiffUtil()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

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
        }

        override fun onClick(v: View?) {
            taskEditListener.onClick(adapterPosition.minus(1))
        }
    }

    class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.activity_header, parent, false)
                return HeaderViewHolder(view)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_HEADER-> HeaderViewHolder.from(parent)
            TYPE_ITEM-> ViewHolder.from(parent,taskEditListener)
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ViewHolder-> {
                val taskItem = getItem(position) as DataItem.TaskItem
                holder.bind(taskItem.task, taskListener)
                holder.itemView.checkBox.isChecked = false
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.Header -> TYPE_HEADER
            is DataItem.TaskItem -> TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(list: List<Task>){
        adapterScope.launch {
            val items = when(list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.TaskItem(it) }
            }
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }
}

class TaskDiffUtil: DiffUtil.ItemCallback<DataItem>(){
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class TaskClickListener(val taskListener: (taskId:Long)-> Unit){
    fun onClick(task: Task) = taskListener(task.taskId)
}

interface TaskEditListener{
    fun onClick(position: Int)
}

sealed class DataItem{
    data class TaskItem(val task: Task): DataItem(){
        override val id = task.taskId
    }
    object Header:DataItem(){
        override val id = Long.MIN_VALUE
    }
    abstract val id:Long
}