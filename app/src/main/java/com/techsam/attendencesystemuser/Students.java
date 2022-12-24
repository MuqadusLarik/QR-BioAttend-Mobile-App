package com.techsam.attendencesystemuser;

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
import com.techsam.attendencesystemuser.adapters.StudentRecyclerAdapter;
import com.techsam.attendencesystemuser.admin.RegisterStudent;
import com.techsam.attendencesystemuser.models.Student;

import java.util.ArrayList;

public class Students extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentRecyclerAdapter adapter;
    ArrayList<Student> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_screen);
        recyclerView = findViewById(R.id.studentrecycler);
        list = new ArrayList<>();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Students").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot snap:task.getResult().getChildren()){
                    Student student = snap.getValue(Student.class);
                    list.add(student);
                }
                adapter = new StudentRecyclerAdapter(list, Students.this);
                LinearLayoutManager llm = new LinearLayoutManager(Students.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void reg(View view) {
        startActivity(new Intent(Students.this, RegisterStudent.class));
    }

}