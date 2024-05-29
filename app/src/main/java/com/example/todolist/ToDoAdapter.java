package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoItemModel> todoList;
    private MainActivity mainActivity;

    public ToDoAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDoItemModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
    }
    public int getItemCount() {
        return todoList.size();
    }
    private boolean toBoolean(int n) {
        return n != 0;
    }

    public void setTasks(List<ToDoItemModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public TextView taskName;
        CheckBox task;

        ViewHolder(View itemView) {
            super(itemView);
            //taskName = itemView.findViewById(R.id.taskName);
            task = itemView.findViewById(R.id.todoCheckBox);//add binding here later
        }

    }
}
