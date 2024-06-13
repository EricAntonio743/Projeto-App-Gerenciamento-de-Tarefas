package com.example.projetoapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    private Button btnCreateTask, btnViewTasks, btnEditDeleteTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnCreateTask = findViewById(R.id.btnCreateTask);
        btnViewTasks = findViewById(R.id.btnViewTasks);
        btnEditDeleteTasks = findViewById(R.id.btnEditDeleteTasks);

        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });

        btnViewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ViewTasksActivity.class);
                startActivity(intent);
            }
        });

        btnEditDeleteTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, EditDeleteTasksActivity.class);
                startActivity(intent);
            }
        });
    }
}