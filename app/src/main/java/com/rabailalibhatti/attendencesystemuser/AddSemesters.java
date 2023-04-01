package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddSemesters extends AppCompatActivity {

    TextInputEditText semTitle;

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    MaterialAutoCompleteTextView semNum;
    ArrayAdapter<String> arrayAdapter;

    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semesters);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Adding Semester..");
        progressDialog.setCancelable(false);

        semTitle= findViewById(R.id.semtitle);
        semNum= findViewById(R.id.semNumber);
        add = findViewById(R.id.add);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1);
        semNum.setAdapter(arrayAdapter);

        addSemesterNumbers();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String semT = semTitle.getText().toString();
                String semD = semNum.getText().toString();
                if (TextUtils.isEmpty(semT)){
                    semTitle.setError("Please Enter Semester Title");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(semD)){
                    semNum.setError("Please Enter Number of Semester");
                    progressDialog.dismiss();
                } else{
                    addSemesters(semT, Integer.parseInt(semD));
                }
            }
        });
    }


    private void addSemesterNumbers() {
        // Retrieve list of all semesters from Firebase
        DatabaseReference semestersRef = FirebaseDatabase.getInstance().getReference("Semesters");
        semestersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Integer> assignedNumbers = new ArrayList<>();
                    for (DataSnapshot semesterSnapshot : snapshot.getChildren()) {
                        // Extract semester number from each semester
                        int semesterNumber = semesterSnapshot.child("semNumber").getValue(Integer.class);
                        assignedNumbers.add(semesterNumber);
                    }

                    // Add unassigned semester numbers to the dropdown
                    for (int i = 1; i <= 8; i++) {
                        if (!assignedNumbers.contains(i)) {
                            arrayAdapter.add(String.valueOf(i));
                        }
                    }
                } else {
                    // No semesters exist yet, add all semester numbers to dropdown
                    for (int i = 1; i <= 8; i++) {
                        arrayAdapter.add(String.valueOf(i));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Error retrieving semesters: " + error.getMessage());
            }
        });
    }


    private void addSemesters(String title, int dura){
        String key = FirebaseDatabase.getInstance().getReference("Semesters").push().getKey();
        FirebaseDatabase.getInstance().getReference("Semesters").child(key).setValue(new semesterModel(key, title, dura))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddSemesters.this, "Semester Successfully Added", Toast.LENGTH_SHORT).show();
                            finish();
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(AddSemesters.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}