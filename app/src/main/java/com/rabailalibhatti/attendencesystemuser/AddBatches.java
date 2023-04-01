package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddBatches extends AppCompatActivity {

    TextInputEditText batchtitle;
    Button add;

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    AutoCompleteTextView startYear, endYear;
    ArrayAdapter<String> yearAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batches);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Adding Batch..");
        progressDialog.setCancelable(false);

        batchtitle = findViewById(R.id.batchtitle);
        startYear = findViewById(R.id.startyear);
        endYear = findViewById(R.id.endyear);
        add = findViewById(R.id.add);

        yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        startYear.setAdapter(yearAdapter);
        endYear.setAdapter(yearAdapter);

        addBatchYears();

        startYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                endYear.setText(String.valueOf(Integer.parseInt(startYear.getText().toString())+4));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String title = batchtitle.getText().toString();
                String ssYear = startYear.getText().toString();
                String eeYear = endYear.getText().toString();
                if (TextUtils.isEmpty(title)){
                    batchtitle.setError("Please Enter Title");
                    progressDialog.dismiss();
                }
                else if (TextUtils.isEmpty(ssYear)){
                    startYear.setError("Please Select Start Year");
                    progressDialog.dismiss();
                }
                else if (TextUtils.isEmpty(eeYear)){
                    endYear.setError("Please Select End Year");
                    progressDialog.dismiss();
                }
                else{
                    addBatch(title, ssYear, eeYear);
                }
            }
        });
    }

    private void addBatch(String title, String sYear, String eYear){
        String key = FirebaseDatabase.getInstance().getReference("Batches").push().getKey();
        FirebaseDatabase.getInstance().getReference("Batches").child(key).setValue(new batchModel(key, title, sYear, eYear, "Active"))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddBatches.this, "Batch Successfully Added", Toast.LENGTH_SHORT).show();
                            finish();
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(AddBatches.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void addBatchYears() {
        // Retrieve list of all batches from Firebase
        DatabaseReference batchesRef = FirebaseDatabase.getInstance().getReference("Batches");
        batchesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Integer> assignedYears = new ArrayList<>();
                    for (DataSnapshot batchSnapshot : snapshot.getChildren()) {
                        // Extract start year from each batch
                        int startYear = Integer.parseInt(batchSnapshot.child("startYear").getValue(String.class));
                        assignedYears.add(startYear);
                    }

                    // Determine next available start year
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    int nextAvailableYear = currentYear;
                    for (int i = 0; i < 5; i++) {
                        if (assignedYears.contains(nextAvailableYear)) {
                            nextAvailableYear++;
                        } else {
                            break;
                        }
                    }

                    // Add unassigned start years to the dropdown
                    for (int i = nextAvailableYear; i <= currentYear + 5; i++) {
                        yearAdapter.add(String.valueOf(i));
                    }
                } else {
                    // No batches exist yet, add all years to dropdown
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    for (int i = currentYear; i <= currentYear + 5; i++) {
                        yearAdapter.add(String.valueOf(i));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Error retrieving batches: " + error.getMessage());
            }
        });
    }

}
