package com.rabailalibhatti.attendencesystemuser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
        startActivity(new Intent(AdminMainscreen.this, Batches.class));
    }

    public void opensubjects(View view) {
        startActivity(new Intent(AdminMainscreen.this, Subjects.class));
    }

    public void opensemesters(View view) {
        startActivity(new Intent(AdminMainscreen.this, Semesters.class));
    }

    public void assignSubjects(View view) {
        startActivity(new Intent(AdminMainscreen.this, AssignedSubjects.class));
    }

    public void attendenceModule(View view) {
        startActivity(new Intent(AdminMainscreen.this, AttendenceReportModule.class));
    }

    public void promoteStudents(View view) {
        startActivity(new Intent(AdminMainscreen.this, PromoteStudents.class));
    }


    public void logout(View view) {
        cLearPrefs();
        startActivity(new Intent(AdminMainscreen.this, MainActivity.class));
        finish();
    }

    private void cLearPrefs() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("isLoggedIn");
        editor.remove("type");
        editor.remove("key");
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}