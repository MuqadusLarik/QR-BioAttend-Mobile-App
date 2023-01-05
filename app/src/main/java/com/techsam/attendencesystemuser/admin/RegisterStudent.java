package com.techsam.attendencesystemuser.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techsam.attendencesystemuser.R;
import com.techsam.attendencesystemuser.adapters.AddStudentSubjectAdapter;
import com.techsam.attendencesystemuser.objects.Student;
import com.techsam.attendencesystemuser.objects.Subject;

import java.util.ArrayList;

public class RegisterStudent extends AppCompatActivity {
    RecyclerView recyclerView;
    AddStudentSubjectAdapter adapter;
    ArrayList<Subject> list;

    public static ArrayList<Subject> checkBoxList;
    EditText editText,editText2,editText3,editText4,editText5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        recyclerView = findViewById(R.id.addstudentsubjectrecycler);
        list = new ArrayList<>();
        checkBoxList = new ArrayList<>();

        editText = findViewById(R.id.studentname);
        editText2 = findViewById(R.id.studentusername);
        editText3 = findViewById(R.id.studentpassword);
        editText4 = findViewById(R.id.roll);
        editText5 = findViewById(R.id.studentbatch);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Subjects").get().addOnCompleteListener(task -> {
            for(DataSnapshot snap:task.getResult().getChildren()){
                if(snap.exists()){
                    Subject subject = snap.getValue(Subject.class);
                    list.add(subject);

                }
            }
            adapter = new AddStudentSubjectAdapter(list, RegisterStudent.this);
            LinearLayoutManager llm = new LinearLayoutManager(RegisterStudent.this, RecyclerView.VERTICAL,false);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(adapter);
        });



    }

    public void submit(View view) {

        String name = editText.getText().toString();
        String username = editText2.getText().toString();
        String password = editText3.getText().toString();
        String rollNo = editText4.getText().toString();
        String batch = editText5.getText().toString();

        DatabaseReference db;
        db = FirebaseDatabase.getInstance().getReference();
        String id = db.child("Students").push().getKey();

        Student student = new Student();
        student.setName(name);
        student.setRollNo(rollNo);
        student.setBatch(batch);
        student.setUser(username);
        student.setPass(password);
        student.setList(checkBoxList);
        student.setId(id);


        db.child("Students").child(id).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                editText.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
                editText5.setText("");
                Toast.makeText(RegisterStudent.this, "Registered", Toast.LENGTH_SHORT).show();
            }
        });


    }
}