package com.example.todolist

import android.app.Activity
import android.content.DialogInterface
import android.database.DatabaseErrorHandler
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNewToDoTask : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "ActionButtonDialog"

        fun newInstance(): AddNewToDoTask {
            return AddNewToDoTask()
        }
    }

    private lateinit var newTaskText: EditText
    private lateinit var saveNewTask: Button
    private lateinit var db: DbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_task, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newTaskText = view.findViewById(R.id.newTaskText)
        saveNewTask = view.findViewById(R.id.newTaskButton)

        db = DbHandler(requireActivity())
        db.openDatabase()

        var isUpdate = false
        val bundle = arguments
        if (bundle != null) {
            isUpdate = true
            val task = bundle.getString("task")
            newTaskText.setText(task)
            if (task.isNullOrEmpty()) {
                saveNewTask.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.google.android.material.R.color.design_default_color_primary_dark
                    )
                )
            }
        }

        saveNewTask.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    saveNewTask.isEnabled = false
                    saveNewTask.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            com.google.android.material.R.color.material_grey_300
                        )
                    )
                } else {
                    saveNewTask.isEnabled = true
                    saveNewTask.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            com.google.android.material.R.color.design_default_color_primary_dark
                        )
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        saveNewTask.setOnClickListener {
            val text = newTaskText.text.toString()
            if (isUpdate) {
                db.updateTask(bundle!!.getInt("id"), text)
            } else {
                val task = ToDoItemModel().apply {
                    this.task = text
                    this.status = 0
                }
                db.addTask(task) // Add this line to add the new task to the database
            }
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity = requireActivity()
        if (activity is DialogCloseListener) {
            activity.handleDialogClose(dialog)
        }
    }
}