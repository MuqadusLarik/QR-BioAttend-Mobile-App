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

public class Teachers extends AppCompatActivity {
    RecyclerView recyclerView;
    TeacherRecyclerAdapter adapter;
    ArrayList<teacherModel> list;

    MaterialToolbar materialToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_screen);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        recyclerView = findViewById(R.id.teacherrecycler);
        list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Teachers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot snap : task.getResult().getChildren()) {
                    teacherModel teacher = snap.getValue(teacherModel.class);
                    list.add(teacher);
                }
                adapter = new TeacherRecyclerAdapter(list);
                recyclerView.setAdapter(adapter);

                }

        });
    }

    public void reg(View view) {
        startActivity(new Intent(Teachers.this, RegisterTeacher.class));
    }
}