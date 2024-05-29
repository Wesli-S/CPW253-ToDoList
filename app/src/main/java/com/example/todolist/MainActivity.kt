package com.example.todolist

import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class MainActivity : AppCompatActivity(), DialogCloseListener {
    private lateinit var taskView: RecyclerView
    private lateinit var taskAdapter: ToDoAdapter
    private lateinit var taskList: MutableList<ToDoItemModel>
    private lateinit var db: DbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DbHandler(this)
        db.openDatabase()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        taskView = findViewById(R.id.ToDoRecyclerView)
        taskView.layoutManager = LinearLayoutManager(this)
        taskAdapter = ToDoAdapter(this)
        taskView.adapter = taskAdapter

        taskList = db.getAllTasks()
        Collections.reverse(taskList)
        taskAdapter.setTasks(taskList)
    }

    override fun handleDialogClose(dialog: DialogInterface) {
        taskList = db.getAllTasks()
        Collections.reverse(taskList)
        taskAdapter.setTasks(taskList)
        taskAdapter.notifyDataSetChanged()
    }
}