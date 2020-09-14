package com.example.mob1032_assignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mob1032_assignment.R;
import com.example.mob1032_assignment.ultilities.ManagementDbHelper;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ManagementDbHelper dbHelper = new ManagementDbHelper(MainActivity.this);//tạo database
        moveToStudentManagement();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void moveToStudentManagement()
    {
        Intent intent = new Intent(MainActivity.this, StudentManagement.class);
        startActivity(intent);
    }

}
