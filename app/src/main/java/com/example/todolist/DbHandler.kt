package com.example.todolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHandler(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val VERSION = 1
        private const val NAME = "ToDoList.db"
        private const val TABLE = "todo"
        private const val ID = "id"
        private const val TASK = "task"
        private const val STATUS = "status"
        private const val CREATE_TABLE =
            "CREATE TABLE $TABLE ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $TASK TEXT, $STATUS INTEGER)"
    }

    private var db: SQLiteDatabase? = null

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    fun openDatabase() {
        db = this.writableDatabase
    }

    fun addTask(task: ToDoItemModel) {
        val values = ContentValues().apply {
            put(TASK, task.task)
            put(STATUS, task.status)
        }
        db?.insert(TABLE, null, values)
    }

    @SuppressLint("Range")
    fun getAllTasks(): MutableList<ToDoItemModel> {
        val tasks = mutableListOf<ToDoItemModel>()
        val cursor = db?.query(TABLE, null, null, null,
            null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val task = ToDoItemModel().apply {
                        id = it.getInt(it.getColumnIndex(ID))
                        task = it.getString(it.getColumnIndex(TASK))
                        status = it.getInt(it.getColumnIndex(STATUS))
                    }
                    tasks.add(task)
                } while (it.moveToNext())
            }
        }

        return tasks
    }

    fun updateTaskStatus(id: Int, status: Int) {
        val values = ContentValues().apply {
            put(STATUS, status)
        }
        db?.update(TABLE, values, "$ID=?", arrayOf(id.toString()))
    }

    fun updateTask(id: Int, task: String) {
        val values = ContentValues().apply {
            put(TASK, task)
        }
        db?.update(TABLE, values, "$ID=?", arrayOf(id.toString()))
    }

    fun deleteTask(id: Int) {
        db?.delete(TABLE, "$ID=?", arrayOf(id.toString()))
    }
}