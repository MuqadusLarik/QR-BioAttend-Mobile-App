package com.rabailalibhatti.attendencesystemuser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AttendenceSubjectRecyclerAdapter extends RecyclerView.Adapter<AttendenceSubjectRecyclerAdapter.ViewHolder>{
    private Context context;
    private ArrayList<attendenceSubjectData> arrayList;
    private String semNamee, teacherKey;

    public AttendenceSubjectRecyclerAdapter(Context context, ArrayList<attendenceSubjectData> arrayList, String teacherKey) {
        this.context = context;
        this.arrayList = arrayList;
        this.teacherKey = teacherKey;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_attendence_subjects, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.subjectName.setText(arrayList.get(position).getSubjectName());
        FirebaseDatabase.getInstance().getReference("Semesters").child(arrayList.get(position).getSemesterKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    holder.subSemester.setText(task.getResult().getValue(semesterModel.class).getSemTitle());
                }
            }
        });

        if (arrayList.get(position).getStatus().equals("Taken")){
            holder.attendenceStatus.setText("Attendence Taken for Today");
            holder.attendenceStatus.setBackgroundColor(Color.GREEN);
        }
        else if (arrayList.get(position).getStatus().equals("No")){
            holder.attendenceStatus.setText("Attendence Not Taken");
            holder.attendenceStatus.setBackgroundColor(Color.RED);
            holder.buttonTake.setVisibility(View.VISIBLE);
        }

        holder.buttonTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, attenDence_take.class);
                intent.putExtra("SUBKEY", arrayList.get(position).getSubjectKey());
                intent.putExtra("SEMKEY", arrayList.get(position).getSemesterKey());
                intent.putExtra("TEAKEY", teacherKey);
                intent.putExtra("DATEEE", arrayList.get(position).getDateee());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName, subSemester, attendenceStatus;
        Button buttonTake;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            subSemester = itemView.findViewById(R.id.subSemester);
            attendenceStatus = itemView.findViewById(R.id.attendenceStatus);
            buttonTake = itemView.findViewById(R.id.buttonTake);
        }
    }
}
