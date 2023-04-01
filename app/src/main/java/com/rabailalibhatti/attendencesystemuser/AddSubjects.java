package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddSubjects extends AppCompatActivity {

    TextInputEditText subTitle, subCode, creditHours;

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    MaterialAutoCompleteTextView semesters;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> semesterKeysList;
    private String semesterKey;

    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjects);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Adding Subject..");
        progressDialog.setCancelable(false);

        subTitle= findViewById(R.id.subname);
        subCode= findViewById(R.id.subcode);
        creditHours= findViewById(R.id.credithours);
        semesters = findViewById(R.id.semester);
        add = findViewById(R.id.add);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1);
        semesters.setAdapter(arrayAdapter);

        semesterKeysList = new ArrayList<>();

        fetchSemesters();

        semesters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semesterKey = semesterKeysList.get(i).toString();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String subt = subTitle.getText().toString();
                String subc = subCode.getText().toString();
                String credi = creditHours.getText().toString();
                if (TextUtils.isEmpty(subt)){
                    subTitle.setError("Please Enter Subject Title");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(subc)){
                    subCode.setError("Please Enter Subject Code");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(credi)){
                    creditHours.setError("Please Enter Credit Hours");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(semesterKey)){
                    semesters.setError("Please Select Semester");
                    progressDialog.dismiss();
                } else{
                    addSubjects(subt, subc, credi, semesterKey);
                }
            }
        });

    }
    public void fetchSemesters() {
        FirebaseDatabase.getInstance().getReference("Semesters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayAdapter.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    semesterKeysList.add(snap.getKey());
                    arrayAdapter.add(snap.getValue(semesterModel.class).getSemTitle());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addSubjects(String title, String code,String credithours, String semesterKey){
        String key = FirebaseDatabase.getInstance().getReference("Subjects").push().getKey();
        FirebaseDatabase.getInstance().getReference("Subjects").child(key).setValue(new subjectModel(key, title, code, credithours, semesterKey, ""))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddSubjects.this, "Subject Successfully Added", Toast.LENGTH_SHORT).show();
                            finish();
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(AddSubjects.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}