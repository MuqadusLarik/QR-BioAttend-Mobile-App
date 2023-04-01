package com.rabailalibhatti.attendencesystemuser;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Attendence_Subjects extends AppCompatActivity {

    private String TAG = "dubai";
    // define variables
    private RecyclerView recyclerView;
    private AttendenceSubjectRecyclerAdapter attendenceSubjectRecyclerAdapter;
    private ArrayList<subjectModel> assignedSubjects;
    private ArrayList<attendenceSubjectData> subjectList;

    private String teacherKey, semNamee;

    String currentDateandTime;

    private MaterialToolbar materialToolbar;
    private ProgressDialog progressDialog;

    private int mYear, mMonth, mDay;
    Button selectDate, showDate;

    // Request code for starting the EditActivity
    private static final int REQUEST_CODE_EDIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_subjects);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Recods..");
        progressDialog.setCancelable(false);

        recyclerView = findViewById(R.id.recyclerView);
        subjectList = new ArrayList<>();
        assignedSubjects = new ArrayList<>();
        selectDate = findViewById(R.id.selectDate);
        showDate = findViewById(R.id.showDate);

        currentDateandTime = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        showDate.setText(currentDateandTime);

        if (getIntent().hasExtra("KEY")) {
            teacherKey = getIntent().getStringExtra("KEY");
        }

        getAssignedRecords(teacherKey, currentDateandTime);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Attendence_Subjects.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayOfMonth) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthofYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String formattedDate = sdf.format(cal.getTime());
                        showDate.setText("Attendence for Date : "+formattedDate);
                        showDate.setVisibility(View.VISIBLE);

                        progressDialog.show();

                        for (subjectModel sub : assignedSubjects){
                            checkFirebase(formattedDate, sub.getSubKey(), sub.getSubTitle(), sub.getSemesterKey());
                        }

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000));
                datePickerDialog.show();
            }
        });
    }

    public void checkFirebase(String date, String subjectKey, String subName, String semKey) {
        subjectList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Attendance").child(date).child(subjectKey)
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            subjectList.add(new attendenceSubjectData(subjectKey, subName, semKey, "Taken", date));
                            progressDialog.dismiss();
                        } else {
                            subjectList.add(new attendenceSubjectData(subjectKey, subName, semKey, "No", date));
                            progressDialog.dismiss();
                        }
                        showData(subjectList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void getAssignedRecords(String teacherKey, String date) {
        assignedSubjects.clear();
        FirebaseDatabase.getInstance().getReference("Subjects").orderByChild("assignedTo").equalTo(teacherKey)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot subjectSnapshot : snapshot.getChildren()) {
                                subjectModel sub = subjectSnapshot.getValue(subjectModel.class);
                                assignedSubjects.add(sub);
                                checkFirebase(date, sub.getSubKey(), sub.getSubTitle(), sub.getSemesterKey());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showData(ArrayList<attendenceSubjectData> arrayList) {
        attendenceSubjectRecyclerAdapter = new AttendenceSubjectRecyclerAdapter(this, arrayList, teacherKey);
        recyclerView.setAdapter(attendenceSubjectRecyclerAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            getAssignedRecords(data.getStringExtra("TEAC"), data.getStringExtra("DATE"));
            attendenceSubjectRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}