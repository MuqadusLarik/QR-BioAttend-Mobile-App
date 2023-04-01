package com.rabailalibhatti.attendencesystemuser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rabailalibhatti.attendencesystemuser.R;
import com.rabailalibhatti.attendencesystemuser.StudentAttendenceModel;
import com.rabailalibhatti.attendencesystemuser.semesterModel;
import com.rabailalibhatti.attendencesystemuser.studentModel;
import com.rabailalibhatti.attendencesystemuser.subjectModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class checkAttendenceStudent extends AppCompatActivity {

    ArrayAdapter<String> monthAdapter, yearAdapter;
    MaterialAutoCompleteTextView month, year;
    String monthNUmb, yearNUmb;

    private ArrayAdapter<String> subjectAdapter;
    MaterialAutoCompleteTextView subjects;
    private ArrayList<String> subjectKeysList;
    private String subjectKey;

    private ArrayAdapter<String> semesterAdapter;
    MaterialAutoCompleteTextView semesters;
    private ArrayList<String> semesterKeysList;
    private String semesterKey;

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;
    LinearLayout relative;

    private String studentKey, semesterName;
    Button checkAttendence;
    private HashMap<String, String> attendenceMap;
    int countPresent, countAbsent, countLeave, countTotal;

    TextView yearTxt, monthTxt, subjectTxt, semesterTxt, takenattendence, presentdays, absentdays, leavedays, percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendence_student);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> finish());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading..");

        checkAttendence = findViewById(R.id.checkAttendence);
        semesters = findViewById(R.id.semesterName);
        semesterKeysList = new ArrayList<>();
        month = findViewById(R.id.monthSelect);
        year = findViewById(R.id.yearSelect);
        subjects = findViewById(R.id.subjectName);
        relative = findViewById(R.id.relative);
        yearTxt = findViewById(R.id.year);
        monthTxt = findViewById(R.id.month);
        subjectTxt = findViewById(R.id.subject);
        semesterTxt = findViewById(R.id.semester);
        takenattendence = findViewById(R.id.takenattendence);
        presentdays = findViewById(R.id.presentdays);
        absentdays = findViewById(R.id.absentdays);
        leavedays = findViewById(R.id.leavedays);
        percentage = findViewById(R.id.percentage);
        subjectKeysList = new ArrayList<>();

        monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        month.setAdapter(monthAdapter);

        yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        year.setAdapter(yearAdapter);

        subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        subjects.setAdapter(subjectAdapter);

        semesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        semesters.setAdapter(semesterAdapter);

        if (getIntent().hasExtra("KEY")) {
            studentKey = getIntent().getStringExtra("KEY");
        }

        getCurrentSemester(studentKey);


        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        Calendar calendarr = Calendar.getInstance();
        int myear = calendarr.get(Calendar.YEAR);

        populateMonths(months);
        populateYears(myear - 2);

        subjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                subjectKey = subjectKeysList.get(i);
            }
        });

        semesters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clearFields();
                semesterKey = semesterKeysList.get(i);
                fetchSubjects(semesterKey);
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

        checkAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                clearFields();
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
                    countTotal = 0;
                    countAbsent = 0;
                    countLeave = 0;
                    countPresent = 0;
                    searchDates(Integer.parseInt(yearNUmb), Integer.parseInt(monthNUmb), subjectKey);
                }
            }
        });
    }

    private void clearFields() {
        percentage.setText("");
        subjectTxt.setText("");
        semesterTxt.setText("");
        yearTxt.setText("");
        monthTxt.setText("");
        presentdays.setText("");
        absentdays.setText("");
        leavedays.setText("");
        takenattendence.setText("");
        countPresent = 0;
        countAbsent = 0;
        countLeave = 0;
        countTotal = 0;
    }

    private void fetchSemesters(int semNum) {
        FirebaseDatabase.getInstance().getReference("Semesters")
                .orderByChild("semNumber")
                .startAt(1)
                .endAt(semNum)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        semesterAdapter.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                semesterModel sem = snap.getValue(semesterModel.class);
                                semesterKeysList.add(sem.getSemKey());
                                semesterAdapter.add(sem.getSemTitle());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
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
        FirebaseDatabase.getInstance().getReference("Attendance").child(datee).child(subKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            relative.setVisibility(View.VISIBLE);
                            countTotal += 1;
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                StudentAttendenceModel model = snap.getValue(StudentAttendenceModel.class);
                                if (model != null && model.getAttendenceRecord() != null) {
                                    attendenceMap = model.getAttendenceRecord();
                                    if (attendenceMap.get(studentKey).equals("present")) {
                                        countPresent += 1;
                                    } else if (attendenceMap.get(studentKey).equals("absent")) {
                                        countAbsent += 1;
                                    } else if (attendenceMap.get(studentKey).equals("leave")) {
                                        countLeave += 1;
                                    }
                                }
                            }
                            enterFieldData(countTotal, countPresent, countAbsent, countLeave, subjects.getText().toString(), semesters.getText().toString(), year.getText().toString(), month.getText().toString());
                        } else {
                            progressDialog.dismiss();
                            enterFieldData(countTotal, countPresent, countAbsent, countLeave, subjects.getText().toString(), semesters.getText().toString(), year.getText().toString(), month.getText().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void enterFieldData(int cTotal, int cPresent, int cAbsent, int cLeave, String subName, String semName, String yea, String mon) {
        float percent = 0;
        if (cTotal != 0) {
            percent = (cPresent * 100) / cTotal;
        }
        percentage.setText(String.valueOf(percent) + "%");
        subjectTxt.setText(subName);
        semesterTxt.setText(semName);
        yearTxt.setText(yea);
        monthTxt.setText(mon);
        presentdays.setText(String.valueOf(cPresent));
        absentdays.setText(String.valueOf(cAbsent));
        leavedays.setText(String.valueOf(cLeave));
        takenattendence.setText(String.valueOf(cTotal));
    }

    private void getCurrentSemester(String stdKey) {
        FirebaseDatabase.getInstance().getReference("Students").child(stdKey).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    semesterKey = task.getResult().getValue(studentModel.class).getCurrentSemesterKey();
                    getSemesterName(semesterKey);
                }
            }
        });
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

    private void fetchSubjects(String semKey) {
        FirebaseDatabase.getInstance().getReference("Subjects").orderByChild("semesterKey").equalTo(semKey)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        subjectKeysList.clear();
                        subjectAdapter.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot subjectSnapshot : snapshot.getChildren()) {
                                subjectModel sub = subjectSnapshot.getValue(subjectModel.class);
                                subjectKeysList.add(sub.getSubKey());
                                subjectAdapter.add(sub.getSubTitle());
                            }
                        }
                                            subjects.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void getSemesterName(String semesterKey) {
        FirebaseDatabase.getInstance().getReference("Semesters").child(semesterKey).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    semesterName = task.getResult().getValue(semesterModel.class).getSemTitle();
                    fetchSemesters(task.getResult().getValue(semesterModel.class).getSemNumber());
                }
            }
        });
    }
}