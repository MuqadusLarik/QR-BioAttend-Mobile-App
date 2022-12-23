package com.techsam.attendencesystemuser.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techsam.attendencesystemuser.R;
import com.techsam.attendencesystemuser.Student;
import com.techsam.attendencesystemuser.Teacher;

public class RegisterTeacher extends AppCompatActivity {

    EditText editText,editText2,editText3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_teacher);
        editText = findViewById(R.id.teachername);
        editText2 = findViewById(R.id.teacherusername);
        editText3 = findViewById(R.id.teacherpassword);
    }

    public void submit(View view) {

        String name = editText.getText().toString();
        String username = editText2.getText().toString();
        String password = editText3.getText().toString();


        DatabaseReference db;
        db= FirebaseDatabase.getInstance().getReference();
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setUsername(username);
        teacher.setPassword(password);


        db.child("Teachers").push().setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegisterTeacher.this, "teacher added", Toast.LENGTH_SHORT).show();
            }
        });




    }

}
