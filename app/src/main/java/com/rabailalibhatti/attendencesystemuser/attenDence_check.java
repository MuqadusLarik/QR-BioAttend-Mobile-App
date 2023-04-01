package com.rabailalibhatti.attendencesystemuser;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class attenDence_check extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;
    private ArrayList<String> subjectKeysList;
    private ArrayAdapter<String> subjectAdapter;
    MaterialAutoCompleteTextView teachers, subjects;
    private String subjectKey, teacherKey;
    TextInputLayout subName;

    private ArrayList<subjectModel> subjectModels;
    private subjectModel selectedModel;

    MaterialCardView showDate, design;
    private int mYear, mMonth, mDay;

    private HashMap<String, String> attendenceMap;
    TextView datee;
    ArrayList<StudentAttendenceModel> arrayList;
    RecyclerView recyclerView;
    AttendenceCheckRecyclerAdapter recyclerViewAdapter;

    Button checkAttendence;
    private String TAG = "dubai";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atten_dence_check);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Assigning Subjects");
        progressDialog.setCancelable(false);

        subjectKeysList = new ArrayList<>();
        subjectModels = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        showDate = findViewById(R.id.showdate);
        design = findViewById(R.id.design);
        datee = findViewById(R.id.date);
        attendenceMap = new HashMap<>();
        checkAttendence = findViewById(R.id.checkAttendence);
        subName = findViewById(R.id.subName);

        subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        subjects = findViewById(R.id.subjectName);
        subjects.setAdapter(subjectAdapter);

        if (getIntent().hasExtra("KEY")) {
            teacherKey = getIntent().getStringExtra("KEY");
            fetchSubjects(teacherKey);
        }

        if (getIntent().hasExtra("DATE")){
            subjects.setVisibility(View.GONE);
            showDate.setVisibility(View.GONE);
            checkAttendence.setVisibility(View.GONE);
            design.setVisibility(View.VISIBLE);
            subName.setVisibility(View.GONE);
            checkFirebase(getIntent().getStringExtra("DATE"), getIntent().getStringExtra("SUBK"));
        }

        subjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                subjectKey = subjectKeysList.get(i);
            }
        });

        showDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(attenDence_check.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayOfMonth) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthofYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String formattedDate = sdf.format(cal.getTime());
                        datee.setText(formattedDate);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()+1);
                datePickerDialog.show();
            }
        });

        checkAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if (subjectKey.isEmpty()) {
                    subjects.setError("Please Select Subject");
                    progressDialog.dismiss();
                } else if (datee.getText().toString().equals("Select Date")) {
                    Toast.makeText(attenDence_check.this, "Please Select Date First", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                     checkFirebase(datee.getText().toString(), subjectKey);
                }
            }
        });
    }

    private void fetchSubjects(String teaKey) {
        FirebaseDatabase.getInstance().getReference("Subjects").orderByChild("assignedTo").equalTo(teaKey)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        subjectAdapter.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot subjectSnapshot : snapshot.getChildren()) {
                                subjectModel sub = subjectSnapshot.getValue(subjectModel.class);
                                subjectKeysList.add(sub.getSubKey());
                                subjectAdapter.add(sub.getSubTitle());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void showAll(ArrayList<StudentAttendenceModel> arrayList, HashMap<String, String> hashMap) {
        recyclerViewAdapter = new AttendenceCheckRecyclerAdapter(attenDence_check.this, arrayList, hashMap);
        recyclerView.setAdapter(recyclerViewAdapter);
        progressDialog.dismiss();
    }

    public void checkFirebase(String datee, String subKey) {
        FirebaseDatabase.getInstance().getReference("Attendance")
                .child(datee)
                .child(subKey)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            arrayList.clear();
                            attendenceMap.clear();
                            design.setVisibility(View.VISIBLE);
                            for (DataSnapshot snap : task.getResult().getChildren()) {
                                StudentAttendenceModel model = snap.getValue(StudentAttendenceModel.class);
                                if (model != null && model.getAttendenceRecord() != null) {
                                    attendenceMap = model.getAttendenceRecord();
                                    arrayList.add(model);
                                } else {
                                    Toast.makeText(attenDence_check.this, "Attendence was Taken but No Student was Present", Toast.LENGTH_SHORT).show();
                                }
                            }
                            showAll(arrayList, attendenceMap);
                        } else {
                            Toast.makeText(attenDence_check.this, "No Attendence Record for this Date", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}