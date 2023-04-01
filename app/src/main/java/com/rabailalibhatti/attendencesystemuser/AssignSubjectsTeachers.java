package com.rabailalibhatti.attendencesystemuser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignSubjectsTeachers extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    private ArrayList<assignSubjectsShowList> subjectList;
    private RecyclerView recyclerView;
    private AssignSubjectsShowRecyclerAdapter assignSubjectsShowRecyclerAdapter;

    private ArrayList<String> teacherKeysList, subjectKeysList;
    private ArrayAdapter<String> teacherAdapter, subjectAdapter;
    private String teacherKey, subjectKey, semesterName;

    private ArrayList<subjectModel> subjectModels;
    private subjectModel selectedModel;

    MaterialAutoCompleteTextView teachers, subjects;
    Button addSubjects;

    List<String> willbeAssigned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_subjects_teachers);
        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Assigning Subjects");
        progressDialog.setCancelable(false);

        subjectList = new ArrayList<>();
        teacherKeysList = new ArrayList<>();
        subjectKeysList = new ArrayList<>();

        willbeAssigned = new ArrayList<>();

        subjectModels = new ArrayList<>();
        selectedModel = new subjectModel();

        teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        teachers = findViewById(R.id.teacherName);
        subjects = findViewById(R.id.subjectName);

        teachers.setAdapter(teacherAdapter);
        subjects.setAdapter(subjectAdapter);

        fetchSubjects();
        fetchTeachers();

        recyclerView = findViewById(R.id.subjectList);

        addSubjects = findViewById(R.id.add);

        teachers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                subjectList.clear();
                teacherKey = teacherKeysList.get(i);
                getAssignedRecords(teacherKey);
            }
        });

        subjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                subjectKey = subjectKeysList.get(i);
                selectedModel = subjectModels.get(i);
            }
        });

        // This is clicked to add the subject to the list for now
        addSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(teacherKey)){
                    teachers.setError("Please Select Teacher");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(subjectKey)){
                    subjects.setError("Please Select Subject");
                    progressDialog.dismiss();
                } else{
                    addSubjectstoList(subjectKey, subjects.getText().toString(), selectedModel.getSemesterKey());
                    assignSubject(subjectKey, teacherKey);
                }
            }
        });
    }

    private void assignSubject(String subKey, String teaKey){
        DatabaseReference subjectRef = FirebaseDatabase.getInstance().getReference("Subjects").child(subKey);
        subjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjectModel subject = snapshot.getValue(subjectModel.class);
                subject.setAssignedTo(teaKey);
                subjectRef.setValue(subject).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AssignSubjectsTeachers.this, ""+ subject.getSubTitle()+ " assigned to "+teachers.getText().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AssignSubjectsTeachers.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Retrieval failed
            }
        });
    }

    private void getAssignedRecords(String teacherKey){
        subjectList.clear();
        FirebaseDatabase.getInstance().getReference("Subjects").orderByChild("assignedTo").equalTo(teacherKey)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot subjectSnapshot : snapshot.getChildren()) {
                                subjectModel sub = subjectSnapshot.getValue(subjectModel.class);
                                addSubjectstoList(sub.getSubKey(), sub.getSubTitle(), sub.getSemesterKey());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void addSubjectstoList(String subKey, String subName, String semName) {
        subjectList.add(new assignSubjectsShowList(subKey, subName, semName));
        assignSubjectsShowRecyclerAdapter = new AssignSubjectsShowRecyclerAdapter(this, subjectList);
        recyclerView.setAdapter(assignSubjectsShowRecyclerAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();

        clearsubjectField();
        fetchSubjects();
    }

    private void clearsubjectField(){
        subjects.setText("");
        subjectKeysList.remove(subjectKey);
        subjectModels.remove(selectedModel);
        subjectAdapter.notifyDataSetChanged();
    }


    public void fetchTeachers() {
        FirebaseDatabase.getInstance().getReference("Teachers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teacherAdapter.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    teacherKeysList.add(snap.getKey());
                    teacherAdapter.add(snap.getValue(teacherModel.class).getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fetchSubjects() {
        FirebaseDatabase.getInstance().getReference("Subjects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjectAdapter.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String subjectKey = snap.getKey();
                    subjectModel subject = snap.getValue(subjectModel.class);
                    if (subject.getAssignedTo() == null || subject.getAssignedTo().toString().isEmpty()){
                        // Check if subject has already been added to the list
                        boolean isAlreadyAdded = false;
                        for (assignSubjectsShowList item : subjectList) {
                            if (item.getSubjectKey().equals(subjectKey)) {
                                isAlreadyAdded = true;
                                break;
                            }
                        }

                        if (!isAlreadyAdded) {
                            // Add subject to dropdown
                            subjectKeysList.add(subjectKey);
                            subjectAdapter.add(subject.getSubTitle());
                            subjectModels.add(subject);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}