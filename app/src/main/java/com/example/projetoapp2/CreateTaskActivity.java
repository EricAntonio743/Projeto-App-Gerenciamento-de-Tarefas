package com.example.projetoapp2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateTaskActivity extends AppCompatActivity {
    private EditText etTaskName, etDeadline, etStatus, etPriority;
    private Button btnCreate;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        etTaskName = findViewById(R.id.etTaskName);
        etDeadline = findViewById(R.id.etDeadline);
        etStatus = findViewById(R.id.etStatus);
        etPriority = findViewById(R.id.etPriority);
        btnCreate = findViewById(R.id.btnCreate);

        // Inicializar o banco de dados
        db = openOrCreateDatabase("tasksDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, deadline TEXT, status TEXT, priority TEXT)");

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = etTaskName.getText().toString();
                String deadline = etDeadline.getText().toString();
                String status = etStatus.getText().toString();
                String priority = etPriority.getText().toString();

                ContentValues values = new ContentValues();
                values.put("name", taskName);
                values.put("deadline", deadline);
                values.put("status", status);
                values.put("priority", priority);

                db.insert("tasks", null, values);
                Toast.makeText(CreateTaskActivity.this, "Tarefa criada com sucesso", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CreateTaskActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
