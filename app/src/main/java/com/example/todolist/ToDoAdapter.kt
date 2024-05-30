package com.example.todolist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoItemModel> todoList;
    private MainActivity mainActivity;
    private DbHandler db;

    public ToDoAdapter(DbHandler db, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.db = db;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();
        ToDoItemModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateTaskStatus(item.getID(), 1);
                } else {
                    db.updateTaskStatus(item.getID(), 0);
                }
            }
        });
    }
    public int getItemCount() {
        return todoList.size();
    }
    private boolean toBoolean(int n) {
        return n != 0;
    }

    public void setTasks(List<ToDoItemModel> todoList) {
        this.todoList = todoList;
    }
    public Context getContext() {
        return mainActivity;
    }
    public void editTask(int position) {
        ToDoItemModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getID());
        bundle.putString("task", item.getTask());
        AddNewToDoTask fragment = new AddNewToDoTask();
        fragment.setArguments(bundle);
        fragment.show(mainActivity.getSupportFragmentManager(), AddNewToDoTask.TAG);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        ViewHolder(View itemView) {
            super(itemView);
            //taskName = itemView.findViewById(R.id.taskName);
            task = itemView.findViewById(R.id.todoCheckBox);//add binding here later
        }

    }
}
