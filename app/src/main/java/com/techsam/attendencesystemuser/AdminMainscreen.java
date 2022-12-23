package com.techsam.attendencesystemuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.techsam.attendencesystemuser.admin.RegisterStudent;
import com.techsam.attendencesystemuser.admin.RegisterTeacher;

public class AdminMainscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
    }

    public void registerstudents(View view) {
        startActivity(new Intent(AdminMainscreen.this, RegisterStudent.class));

    }

    public void openstudentscreen(View view) {
        startActivity(new Intent(AdminMainscreen.this,StudentScreen.class));
    }

    public void teacherregistration(View view) {
        startActivity(new Intent(AdminMainscreen.this,RegisterTeacher.class));
    }

    public void openteachers(View view) {
        startActivity(new Intent(AdminMainscreen.this,TeachersScreen.class));
    }
}