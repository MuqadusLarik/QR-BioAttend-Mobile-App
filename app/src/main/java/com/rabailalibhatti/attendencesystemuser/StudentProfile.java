package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class StudentProfile extends AppCompatActivity {

    TextInputEditText name, roll, batch, username, password;
    Button editPass, changePass;
    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    private String studentKey;

    studentModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Details..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (getIntent().hasExtra("KEY")){
            studentKey = getIntent().getStringExtra("KEY");
        }

        fillData(studentKey);

        name = findViewById(R.id.name);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.passWord);
        roll = findViewById(R.id.roll);
        batch = findViewById(R.id.batch);

        editPass = findViewById(R.id.editPass);
        changePass = findViewById(R.id.changePass);

        editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPass.setVisibility(View.GONE);
                changePass.setVisibility(View.VISIBLE);
                password.setEnabled(true);
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().isEmpty()){
                    password.setError("Enter Password");
                }
                else{
                    updatePass(password.getText().toString());
                    changePass.setVisibility(View.GONE);
                    editPass.setVisibility(View.VISIBLE);
                    password.setEnabled(false);
                }

            }
        });
    }

    private void fillData(String key){
        FirebaseDatabase.getInstance().getReference("Students").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    model = task.getResult().getValue(studentModel.class);
                    name.setText(model.getName());
                    roll.setText(model.getRollNumber());
                    username.setText(model.getUserName());
                    getBatch(model.getBatchKey());
                    password.setText(model.getPassword());
                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(StudentProfile.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });
    }

    private void getBatch(String key){
        FirebaseDatabase.getInstance().getReference("Batches").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    batch.setText(task.getResult().getValue(batchModel.class).getBatchTitle());
                }
                else{
                    Toast.makeText(StudentProfile.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePass(String passW){
        FirebaseDatabase.getInstance().getReference("Students")
                .child(model.getKey())
                .setValue(new studentModel(model.getKey(), model.getName(), model.getRollNumber(), model.getBatchKey(), model.getCurrentSemesterKey(),  model.getUserName(), passW))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(StudentProfile.this, "Password Successfully Updated", Toast.LENGTH_SHORT).show();
                            finish();
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(StudentProfile.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}