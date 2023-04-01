package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rabailalibhatti.attendencesystemuser.R;
import com.rabailalibhatti.attendencesystemuser.subjectModel;
import com.rabailalibhatti.attendencesystemuser.SubjectsCheckRecyclerAdapter;

import java.util.ArrayList;

public class SubjectAssignedCheck extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    RecyclerView recyclerView;
    ArrayList<subjectModel> subjectChecks;
    SubjectsCheckRecyclerAdapter subjectsCheckRecyclerAdapter;

    private String teacherKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_assigned_check);

        if (getIntent().hasExtra("KEY")){
            teacherKey = getIntent().getStringExtra("KEY");
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
        subjectChecks = new ArrayList<>();

        getAssignedRecords(teacherKey);

    }
    private void getAssignedRecords(String teacherKey) {
        FirebaseDatabase.getInstance().getReference("Subjects").orderByChild("assignedTo").equalTo(teacherKey)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot subjectSnapshot : snapshot.getChildren()) {
                                subjectModel sub = subjectSnapshot.getValue(subjectModel.class);
                                subjectChecks.add(sub);
                            }
                            showData(subjectChecks);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showData(ArrayList<subjectModel> arrayList) {
        subjectsCheckRecyclerAdapter = new SubjectsCheckRecyclerAdapter(this, arrayList);
        recyclerView.setAdapter(subjectsCheckRecyclerAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}