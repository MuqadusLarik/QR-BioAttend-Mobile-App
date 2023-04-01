package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Subjects extends AppCompatActivity {
    RecyclerView recyclerView;
    SubjectRecyclerAdapter adapter;
    ArrayList<subjectModel> list;

    MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_screen);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        recyclerView = findViewById(R.id.subjectsrecycler);
        list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Subjects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() != 0){
                    for(DataSnapshot snap:snapshot.getChildren()){
                        subjectModel subj = snap.getValue(subjectModel.class);
                        list.add(subj);
                    }
                    adapter = new SubjectRecyclerAdapter(list);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addsubject(View view) {
        startActivity(new Intent(Subjects.this, AddSubjects.class));
    }
}

