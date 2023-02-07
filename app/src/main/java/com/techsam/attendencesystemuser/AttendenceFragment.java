package com.techsam.attendencesystemuser;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.techsam.attendencesystemuser.adapters.AttendenceRecyclerAdapter;
import com.techsam.attendencesystemuser.objects.AttendenceObj;
import com.techsam.attendencesystemuser.objects.PresentAbsent;
import com.techsam.attendencesystemuser.objects.Student;
import com.techsam.attendencesystemuser.objects.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AttendenceFragment extends Fragment {
    Button selectDate, submit;
    Calendar myCalendar;
    AttendenceRecyclerAdapter adapter;
    RecyclerView recyclerView;
    DatabaseReference db;
    ImageButton qrScan;

    public static ArrayList<PresentAbsent> list, list2;
    public static HashMap<String, PresentAbsent> maps = new HashMap<>();
    AttendenceObj attendenceObj;

    ProgressDialog pd;
    ArrayList<String> listOfKeys = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_attendence, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_attendence_student);
        db = FirebaseDatabase.getInstance().getReference();
        selectDate = view.findViewById(R.id.selectdate);
        submit = view.findViewById(R.id.submit_attendence);
        qrScan = view.findViewById(R.id.qr);

        myCalendar = Calendar.getInstance();
        attendenceObj = new AttendenceObj();

        list = new ArrayList<>();
        list2 = new ArrayList<>();

        pd = new ProgressDialog(getContext());
        pd.setMessage("Please Wait");


        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            updateLabel();
        };
        selectDate.setOnClickListener(view1 -> {
            new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();


        });

        submit.setOnClickListener(view12 -> {
            AttendenceObj aObj = new AttendenceObj();
            aObj.setDate(selectDate.getText().toString());
            aObj.setSubName(Attendence.subjectName);
            aObj.setList(maps);
            db.child("Attendence").child(aObj.getDate()).child(aObj.getSubName()).setValue(aObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getContext(), "Attendence Submitted", Toast.LENGTH_SHORT).show();
                    maps.clear();
                }
            });
        });

        qrScan.setOnClickListener(view13 -> {

            ScanOptions options = new ScanOptions();
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            options.setPrompt("Scan Student's Barcode");
            options.setCameraId(0);  // Use a specific camera of the device
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setBarcodeImageEnabled(true);
            barcodeLauncher.launch(options);
        });

        return view;


    }

    private void getAttendences() {
        pd.show();
        db.child("Students").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                list.clear();
                for (DataSnapshot snap : task.getResult().getChildren()) {
                    Student student = snap.getValue(Student.class);
                    for (Subject sub : student.getList()) {
                        if (Attendence.subjectName.equals(sub.getSubName())) {
                            PresentAbsent pA = new PresentAbsent();
                            pA.setStudentName(student.getName());
                            pA.setStudentId(student.getId());
                            if (attendenceObj != null)
                                for (Map.Entry<String, PresentAbsent> entry : attendenceObj.getList().entrySet()) {
                                    String key = entry.getKey();
                                    PresentAbsent value = entry.getValue();
                                    if (key.equals(student.getId())) {
                                        pA.setStatus(value.getStatus());
                                    }
                                }
//                            Toast.makeText(getContext(), ""+listOfKeys.size(), Toast.LENGTH_SHORT).show();
//                            for (String key : listOfKeys) {
//
//                            }
                            list.add(pA);
                            break;
                        }
                    }
                }
                pd.dismiss();

                adapter = new AttendenceRecyclerAdapter(list, getContext());
                LinearLayoutManager llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void updateLabel() {

        qrScan.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        selectDate.setText(dateFormat.format(myCalendar.getTime()));
        getAllAttendences();
    }

    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Scanned" + result.getContents(), Toast.LENGTH_LONG).show();
                    submitUserAttendence(result.getContents());
                }
            });

// Launch

    private void submitUserAttendence(String userId) {
        HashMap<String, PresentAbsent> mapList = new HashMap<>();
        AttendenceObj aObj = new AttendenceObj();
        aObj.setDate(selectDate.getText().toString());
        aObj.setSubName(Attendence.subjectName);

        db.child("Students").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Student student = task.getResult().getValue(Student.class);
                mapList.put(userId, new PresentAbsent(userId, student.getName(), "Present"));
                aObj.setList(mapList);
                db.child("Attendence").child(aObj.getDate()).child(aObj.getSubName()).setValue(aObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Attendence Submitted", Toast.LENGTH_SHORT).show();
                        mapList.clear();
                    }
                });
            }
        });
    }


    private void getAllAttendences() {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please wait");
        pd.show();
        db.child("Attendence").child(selectDate.getText().toString()).child(Attendence.subjectName).get().addOnCompleteListener(task -> {
            if (task.getResult() != null) {
                attendenceObj = task.getResult().getValue(AttendenceObj.class);
                pd.dismiss();
                getAttendences();

//                for (DataSnapshot key : task.getResult().getChildren()) {
//                    listOfKeys.add(key.getValue().toString());
//                }
            }
        });

    }


}