package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterStudent extends AppCompatActivity {

    TextInputEditText name, rollnumber, username, password;
    Button add, update;

    MaterialAutoCompleteTextView batch;
    private String batchkey;
    private ArrayList<String> batchKeysList;
    ArrayAdapter<String> arrayAdapter;

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    private String semesterFirst;

    private String studentKey;
    private studentModel model;
    TextView appbartitle;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        getSemester();

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Adding Student..");
        progressDialog.setCancelable(false);

        name = findViewById(R.id.studentname);
        username = findViewById(R.id.studentusername);
        password = findViewById(R.id.studentpassword);
        rollnumber = findViewById(R.id.roll);
        batch = findViewById(R.id.studentbatch);
        add = findViewById(R.id.add);
        update = findViewById(R.id.update);
        appbartitle = findViewById(R.id.appbartitle);

        if (getIntent().hasExtra("KEY")){
            studentKey = getIntent().getStringExtra("KEY");
            update.setVisibility(View.VISIBLE);
            fillData(studentKey);
            appbartitle.setText("Update Student");
        }
        else{
            add.setVisibility(View.VISIBLE);
            appbartitle.setText("Student Registration");
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        batch.setAdapter(arrayAdapter);

        batchKeysList = new ArrayList<>();

        batch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                batchkey = batchKeysList.get(i).toString();
            }
        });

        fetchBatches();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String namee = name.getText().toString();
                String rolla = rollnumber.getText().toString();
                String usern = username.getText().toString();
                String passw = password.getText().toString();
                if (TextUtils.isEmpty(namee)){
                    name.setError("Please Enter Name");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(rolla)){
                    rollnumber.setError("Please Enter Roll Numberr");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(usern)){
                    username.setError("Please Enter Username");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(passw)){
                    password.setError("Please Enter Password");
                    progressDialog.dismiss();
                } else{
                    addStudent(namee, rolla, batchkey, usern, passw);
                }
            }
        });
    }

    private void fillData(String key){
        FirebaseDatabase.getInstance().getReference("Students").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                model = task.getResult().getValue(studentModel.class);
                name.setText(model.getName());
                rollnumber.setText(model.getRollNumber());
                batch.setEnabled(false);
                username.setText(model.getUserName());
                password.setText(model.getPassword());
            }
        });
    }

    private void fetchBatches(){
        FirebaseDatabase.getInstance().getReference("Batches").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    for (DataSnapshot snap : task.getResult().getChildren()){
                        batchKeysList.add(snap.getValue(batchModel.class).getKey());
                        arrayAdapter.add(snap.getValue(batchModel.class).getBatchTitle());
                    }
                }
            }
        });
    }

    private void getSemester(){
        FirebaseDatabase.getInstance().getReference("Semesters").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    for (DataSnapshot snap : task.getResult().getChildren()){
                        semesterModel model = snap.getValue(semesterModel.class);
                        if (model != null){
                            if (model.getSemNumber() == 1){
                                semesterFirst = model.getSemKey();
                                Log.d("dubai", "name: "+model.getSemTitle());
                                Log.d("dubai", "key: "+model.getSemKey());
//                                Toast.makeText(RegisterStudent.this, ""+semesterFirst, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            else{
                                new AlertDialog.Builder(RegisterStudent.this)
                                        .setTitle("Unsuccessfull")
                                        .setMessage("Please add Data for Semester 1")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(RegisterStudent.this, AddSemesters.class));
                                                finish();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                        } else{
                            Toast.makeText(RegisterStudent.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void addStudent(String name, String roll, String batchKey, String username, String password){
        String key = FirebaseDatabase.getInstance().getReference("Students").push().getKey();
        FirebaseDatabase.getInstance().getReference("Students").child(key).setValue(new studentModel(key, name, roll, batchKey, semesterFirst, username, password, false))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterStudent.this, "Student Successfully Added", Toast.LENGTH_SHORT).show();
                            finish();
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(RegisterStudent.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    public void update(View view) {
        FirebaseDatabase.getInstance().getReference("Students")
                .child(model.getKey())
                .setValue(new studentModel(model.getKey(), name.getText().toString(), rollnumber.getText().toString(), model.getBatchKey(), model.getCurrentSemesterKey(), username.getText().toString(), password.getText().toString(), model.getPassedOut()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterStudent.this, "Student Successfully Updated", Toast.LENGTH_SHORT).show();
                            finish();
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(RegisterStudent.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}