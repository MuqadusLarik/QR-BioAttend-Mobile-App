package com.rabailalibhatti.attendencesystemuser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rabailalibhatti.attendencesystemuser.R;
import com.rabailalibhatti.attendencesystemuser.StudentAttendenceModel;
import com.rabailalibhatti.attendencesystemuser.subjectModel;
import com.rabailalibhatti.attendencesystemuser.AttendanceReportRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewAttendanceReport extends AppCompatActivity {

    ArrayAdapter<String> monthAdapter, yearAdapter;
    MaterialAutoCompleteTextView month, year;
    String monthNUmb, yearNUmb;

    private ArrayAdapter<String> subjectAdapter;
    MaterialAutoCompleteTextView subjects;
    private ArrayList<String> subjectKeysList;
    private String subjectKey;

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;

    private ArrayList<StudentAttendenceModel> attendenceRecords;
    AttendanceReportRecyclerAdapter attendanceReportRecyclerAdapter;
    RecyclerView recyclerView;

    private String teacherKey;
    Button generateReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance_report);

        if (getIntent().hasExtra("KEY")) {
            teacherKey = getIntent().getStringExtra("KEY");
        }

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> finish());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading..");

        generateReport = findViewById(R.id.generateReport);
        month = findViewById(R.id.monthSelect);
        year = findViewById(R.id.yearSelect);
        subjects = findViewById(R.id.subjectName);
        recyclerView = findViewById(R.id.recyclerView);
        subjectKeysList = new ArrayList<>();
        attendenceRecords = new ArrayList<>();

        monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        month.setAdapter(monthAdapter);

        yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        year.setAdapter(yearAdapter);

        subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        subjects.setAdapter(subjectAdapter);

        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        Calendar calendarr = Calendar.getInstance();
        int myear = calendarr.get(Calendar.YEAR);

        populateMonths(months);
        populateYears(myear - 2);
        fetchSubjects(teacherKey);

        subjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                subjectKey = subjectKeysList.get(i);
            }
        });

        month.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                monthNUmb = String.valueOf(i + 1);
            }
        });

        year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yearNUmb = year.getText().toString();
            }
        });

        generateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if (subjectKey.isEmpty()) {
                    subjects.setError("Please Select Subject");
                    progressDialog.dismiss();
                } else if (year.getText().toString().isEmpty()) {
                    year.setError("Please Select Year");
                    progressDialog.dismiss();
                } else if (month.getText().toString().isEmpty()) {
                    month.setError("Please Select Month");
                    progressDialog.dismiss();
                } else {
                    attendenceRecords.clear();
                    searchDates(Integer.parseInt(yearNUmb), Integer.parseInt(monthNUmb), subjectKey);
                }
            }
        });
    }

    private void searchDates(int year, int month, String subKey) {
        List<String> dates = getDatesInMonth(year, month);

        for (String date : dates) {
            fetchFromFirebase(date, subKey);
        }
    }

    private void fetchFromFirebase(String datee, String subKey) {
        FirebaseDatabase.getInstance().getReference("Attendance").child(datee).child(subKey).orderByChild("date")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                StudentAttendenceModel model = snap.getValue(StudentAttendenceModel.class);
                                if (model != null) {
                                    attendenceRecords.add(model);
                                }
                            }
                            showData(attendenceRecords);
                        }
                        else{
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void populateYears(int mYear) {
        for (int i = 1; i <= 10; i++) {
            yearAdapter.add(String.valueOf(mYear + i));
        }
    }

    private void populateMonths(String[] monthNames) {
        for (String month : monthNames) {
            monthAdapter.add(month);
        }
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
                        progressDialog.dismiss();
                    }
                });
    }

    public static int getDaysInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static List<String> getDatesInMonth(int year, int month) {
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int numDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        for (int day = 1; day <= numDays; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            Date date = calendar.getTime();
            String dateStr = dateFormat.format(date);
            dates.add(dateStr);
        }
        return dates;
    }

    private void showData(ArrayList<StudentAttendenceModel> arrayList){
        progressDialog.dismiss();
        attendanceReportRecyclerAdapter = new AttendanceReportRecyclerAdapter(this, arrayList, subjectKey);
        recyclerView.setAdapter(attendanceReportRecyclerAdapter);
    }

}