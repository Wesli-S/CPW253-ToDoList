package com.example.todolist;

public class ToDoItemModel {
    //id: The id number of each task
    //status: The status of each task (Boolean value(0=true, 1=false))
    //task: The task itself typed in by user
    private int id, status;
    private String task;

    public void setID(int id) {
       this.id = id;
    }
    public int getID() {
        return id;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public String getTask() {
        return task;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }
}
