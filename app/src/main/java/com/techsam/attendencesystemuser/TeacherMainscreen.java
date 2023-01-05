package com.techsam.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techsam.attendencesystemuser.Screens.Teachers;
import com.techsam.attendencesystemuser.adapters.SubjectRecyclerAdapter;
import com.techsam.attendencesystemuser.adapters.TeacherRecyclerAdapter;
import com.techsam.attendencesystemuser.objects.Subject;
import com.techsam.attendencesystemuser.objects.Teacher;

import java.util.ArrayList;

public class TeacherMainscreen extends AppCompatActivity {
    RecyclerView recyclerView;
    SubjectRecyclerAdapter adapter;
    ArrayList<Subject> list;
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_mainscreen);
        recyclerView = findViewById(R.id.recycler_teacherportal);
        list = new ArrayList<>();
        welcome = findViewById(R.id.welcome);


        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Teachers").child(MainActivity.userKey).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                    Teacher teacher = task.getResult().getValue(Teacher.class);
                    welcome.setText("Welcome "+teacher.getName());

                    for(Subject subject:teacher.getList()){
                        list.add(subject);
                    }

                adapter = new SubjectRecyclerAdapter(list, TeacherMainscreen.this);
                LinearLayoutManager llm = new LinearLayoutManager(TeacherMainscreen.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);

            }

        });
    }
}