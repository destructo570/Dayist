package com.destructo.dayist.ui.addNewTask

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.destructo.dayist.databinding.FragmentAddNewTaskBinding
import com.destructo.dayist.databinding.FragmentAddNewTaskBinding.inflate
import com.destructo.dayist.repository.Task
import com.destructo.dayist.repository.TaskDatabase
import com.destructo.dayist.util.showSoftKeyboard
import com.destructo.dayist.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_new_task.*

@AndroidEntryPoint
class AddNewTaskFragment : Fragment() {

    lateinit var binding:FragmentAddNewTaskBinding
    val taskViewModel:TaskViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            saveTaskToDatabase()
            requireFragmentManager().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = inflate(inflater)

        setLayoutFocus()

        binding.lifecycleOwner = this

        return binding.root
    }


    private fun setLayoutFocus() {
        binding.descLinearLayout.setOnClickListener {
            add_task_description.apply {
                showSoftKeyboard()
            }
        }
    }


    private fun saveTaskToDatabase(){
        val task = Task(
            taskTitle = add_task_title.text.toString(),
            taskDescription = add_task_description.text.toString()
        )
        taskViewModel.addTaskToDatabase(task)
    }


}