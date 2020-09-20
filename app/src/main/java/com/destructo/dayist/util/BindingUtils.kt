package com.destructo.dayist.util

import android.text.TextUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.destructo.dayist.adapter.TaskAdapter
import com.destructo.dayist.repository.Task


@BindingAdapter("taskTitle")
fun TextView.taskTitle(item: Task?){
    item?.let {
        text = item.taskTitle
    }
}

@BindingAdapter("taskDescription")
fun TextView.taskDescription(item:Task?){
    item?.let {
        text = item.taskDescription
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
    }
}

@BindingAdapter("submitTaskList")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Task>?){
    data?.let{
        val adapter = recyclerView.adapter as TaskAdapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
    adapter.submitList(it)
    }
}

