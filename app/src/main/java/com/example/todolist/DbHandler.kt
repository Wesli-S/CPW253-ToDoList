package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

//Create a DB to store ToDo data
//https://developer.android.com/training/data-storage/sqlite
public class DbHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "ToDoList.db";//Name of the database
    private static final String TABLE = "todo";//Name of the table
    private static final String ID = "id";//Primary key
    private static final String TASK = "task";//Text
    private static final String STATUS = "status";//Status
    private static final String CREATE_TABLE ="CREATE TABLE " +
    TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK + " TEXT, " +
                STATUS + " INTEGER)";
    private SQLiteDatabase db;

    public DbHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);//executes SQL
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);//Drops the older table
        onCreate(db);//adds the table back again with updated data
    }
    public void openDatabase(){
        db = this.getWritableDatabase();
    }
    public void addTask(ToDoItemModel task){
        ContentValues values = new ContentValues();
        values.put(TASK, task.getTask());
        values.put(STATUS, task.getStatus());
        db.insert(TABLE, null, values);
    }

    @SuppressLint("Range")
    public List<ToDoItemModel> getAllTasks(){
        List<ToDoItemModel> tasks = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try{//Should return all rows from db
            cursor = db.query(TABLE,
                    null, null, null,
                    null, null, null, null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do {
                        ToDoItemModel task = new ToDoItemModel();
                        task.setID(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        tasks.add(task);
                    } while(cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return tasks;
    }

    public void updateTaskStatus(int id, int status){
        ContentValues values = new ContentValues();
        values.put(STATUS, status);
        db.update(TABLE, values, ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateTask(int id, String task){
        ContentValues values = new ContentValues();
        values.put(TASK, task);
        db.update(TABLE, values, ID + "=?", new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id){
        db.delete(TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }

}
