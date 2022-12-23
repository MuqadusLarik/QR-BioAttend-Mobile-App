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

public class RegisterStudent extends AppCompatActivity {
    EditText editText,editText2,editText3,editText4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        editText = findViewById(R.id.studentname);
        editText2 = findViewById(R.id.surname);
        editText3 = findViewById(R.id.cnic);
        editText4 = findViewById(R.id.roll);

    }

    public void submit(View view) {

        String name = editText.getText().toString();
        String surname = editText2.getText().toString();
        String rollNo = editText3.getText().toString();
        String cnic = editText4.getText().toString();

        Student student = new Student();
        student.setName(name);
        student.setSurname(surname);
        student.setUsername(rollNo);
        student.setPassword(cnic);

        DatabaseReference db;
        db = FirebaseDatabase.getInstance().getReference();
        db.child("Students").push().setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                editText.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
                Toast.makeText(RegisterStudent.this, "Data submitted", Toast.LENGTH_SHORT).show();
            }
        });



    }
}