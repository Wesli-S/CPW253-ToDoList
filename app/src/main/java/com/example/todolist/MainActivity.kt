package com.example.todolist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var taskView: RecyclerView
    private lateinit var taskAdapter: ToDoAdapter
    private lateinit var taskList: MutableList<ToDoItemModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        taskList = ArrayList();

        taskView = findViewById(R.id.ToDoRecyclerView)
        taskView.layoutManager = LinearLayoutManager(this)
        taskAdapter = ToDoAdapter(this)
        taskView.setAdapter(taskAdapter)


        taskList = mutableListOf()//initialize list

        //add default list item test
        val task = ToDoItemModel()
        task.task= "This is a test task"
        task.status = 0
        task.id = 1

        taskList.add(task)
        taskList.add(task)
        taskList.add(task)
        taskList.add(task)
        taskList.add(task)

        taskAdapter.setTasks(taskList)
    }
}