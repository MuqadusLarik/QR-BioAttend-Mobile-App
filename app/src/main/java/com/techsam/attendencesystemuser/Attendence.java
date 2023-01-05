package com.techsam.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techsam.attendencesystemuser.adapters.AttendenceRecyclerAdapter;
import com.techsam.attendencesystemuser.objects.PresentAbsent;
import com.techsam.attendencesystemuser.objects.Student;
import com.techsam.attendencesystemuser.objects.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Attendence extends AppCompatActivity {
    Button selectDate;
    Calendar myCalendar;
    AttendenceRecyclerAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<PresentAbsent> list,list2;
    DatabaseReference db;
    ProgressDialog pd;

    String subjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        selectDate = findViewById(R.id.selectdate);
        myCalendar = Calendar.getInstance();
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview_attendence_student);
        db = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(this);
        pd.setTitle("Fetching Data");
        pd.setMessage("Please Wait");
        pd.show();
        subjectName = getIntent().getStringExtra("Subject");
        Toast.makeText(this, subjectName, Toast.LENGTH_SHORT).show();


        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            updateLabel();
        };
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Attendence.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        db.child("Students").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot snap : task.getResult().getChildren()) {
                    Student student = snap.getValue(Student.class);

//                    for(Subject sub:student.getList()){
//                        if (subjectName.equals(sub)) {
//
//                            PresentAbsent pA = new PresentAbsent();
//                            pA.setStudentName(student.getName());
//                            pA.setStudentId(student.getId());
//                            list.add(pA);
////                            break;
//                        }
//                    }
                }

                pd.dismiss();

                adapter = new AttendenceRecyclerAdapter(list, Attendence.this);
                LinearLayoutManager llm = new LinearLayoutManager(Attendence.this, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
            }
        });


    }


    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        selectDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}