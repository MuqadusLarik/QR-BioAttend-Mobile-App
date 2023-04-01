package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rabailalibhatti.attendencesystemuser.R;
import com.rabailalibhatti.attendencesystemuser.studentModel;
import com.rabailalibhatti.attendencesystemuser.SubjectsStudentRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SubjectAssignedStudentCheck extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    RecyclerView recyclerView;
    ArrayList<studentModel> studentModels;
    SubjectsStudentRecyclerAdapter subjectsStudentRecyclerAdapter;

    private String semesterKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_assigned_student_check);

        if (getIntent().hasExtra("SEMKEY")){
            semesterKey = getIntent().getStringExtra("SEMKEY");
        }

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Records..");
        progressDialog.setCancelable(false);

        recyclerView = findViewById(R.id.recyclerView);
        studentModels = new ArrayList<>();

        getStudents(semesterKey);

    }
    private void getStudents(String semeKey) {
        FirebaseDatabase.getInstance().getReference("Students").orderByChild("currentSemesterKey").equalTo(semeKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (!snap.getValue(studentModel.class).getPassedOut()){
                        studentModels.add(snap.getValue(studentModel.class));
                    }
                }

                // Sort the ArrayList using a custom Comparator
                Collections.sort(studentModels, new Comparator<studentModel>() {
                    @Override
                    public int compare(studentModel s1, studentModel s2) {
                        return s1.getName().compareTo(s2.getName());
                    }
                });

                showData(studentModels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showData(ArrayList<studentModel> arrayList) {
        subjectsStudentRecyclerAdapter = new SubjectsStudentRecyclerAdapter( arrayList);
        recyclerView.setAdapter(subjectsStudentRecyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}