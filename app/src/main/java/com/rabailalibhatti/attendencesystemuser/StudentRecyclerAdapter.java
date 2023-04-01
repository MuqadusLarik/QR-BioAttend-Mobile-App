package com.rabailalibhatti.attendencesystemuser;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> {
    private ArrayList<studentModel> arrayList;

    public StudentRecyclerAdapter(ArrayList<studentModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public StudentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_students,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentRecyclerAdapter.ViewHolder holder, int position) {
        holder.studentName.setText(arrayList.get(position).getName());
        holder.studentRollNo.setText(arrayList.get(position).getRollNumber());

        FirebaseDatabase.getInstance().getReference("Batches").child(arrayList.get(position).getBatchKey())
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            holder.studentBatch.setText(task.getResult().getValue(batchModel.class).getBatchTitle().toString());
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName,studentRollNo, studentBatch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.teacherName);
            studentRollNo = itemView.findViewById(R.id.teacherNumber);
            studentBatch = itemView.findViewById(R.id.subCreditHours);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAbsoluteAdapterPosition();
                    Intent intent = new Intent(view.getContext(), RegisterStudent.class);
                    intent.putExtra("KEY", arrayList.get(pos).getKey());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
