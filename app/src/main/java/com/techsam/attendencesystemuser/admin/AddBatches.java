package com.techsam.attendencesystemuser.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techsam.attendencesystemuser.R;
import com.techsam.attendencesystemuser.objects.Batch;

public class AddBatches extends AppCompatActivity {
    EditText title,startyear,endyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batches);
        title = findViewById(R.id.batchtitle);
        startyear = findViewById(R.id.startyear);
        endyear = findViewById(R.id.endyear);
    }

    public void submit(View view) {

        String batchtitle = title.getText().toString();
        String startYear = startyear.getText().toString();
        String endYear = endyear.getText().toString();


        DatabaseReference db;
        db= FirebaseDatabase.getInstance().getReference();
        Batch batch = new Batch();
        batch.setBatchTitle(batchtitle);
        batch.setStartYear(startYear);
        batch.setEndYear(endYear);


        db.child("Batches").push().setValue(batch).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                title.setText("");
                startyear.setText("");
                endyear.setText("");
                Toast.makeText(AddBatches.this, "Batch Added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
