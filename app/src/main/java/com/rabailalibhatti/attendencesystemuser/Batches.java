package com.rabailalibhatti.attendencesystemuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Batches extends AppCompatActivity {
    RecyclerView recyclerView;
    BatchRecyclerAdapter adapter;
    ArrayList<batchModel> list;

    MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches_screen);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        recyclerView = findViewById(R.id.batchrecycler);
        list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Batches").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    for (DataSnapshot snap : task.getResult().getChildren()) {
                        batchModel model = snap.getValue(batchModel.class);
                        if (model != null && model.getBatchStatus() != "Ended"){
                            list.add(model);
                        }
                    }
                    adapter = new BatchRecyclerAdapter(list);
                    recyclerView.setAdapter(adapter);
                }
                else{
                    Toast.makeText(Batches.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addbatch(View view) {
        startActivity(new Intent(Batches.this, AddBatches.class));
    }
}
