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
    EditText title,startyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batches);
        title = findViewById(R.id.batchtitle);
        startyear = findViewById(R.id.startyear);
    }

    public void submit(View view) {

        String batchtitle = title.getText().toString();
        String startYear = startyear.getText().toString();


        DatabaseReference db;
        db= FirebaseDatabase.getInstance().getReference();
        String id = db.child("Batches").push().getKey();

        Batch batch = new Batch();
        batch.setBatchTitle(batchtitle);
        batch.setStartYear(startYear);


        db.child("Batches").child(id).setValue(batch).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                title.setText("");
                startyear.setText("");
                Toast.makeText(AddBatches.this, "Batch Added", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
