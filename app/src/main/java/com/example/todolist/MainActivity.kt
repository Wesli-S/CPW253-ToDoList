package com.example.todolist

import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), DialogCloseListener {
    private lateinit var taskView: RecyclerView
    private lateinit var taskAdapter: ToDoAdapter
    private lateinit var addButton: FloatingActionButton

    private lateinit var taskList: MutableList<ToDoItemModel>
    private lateinit var db: DbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DbHandler(this)
        db.openDatabase()
        db.getAllTasks()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        taskView = findViewById(R.id.ToDoRecyclerView)
        taskView.layoutManager = LinearLayoutManager(this)
        taskAdapter = ToDoAdapter(db, this)
        taskView.adapter = taskAdapter

        ToDoItemModel().apply {
            task = "Test"
            status = 0
            id = 1
        }

        addButton = findViewById(R.id.fab)

        val itemTouchHelper = ItemTouchHelper(Recycler(taskAdapter))
        itemTouchHelper.attachToRecyclerView(taskView)

        taskList = db.getAllTasks()
        taskList.reversed()
        taskAdapter.setTasks(taskList)

        addButton.setOnClickListener {
            AddNewToDoTask.newInstance().show(supportFragmentManager, AddNewToDoTask.TAG)
        }

    }

    override fun handleDialogClose(dialog: DialogInterface?) {
        taskList = db.getAllTasks()
        taskList.reversed()
        taskAdapter.setTasks(taskList)
        taskAdapter.notifyDataSetChanged()
    }

}