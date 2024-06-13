package com.example.projetoapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> tasksList;
    private SQLiteDatabase db;

    public CustomAdapter(Context context, ArrayList<String> tasksList, SQLiteDatabase db) {
        this.context = context;
        this.tasksList = tasksList;
        this.db = db;
    }

    @Override
    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Object getItem(int position) {
        return tasksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        }

        TextView tvTask = convertView.findViewById(R.id.tvTask);
        Button btnCompleteTask = convertView.findViewById(R.id.btnCompleteTask);

        String task = tasksList.get(position);
        tvTask.setText(task);

        btnCompleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] taskDetails = task.split(" - ");
                String taskId = taskDetails[0];

                // Alterar o status da tarefa para "Concluído"
                ContentValues values = new ContentValues();
                values.put("status", "Concluído");
                db.update("tasks", values, "id=?", new String[]{taskId});

                // Atualizar a lista após concluir a tarefa
                refreshTaskList();
            }
        });

        return convertView;
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
        notifyDataSetChanged();
    }
}