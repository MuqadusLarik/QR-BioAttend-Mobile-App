package com.rabailalibhatti.attendencesystemuser;

import android.content.Context;
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

public class SubjectsCheckRecyclerAdapter extends RecyclerView.Adapter<SubjectsCheckRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<subjectModel> arrayList;
    private ArrayList<studentModel> studentModels;
    private String TAG = "dubai";

    public SubjectsCheckRecyclerAdapter(Context context, ArrayList<subjectModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SubjectsCheckRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_subjects_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsCheckRecyclerAdapter.ViewHolder holder, int position) {
        holder.subjectName.setText(arrayList.get(position).getSubTitle());
        holder.subjectCode.setText(arrayList.get(position).getSubCode());

        FirebaseDatabase.getInstance().getReference("Semesters").child(arrayList.get(position).getSemesterKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    holder.subjectSemester.setText(task.getResult().getValue(semesterModel.class).getSemTitle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName ,subjectCode,        subjectSemester;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            subjectCode = itemView.findViewById(R.id.subjectCode);
            subjectSemester = itemView.findViewById(R.id.subjectSemester);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    Intent intent = new Intent(context, SubjectAssignedStudentCheck.class);
                    intent.putExtra("SEMKEY", arrayList.get(pos).getSemesterKey());
                    context.startActivity(intent);
                }
            });
        }
    }
}
