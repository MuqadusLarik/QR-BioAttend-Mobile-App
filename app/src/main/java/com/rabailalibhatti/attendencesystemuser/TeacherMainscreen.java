package com.rabailalibhatti.attendencesystemuser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherMainscreen extends AppCompatActivity {

    TextView name;
    private String teacherKey;
    private String TAG = "dubai";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_mainscreen);
        name = findViewById(R.id.name);

        if (getIntent().hasExtra("KEY")) {
            teacherKey = getIntent().getStringExtra("KEY");
        }

        getName(teacherKey);

    }

    private void getName(String teaKey) {
        FirebaseDatabase.getInstance().getReference("Teachers").child(teaKey)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            teacherModel model = task.getResult().getValue(teacherModel.class);
                            if (model != null) {
                                name.setText("Welcome "+ model.getName());
                            }
                        }
                    }
                });
    }

    public void checkProfile(View view) {
        startIntent(TeacherProfile.class, "KEY", teacherKey);
    }

    public void checkSubjects(View view) {
        startIntent(SubjectAssignedCheck.class, "KEY", teacherKey);
    }

    public void takeAttendence(View view) {
        startIntent(Attendence_Subjects.class, "KEY", teacherKey);
    }

    public void checkAttendence(View view) {
        startIntent(attenDence_check.class, "KEY", teacherKey);
    }

    public void viewAttendanceReport(View view) {
        startIntent(ViewAttendanceReport.class, "KEY", teacherKey);
    }

    public void logout(View view) {
        cLearPrefs();
        startActivity(new Intent(TeacherMainscreen.this, MainActivity.class));
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

    public void startIntent(Class<?> cls, String input, String input_Text) {
        Intent intent = new Intent(TeacherMainscreen.this, cls);
        intent.putExtra(input, input_Text);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}