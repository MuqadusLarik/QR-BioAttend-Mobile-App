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
import com.techsam.attendencesystemuser.objects.Subject;

public class AddSubjects extends AppCompatActivity {

    EditText SubName,SubCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjects);
        SubName= findViewById(R.id.subname);
        SubCode= findViewById(R.id.subcode);
    }

    public void submit(View view) {
        String subname=SubName.getText().toString();
        String subcode=SubCode.getText().toString();

        DatabaseReference db;
        db= FirebaseDatabase.getInstance().getReference();

        Subject subject =new Subject();
        subject.setSubName(subname);
        subject.setSubCode(subcode);

        db.child("Subjects").push().setValue(subject).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                SubName.setText("");
                SubCode.setText("");
                Toast.makeText(AddSubjects.this, "Subject added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}