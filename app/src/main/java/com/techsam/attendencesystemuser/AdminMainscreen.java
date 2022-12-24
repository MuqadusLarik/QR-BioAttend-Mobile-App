package com.techsam.attendencesystemuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminMainscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
    }


    public void openstudentscreen(View view) {
        startActivity(new Intent(AdminMainscreen.this, Students.class));
    }

    public void openteachers(View view) {
        startActivity(new Intent(AdminMainscreen.this, Teachers.class));
    }

    public void openbatches(View view) {
        startActivity(new Intent(AdminMainscreen.this,Batches.class));
    }

    public void opensubjects(View view) {
        startActivity(new Intent(AdminMainscreen.this,Subjects.class));
    }
}