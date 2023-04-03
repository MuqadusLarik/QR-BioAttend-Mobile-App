package com.rabailalibhatti.attendencesystemuser;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PromoteStudents extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    RecyclerView recyclerView;
    ArrayList<studentModel> studentModels;
    SubjectsStudentRecyclerAdapter subjectsStudentRecyclerAdapter;

    private ArrayAdapter<String> semesterAdapter;
    MaterialAutoCompleteTextView semesters;
    private ArrayList<String> semesterKeysList;
    private ArrayList<semesterModel> semesterToPromoteList;
    private semesterModel semesterToPromoteModel;
    private String semesterKey;

    private ArrayAdapter<String> semesterToPromoteToAdapter;
    MaterialAutoCompleteTextView semesterToPromoteTo;
    private ArrayList<String> semesterToPromoteToKeysList;
    private semesterModel semesterToPromoteToModel;
    private ArrayList<semesterModel> semesterToPromoteToList;
    private String semesterToPromoteToKey;

    Button promoteStudents, passoutStudents;
    private String batchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_students);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Promoting Students..");
        progressDialog.setCancelable(false);

        recyclerView = findViewById(R.id.recyclerView);
        semesters = findViewById(R.id.semesterName);
        semesterToPromoteTo = findViewById(R.id.semesterToPromote);
        recyclerView = findViewById(R.id.recyclerView);
        promoteStudents = findViewById(R.id.promoteStudents);
        passoutStudents = findViewById(R.id.passoutStudents);

        studentModels = new ArrayList<>();
        semesterKeysList = new ArrayList<>();
        semesterToPromoteToKeysList = new ArrayList<>();
        semesterToPromoteList = new ArrayList<>();
        semesterToPromoteToList = new ArrayList<>();

        semesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        semesters.setAdapter(semesterAdapter);

        semesterToPromoteToAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        semesterToPromoteTo.setAdapter(semesterToPromoteToAdapter);

        fetchSemesters();

        semesters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semesterKey = semesterKeysList.get(i);
                semesterToPromoteModel = semesterToPromoteList.get(i);
                getStudents(semesterKey);
                if (semesterToPromoteModel.getSemNumber() == 8) {
                    passoutStudents.setVisibility(View.VISIBLE);
                } else {
                    passoutStudents.setVisibility(View.GONE);
                }
                Toast.makeText(PromoteStudents.this, ""+semesterToPromoteModel.getSemNumber(), Toast.LENGTH_SHORT).show();
            }
        });

        semesterToPromoteTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semesterToPromoteToKey = semesterToPromoteToKeysList.get(i);
                semesterToPromoteToModel = semesterToPromoteToList.get(i);
                Toast.makeText(PromoteStudents.this, ""+semesterToPromoteToModel.getSemNumber(), Toast.LENGTH_SHORT).show();
            }
        });

        promoteStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((semesterToPromoteModel.getSemNumber() +1) == semesterToPromoteToModel.getSemNumber()) {
                    new AlertDialog.Builder(PromoteStudents.this)
                            .setTitle("Confirmation")
                            .setMessage("Do you want to promote these students")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    promoteStudentss(semesterToPromoteToKey, studentModels);

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(PromoteStudents.this, "Semester " + semesterToPromoteModel.getSemNumber() + " students can not be promoted to " + semesterToPromoteToModel.getSemNumber(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        passoutStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (semesterToPromoteModel.getSemNumber() != 8) {
                    Toast.makeText(PromoteStudents.this, "Semester " + semesterToPromoteModel.getSemNumber() + " students can not be promoted to " + semesterToPromoteToModel.getSemNumber(), Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(PromoteStudents.this)
                            .setTitle("Confirmation")
                            .setMessage("Do you want to passout these students")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    passoutStudentss(studentModels);
                                    if (!batchKey.isEmpty()) {
                                        passOutBatch(batchKey);
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
                }
            }
        });
    }

    private void promoteStudentss(String targetSemesterKey, ArrayList<studentModel> selectedStudents) {
        for (studentModel student : selectedStudents) {
            FirebaseDatabase.getInstance().getReference("Students")
                    .child(student.getKey())
                    .child("currentSemesterKey")
                    .setValue(targetSemesterKey);
        }
        progressDialog.dismiss();
        Toast.makeText(this, "All Students have been promoted", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void passoutStudentss(ArrayList<studentModel> selectedStudents) {
        for (studentModel student : selectedStudents) {
            FirebaseDatabase.getInstance().getReference("Students")
                    .child(student.getKey())
                    .child("passedOut")
                    .setValue(true);
        }
        progressDialog.dismiss();
        Toast.makeText(this, "All selected students have graduated", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void getStudents(String semeKey) {
        studentModels.clear();
        FirebaseDatabase.getInstance().getReference("Students").orderByChild("currentSemesterKey").equalTo(semeKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    studentModel model = snap.getValue(studentModel.class);
                    if (model != null && !model.getPassedOut()) {
                        studentModels.add(model);
                        batchKey = model.getBatchKey();
                    } else {
                        Toast.makeText(PromoteStudents.this, "No Student in this semester for now", Toast.LENGTH_SHORT).show();
                    }
                }

                // Sort the ArrayList using a custom Comparator
                Collections.sort(studentModels, new Comparator<studentModel>() {
                    @Override
                    public int compare(studentModel s1, studentModel s2) {
                        return s1.getName().compareTo(s2.getName());
                    }
                });

                showData(studentModels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void passOutBatch(String batchKey) {
        DatabaseReference batchRef = FirebaseDatabase.getInstance().getReference("Batches").child(batchKey);

        batchRef.child("batchStatus").setValue("Ended")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    private void fetchSemesters() {
        FirebaseDatabase.getInstance().getReference("Semesters").orderByChild("semTitle")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        clearFields();
                        if (snapshot.exists()) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                semesterModel sem = snap.getValue(semesterModel.class);
                                semesterKeysList.add(sem.getSemKey());
                                semesterAdapter.add(sem.getSemTitle());
                                semesterToPromoteList.add(sem);

                                semesterToPromoteToKeysList.add(sem.getSemKey());
                                semesterToPromoteToAdapter.add(sem.getSemTitle());
                                semesterToPromoteToList.add(sem);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void clearFields() {
        semesterAdapter.clear();
        semesterKeysList.clear();
        semesterToPromoteToAdapter.clear();
        semesterToPromoteToKeysList.clear();
        semesterToPromoteList.clear();
        semesterToPromoteToList.clear();
    }

    private void showData(ArrayList<studentModel> arrayList) {
        subjectsStudentRecyclerAdapter = new SubjectsStudentRecyclerAdapter(arrayList);
        recyclerView.setAdapter(subjectsStudentRecyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}