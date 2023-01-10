package com.techsam.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.techsam.attendencesystemuser.adapters.SubjectRecyclerAdapter;
import com.techsam.attendencesystemuser.objects.Student;
import com.techsam.attendencesystemuser.objects.Subject;
import com.techsam.attendencesystemuser.objects.Teacher;

public class StudentMainscreen extends AppCompatActivity {
    ImageView img;
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mainscreen);
        img = findViewById(R.id.generatedqr);


        welcome = findViewById(R.id.stuwelcome);


        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Students").child(MainActivity.userKey).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Student student = task.getResult().getValue(Student.class);
                welcome.setText("Welcome "+student.getName());

            }

        });


        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(MainActivity.userKey, BarcodeFormat.QR_CODE, 400, 400);;
            img.setImageBitmap(bitmap);
        } catch(Exception e) {

        }



    }
}