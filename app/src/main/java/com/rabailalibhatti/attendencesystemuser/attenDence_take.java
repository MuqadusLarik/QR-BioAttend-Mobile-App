package com.rabailalibhatti.attendencesystemuser;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class attenDence_take extends AppCompatActivity implements AttendenceListener {

    TextView date;
    Button submit_attendence;

    String[] perms = {};
    RecyclerView recyclerView;
    AttendenceRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<studentModel> arrayList;
    ProgressDialog progressDialog;
    HashMap<String, String> AttendenceMap;
    MaterialToolbar toolbar;
    MaterialCardView scan_qr_button, showdate;
    private AttendenceListener attendenceListener;
    private static final int REQUEST_CODE_QR_SCAN = 101;

    private String subKey, semKey, teaKey, dateOfAttendence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atten_dence_take);

        toolbar = findViewById(R.id.toolBar);
        toolbar.setNavigationOnClickListener(view -> finish());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Data..");
        progressDialog.setCancelable(false);

        if (getIntent().hasExtra("SUBKEY")) {
            subKey = getIntent().getStringExtra("SUBKEY");
            semKey = getIntent().getStringExtra("SEMKEY");
            teaKey = getIntent().getStringExtra("TEAKEY");
            dateOfAttendence = getIntent().getStringExtra("DATEEE");
        }

        if (!semKey.isEmpty()) {
            showStudents(semKey);
        }

        submit_attendence = findViewById(R.id.submit_attend);
        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        AttendenceMap = new HashMap<>();
        date = findViewById(R.id.date);
        scan_qr_button = findViewById(R.id.scan_qr_button);
        showdate = findViewById(R.id.showdate);

        date.setText("Attendance for Date : " + dateOfAttendence);

        perms = new String[]{Manifest.permission.CAMERA};
        scan_qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasCameraPermission()){
                    // Start QR scan activity
                    Intent i = new Intent(attenDence_take.this, qrScanActivity.class);
                    startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                    Toast.makeText(attenDence_take.this, "When Scanning is Complete Press back button", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (EasyPermissions.somePermissionPermanentlyDenied(attenDence_take.this, Arrays.asList(perms))) {
                        new AppSettingsDialog.Builder(attenDence_take.this).build().show();
                    }
                    else{
                        EasyPermissions.requestPermissions(attenDence_take.this, getString(R.string.rationale_camera), 123, Manifest.permission.CAMERA);
                    }
                }
            }
        });

        submit_attendence.setOnClickListener(view -> {
            if ((AttendenceMap != null) && (AttendenceMap.size() == arrayList.size())) {
                uploadToFirebase(dateOfAttendence, AttendenceMap);
            } else {
                Toast.makeText(this, "Please Mark All Students", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, ""+AttendenceMap.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showAll(ArrayList<studentModel> arrayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(attenDence_take.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new AttendenceRecyclerViewAdapter(attenDence_take.this, arrayList, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        progressDialog.dismiss();
    }

    private boolean hasCameraPermission(){
        return EasyPermissions.hasPermissions(this, perms);
    }

    public void showStudents(String key) {
        arrayList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Students").orderByChild("currentSemesterKey").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (!snap.getValue(studentModel.class).getPassedOut()){
                        arrayList.add(snap.getValue(studentModel.class));
                    }
                }

                // Sort the ArrayList using a custom Comparator
                Collections.sort(arrayList, new Comparator<studentModel>() {
                    @Override
                    public int compare(studentModel s1, studentModel s2) {
                        return s1.getName().compareTo(s2.getName());
                    }
                });

                showAll(arrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void uploadToFirebase(String currDate, HashMap<String, String> Map) {

        int totalCount = 0;
        int presentCount = 0;
        int absentCount = 0;
        int leaveCount = 0;

        for (String status : Map.values()) {
            totalCount++;
            switch (status) {
                case "present":
                    presentCount++;
                    break;
                case "absent":
                    absentCount++;
                    break;
                case "leave":
                    leaveCount++;
                    break;
            }
        }

        String hwokey = FirebaseDatabase.getInstance().getReference("Attendance").child(currDate).child(subKey).push().getKey();
        FirebaseDatabase.getInstance().getReference("Attendance").child(currDate).child(subKey).child(hwokey)
                .setValue(new StudentAttendenceModel(hwokey, subKey, teaKey, currDate, "Taken", Map, totalCount, presentCount, absentCount, leaveCount))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(attenDence_take.this, "Attendance has successfully been recorded for for Today", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(attenDence_take.this, TeacherMainscreen.class);
                        intent.putExtra("KEY", teaKey);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    public void onAttendenceChange(HashMap<String, String> hashMap) {
        AttendenceMap = hashMap;
    }

    private void scanQRCode() {
        Intent intent = new Intent(this, qrScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT).show();
        }

        if (requestCode == REQUEST_CODE_QR_SCAN && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra("result");

            // Check if scanned QR code matches any of the student keys
            boolean found = false;
            for (studentModel student : arrayList) {
                if (student.getKey().equals(result)) {
                    AttendenceMap.put(student.getKey(), "present");
                    Toast.makeText(this, student.getName().toString()+" is present", Toast.LENGTH_SHORT).show();
                    int index = arrayList.indexOf(student);
                    View view = recyclerView.getLayoutManager().findViewByPosition(index);
                    if (view != null) {
                        AttendenceRecyclerViewAdapter.ViewHolder viewHolder = (AttendenceRecyclerViewAdapter.ViewHolder) recyclerView.getChildViewHolder(view);
                        viewHolder.present.setChecked(true);
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                Toast.makeText(this, "QR code not recognized", Toast.LENGTH_SHORT).show();
            }
            onAttendenceChange(AttendenceMap);
            scanQRCode(); // Launch scanner again
        }
    }
}