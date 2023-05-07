package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailTaskActivity extends AppCompatActivity {
    TextView taskTitle, taskDeadline, taskDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        taskTitle = findViewById(R.id.taskTitle);
        taskDeadline = findViewById(R.id.taskDeadline);
        taskDescription = findViewById(R.id.taskDescription);

        // Nangkep objeck task yang di pass dari intent
        Task task = getIntent().getParcelableExtra("TASK");

        // Set nilai setiap elemen
        taskTitle.setText(task.getTitle());
        taskDeadline.setText(task.getDeadline());
        taskDescription.setText(task.getDescription());
    }
}