package com.techsam.attendencesystemuser.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techsam.attendencesystemuser.R;
import com.techsam.attendencesystemuser.adapters.BatchRecyclerAdapter;
import com.techsam.attendencesystemuser.admin.AddBatches;
import com.techsam.attendencesystemuser.objects.Batch;

import java.util.ArrayList;

public class Batches extends AppCompatActivity {
    RecyclerView recyclerView;
    BatchRecyclerAdapter adapter;
    ArrayList<Batch> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches_screen);
        recyclerView = findViewById(R.id.batchrecycler);
        list = new ArrayList<>();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Batches").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot snap : task.getResult().getChildren()) {
                    Batch batch = snap.getValue(Batch.class);
                    list.add(batch);
                }
                adapter = new BatchRecyclerAdapter(list, Batches.this);
                LinearLayoutManager llm = new LinearLayoutManager(Batches.this, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void addbatch(View view) {
        startActivity(new Intent(Batches.this, AddBatches.class));
    }
}
