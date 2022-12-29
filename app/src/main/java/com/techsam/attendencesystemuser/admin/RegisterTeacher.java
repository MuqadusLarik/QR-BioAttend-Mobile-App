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
import com.techsam.attendencesystemuser.adapters.AddTeacherSubjectAdapter;
import com.techsam.attendencesystemuser.objects.Subject;
import com.techsam.attendencesystemuser.objects.Teacher;

import java.util.ArrayList;

public class RegisterTeacher extends AppCompatActivity {
    RecyclerView recyclerView;
    AddTeacherSubjectAdapter adapter;
    ArrayList<Subject> list;

    public static ArrayList<Subject> checkBoxList;
    EditText editText,editText2,editText3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_teacher);

        recyclerView = findViewById(R.id.addsubjectrecycler);
        list = new ArrayList<>();
        checkBoxList = new ArrayList<>();

        editText = findViewById(R.id.teachername);
        editText2 = findViewById(R.id.teacherusername);
        editText3 = findViewById(R.id.teacherpassword);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Subjects").get().addOnCompleteListener(task -> {
            for(DataSnapshot snap:task.getResult().getChildren()){
                if(snap.exists()){
                    Subject subject = snap.getValue(Subject.class);
                    list.add(subject);

                }
            }
            adapter = new AddTeacherSubjectAdapter(list, RegisterTeacher.this);
            LinearLayoutManager llm = new LinearLayoutManager(RegisterTeacher.this, RecyclerView.VERTICAL,false);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(adapter);
        });


    }

    public void submit(View view) {

        String name = editText.getText().toString();
        String username = editText2.getText().toString();
        String password = editText3.getText().toString();



        DatabaseReference db;
        db= FirebaseDatabase.getInstance().getReference();
        String id = db.child("Teachers").push().getKey();

        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setUser(username);
        teacher.setPass(password);
        teacher.setId(id);
        teacher.setList(checkBoxList);
;


        db.child("Teachers").child(id).setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                editText.setText("");
                editText2.setText("");
                editText3.setText("");
                Toast.makeText(RegisterTeacher.this, "Teacher Added", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
