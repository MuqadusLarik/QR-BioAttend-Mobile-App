package com.rabailalibhatti.attendencesystemuser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class StudentMainscreen extends AppCompatActivity {
    ImageView img;
    TextView name;
    private String studentKey;
    private String semesterKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mainscreen);

        //        img = findViewById(R.id.generatedqr);
        name = findViewById(R.id.name);

        if (getIntent().hasExtra("KEY")) {
            studentKey = getIntent().getStringExtra("KEY");
        }

        getName(studentKey);

    }

    private void getName(String stuKey) {
        FirebaseDatabase.getInstance().getReference("Students").child(stuKey)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            studentModel model = task.getResult().getValue(studentModel.class);
                            if (model != null) {
                                name.setText("Welcome " + model.getName());
                            }
                        }
                    }
                });
    }

    public void logout(View view) {
        cLearPrefs();
        startActivity(new Intent(StudentMainscreen.this, MainActivity.class));
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

    public void checkAttendence(View view) {
        startIntent(checkAttendenceStudent.class, "KEY", studentKey);
    }

    public void checkProfile(View view) {
        startIntent(StudentProfile.class, "KEY", studentKey);
    }

    public void checkCurrent(View view) {
        startIntent(CurrentSemester.class, "KEY", studentKey);
    }

    public void startIntent(Class<?> cls, String input, String input_Text) {
        Intent intent = new Intent(StudentMainscreen.this, cls);
        intent.putExtra(input, input_Text);
        startActivity(intent);
    }

    public void showQR(View view) {
        startIntent(showQR.class, "KEY", studentKey);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}