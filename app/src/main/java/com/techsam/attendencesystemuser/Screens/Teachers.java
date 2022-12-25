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
import com.techsam.attendencesystemuser.adapters.TeacherRecyclerAdapter;
import com.techsam.attendencesystemuser.admin.RegisterTeacher;
import com.techsam.attendencesystemuser.objects.Teacher;

import java.util.ArrayList;

public class Teachers extends AppCompatActivity {
    RecyclerView recyclerView;
    TeacherRecyclerAdapter adapter;
    ArrayList<Teacher> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_screen);
        recyclerView = findViewById(R.id.teacherrecycler);
        list = new ArrayList<>();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Teachers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot snap:task.getResult().getChildren()){
                    Teacher teacher = snap.getValue(Teacher.class);
                    list.add(teacher);
                }
                adapter = new TeacherRecyclerAdapter(list, Teachers.this);
                LinearLayoutManager llm = new LinearLayoutManager(Teachers.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void reg(View view) {
        startActivity(new Intent(Teachers.this, RegisterTeacher.class));
    }
}