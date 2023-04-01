package com.rabailalibhatti.attendencesystemuser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CurrentSemester extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    RecyclerView recyclerView;
    ArrayList<studentModel> studentModels;
    ArrayList<subjectModel> subjectModels;
    CurrentSemesterInformationRecyclerAdapter currentSemesterInformationRecyclerAdapter;

    private String studentKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_semester);

        if (getIntent().hasExtra("KEY")) {
            studentKey = getIntent().getStringExtra("KEY");
        }

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        subjectModels = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Records..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getStudentSemester(studentKey);
    }

    private void getStudentSemester(String stdKey) {
        FirebaseDatabase.getInstance().getReference("Students").child(stdKey).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    studentModel model = task.getResult().getValue(studentModel.class);
                    if (model != null && model.getKey().equals(stdKey)) {
                        getSemesterSubjects(model.getCurrentSemesterKey());
                    }
                }
                else {
                    Toast.makeText(CurrentSemester.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void getSemesterSubjects(String semKey) {
        FirebaseDatabase.getInstance().getReference("Subjects")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    for (DataSnapshot snap : task.getResult().getChildren()) {
                        subjectModel model = snap.getValue(subjectModel.class);
                        if (model != null && model.getSemesterKey().equals(semKey)){
                            subjectModels.add(model);
                        }
                    }
                    currentSemesterInformationRecyclerAdapter = new CurrentSemesterInformationRecyclerAdapter(CurrentSemester.this, subjectModels);
                    recyclerView.setAdapter(currentSemesterInformationRecyclerAdapter);
                }
                else {
                    Toast.makeText(CurrentSemester.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}