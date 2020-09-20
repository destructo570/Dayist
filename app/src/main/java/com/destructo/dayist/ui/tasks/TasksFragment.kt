package com.destructo.dayist.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.destructo.dayist.R
import com.destructo.dayist.adapter.*
import com.destructo.dayist.databinding.FragmentTasksBinding
import com.destructo.dayist.repository.Task
import com.destructo.dayist.repository.TaskDatabase
import com.destructo.dayist.viewmodel.TaskViewModel
import com.destructo.dayist.viewmodel.TaskViewModelFactory
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : Fragment(), TaskEditListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: FragmentTasksBinding
    private var currentVisiblePosition: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tasks, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao

        val taskViewModelFactory = TaskViewModelFactory(dataSource, application)

        taskViewModel = ViewModelProvider(this, taskViewModelFactory).get(TaskViewModel::class.java)

        binding.lifecycleOwner = this

        binding.taskViewModel = taskViewModel

        binding.addTaskFab.setOnClickListener {addTaskFab->
            addTaskFab.findNavController().navigate(R.id.action_tasksFragment_to_addNewTaskFragment)
        }

        taskViewModel.userTaskList.observe(viewLifecycleOwner, Observer {taskList->
                task_remaining_text.text = "You have ${taskList.size} tasks remaining."
        })

        //insertDummyData()

        binding.taskRecycler.adapter =  TaskAdapter(this,TaskClickListener {taskId->
         taskViewModel.deleteTaskFromDatabase(taskId)
        })

        taskViewModel.editTaskNavigation.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToEditFragment(it))
                taskViewModel.doneEditNavigation()
            }
        })

        return binding.root
    }


    override fun onClick(position: Int) {
        taskViewModel.onEditNavigation(position)

    }

    override fun onPause() {
        super.onPause()
    }

    //Call this function anywhere to add dummy data.
    fun insertDummyData(){
        var i:Int = 0
       while (i<40){
           val task = Task(
               taskTitle = "Test ${i}",
               taskDescription = "Test Desc ${i}"
           )
            taskViewModel.addTaskToDatabase(task)
           i++
        }
    }

}