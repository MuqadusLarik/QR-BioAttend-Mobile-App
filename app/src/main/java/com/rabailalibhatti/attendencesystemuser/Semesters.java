package com.rabailalibhatti.attendencesystemuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Semesters extends AppCompatActivity {
    RecyclerView recyclerView;
    SemesterRecyclerAdapter adapter;
    ArrayList<semesterModel> list;
    MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesters);
        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        recyclerView = findViewById(R.id.semesterrecycler);
        list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Semesters").orderByChild("semNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    list.add(snap.getValue(semesterModel.class));
                }
                adapter = new SemesterRecyclerAdapter(list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addSemester(View view) {
        startActivity(new Intent(Semesters.this, AddSemesters.class));
    }
}