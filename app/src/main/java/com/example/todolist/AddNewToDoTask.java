package com.example.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewToDoTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionButtonDialog";

    private EditText newTaskText;
    private Button saveNewTask;
    private DbHandler db;


    public static AddNewToDoTask newInstance() {
        return new AddNewToDoTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.newTaskText);
        saveNewTask = getView().findViewById(R.id.newTaskButton);

        db = new DbHandler(getActivity());
        db.openDatabase();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            if (task.length() > 0){
                saveNewTask.setTextColor
                        (ContextCompat.getColor(getContext(),
                                com.google.android.material.R.color.
                                        design_default_color_primary_dark));
            }
        }
        saveNewTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    saveNewTask.setEnabled(false);
                    saveNewTask.setTextColor(ContextCompat.getColor(getContext(),
                            com.google.android.material.R.color.material_grey_300));
                }
                else{
                    saveNewTask.setEnabled(true);
                    saveNewTask.setTextColor(ContextCompat.getColor(getContext(),
                            com.google.android.material.R.color.design_default_color_primary_dark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        boolean finalIsUpdate = isUpdate;
        saveNewTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String text = newTaskText.getText().toString();
                if (finalIsUpdate){
                    db.updateTask(bundle.getInt("id"), text);
                }
                else{
                    ToDoItemModel task = new ToDoItemModel();
                    task.setTask(text);
                    task.setStatus(0);
                }
                dismiss();
            }
        });
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}
