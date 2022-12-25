package com.techsam.attendencesystemuser.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techsam.attendencesystemuser.R;
import com.techsam.attendencesystemuser.adapters.SubjectRecyclerAdapter;
import com.techsam.attendencesystemuser.admin.AddSubjects;
import com.techsam.attendencesystemuser.objects.Subject;

import java.util.ArrayList;

public class Subjects extends AppCompatActivity {
    RecyclerView recyclerView;
    SubjectRecyclerAdapter adapter;
    ArrayList<Subject> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_screen);
        recyclerView = findViewById(R.id.subjectsrecycler);
        list = new ArrayList<>();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Subjects").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot snap:task.getResult().getChildren()){
                    Subject subject = snap.getValue(Subject.class);
                    list.add(subject);
                }
                adapter = new SubjectRecyclerAdapter(list, Subjects.this);
                LinearLayoutManager llm = new LinearLayoutManager(Subjects.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void addsubject(View view) {
        startActivity(new Intent(Subjects.this, AddSubjects.class));
    }
}

