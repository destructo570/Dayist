package com.destructo.dayist.ui.editTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.destructo.dayist.R
import com.destructo.dayist.databinding.FragmentEditBinding
import com.destructo.dayist.repository.Task
import com.destructo.dayist.repository.TaskDatabase
import com.destructo.dayist.util.showSoftKeyboard
import com.destructo.dayist.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_new_task.*
import kotlinx.android.synthetic.main.fragment_edit.*

@AndroidEntryPoint
class EditFragment : Fragment() {

    val taskViewModel:TaskViewModel by viewModels()
    lateinit var editTaskArg: Task
    lateinit var binding:FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentEditBinding.inflate(inflater)

        setLayoutFocus()

         editTaskArg = EditFragmentArgs.fromBundle(requireArguments()
        ).editTask

        binding.apply {
            editTaskTitle.setText(editTaskArg.taskTitle)
            editTaskDescription.setText(editTaskArg.taskDescription)
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            updateTaskInDatabase()
            requireFragmentManager().popBackStack()
        }
    }

    private fun updateTaskInDatabase() {

        val updatedTask = Task(
            taskId = editTaskArg.taskId,
            taskTitle = binding.editTaskTitle.text.toString(),
            taskDescription = binding.editTaskDescription.text.toString()
        )
        taskViewModel.updateTaskInDatabase(updatedTask)
    }


    private fun setLayoutFocus() {
        binding.editDescLinearLayout.setOnClickListener {
            edit_task_description.apply {
                showSoftKeyboard()
            }
        }
    }

}