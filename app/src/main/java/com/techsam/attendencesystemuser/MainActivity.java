package com.techsam.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techsam.attendencesystemuser.objects.Student;
import com.techsam.attendencesystemuser.objects.Teacher;

public class MainActivity extends AppCompatActivity {
    EditText username, password;

    boolean isuser = false;
    boolean isTeacher = false;
    ArrayAdapter<String> arrayAdapter;
    MaterialAutoCompleteTextView dropdown;
    public static String pusername;
    public static String pPassword;
    public static String userKey;


    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.editview1);
        password = findViewById(R.id.editview2);

        dropdown = findViewById(R.id.dropdownmenu);

        db = FirebaseDatabase.getInstance().getReference();



        String array[] = {"Admin","Teacher","Student"};
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,array);

        dropdown.setAdapter(arrayAdapter);



    }
    public void openregistration(View view) {
        startActivity(new Intent(MainActivity.this, Registration.class));
    }
    public void openlogin(View view) {

        String user = username.getText().toString();
        String pass = password.getText().toString();

        pusername = user;
        pPassword = pass;
        String dropDownText = dropdown.getText().toString();

        if (dropDownText.equals("Admin") &&(user.equals("admin") && pass.equals("admin123")) ){
            startActivity(new Intent(MainActivity.this, AdminMainscreen.class));
        }else if(dropDownText.equals("Student")){
            db.child("Students").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    for(DataSnapshot snap:task.getResult().getChildren()){
                        Student student = snap.getValue(Student.class);
                        if(student.getUser().equals(user)&&student.getPass().equals(pass)){
                            isuser = true;
                            userKey = student.getId();
                        }
                    }
                    if(isuser){
                        startActivity(new Intent(MainActivity.this,StudentMainscreen.class));
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid authentication", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else if(dropDownText.equals("Teacher")) {
            db.child("Teachers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    for (DataSnapshot snap : task.getResult().getChildren()) {
                        Teacher teacher = snap.getValue(Teacher.class);
                        if (teacher.getUser().equals(user) && teacher.getPass().equals(pass)) {
                            isTeacher = true;
                            userKey = teacher.getId();
                            break;
                        }
                    }
                    if (isTeacher) {
                        startActivity(new Intent(MainActivity.this, TeacherMainscreen.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid authentication", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else{
            Toast.makeText(this, "Invalid authentication", Toast.LENGTH_SHORT).show();
        }
    }
}