package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Students extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentRecyclerAdapter adapter;
    ArrayList<studentModel> list;

    MaterialToolbar materialToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_screen);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        recyclerView = findViewById(R.id.studentrecycler);
        list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Students").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot snap:task.getResult().getChildren()){
                    studentModel student = snap.getValue(studentModel.class);
                    if (student != null && !student.getPassedOut()){
                        list.add(student);
                    }
                }
                adapter = new StudentRecyclerAdapter(list);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void reg(View view) {
        startActivity(new Intent(Students.this, RegisterStudent.class));
    }

}