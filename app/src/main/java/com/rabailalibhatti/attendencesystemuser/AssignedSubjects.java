package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignedSubjects extends AppCompatActivity {

    private String TAG = "dubai";
    private ArrayList<teacherSubjectsModel> teacherSubjectsModels;
    private RecyclerView recyclerView;
    private TeacherSubjectsRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_subjects);

        teacherSubjectsModels = new ArrayList<>();

        recyclerView = findViewById(R.id.tsubjects);
        adapter = new TeacherSubjectsRecyclerAdapter(teacherSubjectsModels);
        recyclerView.setAdapter(adapter);

        fetchAll();
    }

    public void assignSubs(View view) {
        startActivity(new Intent(AssignedSubjects.this, AssignSubjectsTeachers.class));
    }

    private void fetchAll(){
        FirebaseDatabase.getInstance().getReference("Teachers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot teacherSnapshot : snapshot.getChildren()) {
                    teacherModel model = teacherSnapshot.getValue(teacherModel.class);
                    List<String> assignedSubjectsKeys = new ArrayList<>();
                    if (model != null){
                        FirebaseDatabase.getInstance().getReference("Subjects").orderByChild("assignedTo").equalTo(model.getKey())
                                .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    for (DataSnapshot subjectSnapshot : snapshot.getChildren()) {
                                        String subjectKey = subjectSnapshot.getValue(subjectModel.class).getSubKey();
                                        assignedSubjectsKeys.add(subjectKey);
                                    }

                                    // Add this teacher's name and list of subjects to the list of TeacherSubjects objects
                                    teacherSubjectsModels.add(new teacherSubjectsModel(model.getName(), assignedSubjectsKeys));

                                    // Notify the RecyclerView adapter that the data has changed
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}