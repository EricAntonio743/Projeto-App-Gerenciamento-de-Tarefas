package com.example.projetoapp2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewTasksActivity extends AppCompatActivity {
    private ListView lvTasks;
    private Button btnBack;
    private SQLiteDatabase db;
    private ArrayList<String> tasksList;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        lvTasks = findViewById(R.id.lvTasks);
        btnBack = findViewById(R.id.btnBack);

        db = openOrCreateDatabase("tasksDB", MODE_PRIVATE, null);
        tasksList = new ArrayList<>();

        refreshTaskList();

        adapter = new CustomAdapter(this, tasksList, db);
        lvTasks.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTasksActivity.this, DashboardActivity.class);
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
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
