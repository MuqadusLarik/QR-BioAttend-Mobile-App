package com.techsam.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TeachersScreen extends AppCompatActivity {
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
                adapter = new TeacherRecyclerAdapter(list,TeachersScreen.this);
                LinearLayoutManager llm = new LinearLayoutManager(TeachersScreen.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}