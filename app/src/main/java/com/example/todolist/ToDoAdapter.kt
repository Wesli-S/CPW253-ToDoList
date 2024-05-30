package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(private val db: DbHandler, private val mainActivity: MainActivity) :
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    private var todoList: List<ToDoItemModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()
        val item = todoList[position]
        holder.task.text = item.task
        holder.task.isChecked = item.status != 0
        holder.task.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                db.updateTaskStatus(item.id, 1)
            } else {
                db.updateTaskStatus(item.id, 0)
            }
        }
    }

    override fun getItemCount(): Int = todoList.size

    private fun toBoolean(n: Int): Boolean = n != 0

    fun setTasks(todoList: List<ToDoItemModel>) {
        this.todoList = todoList
    }

    fun getContext(): Context = mainActivity

    fun deleteTask(position: Int) {
        val item = todoList[position]
        db.deleteTask(item.id)
        todoList.drop(position)
        notifyItemRemoved(position)
    }

    fun editTask(position: Int) {
        val item = todoList[position]
        val bundle = Bundle().apply {
            putInt("id", item.id)
            putString("task", item.task)
        }
        val fragment = AddNewToDoTask().apply {
            arguments = bundle
        }
        fragment.show(mainActivity.supportFragmentManager, AddNewToDoTask.TAG)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task: CheckBox = itemView.findViewById(R.id.todoCheckBox)
    }
}