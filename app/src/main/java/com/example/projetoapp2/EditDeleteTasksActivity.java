package com.example.projetoapp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class EditDeleteTasksActivity extends AppCompatActivity {
    private ListView lvTasksEditDelete;
    private Button btnBackEditDelete;
    private SQLiteDatabase db;
    private ArrayList<String> tasksList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_tasks);

        lvTasksEditDelete = findViewById(R.id.lvTasksEditDelete);
        btnBackEditDelete = findViewById(R.id.btnBackEditDelete);

        db = openOrCreateDatabase("tasksDB", MODE_PRIVATE, null);
        tasksList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);
        if (cursor.moveToFirst()) {
            do {
                String task = cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getString(2) + " - " + cursor.getString(3) + " - " + cursor.getString(4);
                tasksList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);
        lvTasksEditDelete.setAdapter(adapter);

        lvTasksEditDelete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTask = tasksList.get(position);
                String[] taskDetails = selectedTask.split(" - ");
                final String taskId = taskDetails[0];

                // Criar um AlertDialog para oferecer opções de edição ou deleção
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDeleteTasksActivity.this);
                builder.setTitle("Opções");
                builder.setMessage("Escolha uma opção:");

                // Adicionar botão para editar tarefa
                builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EditDeleteTasksActivity.this, EditTaskActivity.class);
                        intent.putExtra("taskId", taskId);
                        startActivity(intent);
                    }
                });

                // Adicionar botão para deletar tarefa
                builder.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Implementar a lógica para deletar a tarefa
                        db.delete("tasks", "id=?", new String[]{taskId});
                        // Atualizar a lista após deletar a tarefa
                        refreshTaskList();
                    }
                });

                // Exibir o AlertDialog
                builder.create().show();
            }
        });

        btnBackEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditDeleteTasksActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void refreshTaskList() {
        tasksList.clear();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);
        if (cursor.moveToFirst()) {
            do {
                String task = cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getString(2) + " - " + cursor.getString(3) + " - " + cursor.getString(4);
                tasksList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}