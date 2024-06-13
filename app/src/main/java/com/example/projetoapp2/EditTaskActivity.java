package com.example.projetoapp2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {
    private EditText etTaskName, etDeadline, etStatus, etPriority;
    private Button btnUpdate;
    private SQLiteDatabase db;
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        etTaskName = findViewById(R.id.etTaskName);
        etDeadline = findViewById(R.id.etDeadline);
        etStatus = findViewById(R.id.etStatus);
        etPriority = findViewById(R.id.etPriority);
        btnUpdate = findViewById(R.id.btnUpdate);

        taskId = getIntent().getStringExtra("taskId");

        // Inicializar o banco de dados
        db = openOrCreateDatabase("tasksDB", MODE_PRIVATE, null);

        // Preencher os campos com as informações da tarefa selecionada
        Cursor cursor = db.rawQuery("SELECT * FROM tasks WHERE id=?", new String[]{taskId});
        if (cursor.moveToFirst()) {
            etTaskName.setText(cursor.getString(1));
            etDeadline.setText(cursor.getString(2));
            etStatus.setText(cursor.getString(3));
            etPriority.setText(cursor.getString(4));
        }
        cursor.close();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Atualizar as informações da tarefa no banco de dados
                String taskName = etTaskName.getText().toString();
                String deadline = etDeadline.getText().toString();
                String status = etStatus.getText().toString();
                String priority = etPriority.getText().toString();

                ContentValues values = new ContentValues();
                values.put("name", taskName);
                values.put("deadline", deadline);
                values.put("status", status);
                values.put("priority", priority);

                db.update("tasks", values, "id=?", new String[]{taskId});

                // Retornar para a atividade anterior
                finish();
            }
        });
    }
}
