package com.rabailalibhatti.attendencesystemuser;

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

public class SubjectRecyclerAdapter extends RecyclerView.Adapter<SubjectRecyclerAdapter.ViewHolder> {
    private ArrayList<subjectModel> arrayList;

    public SubjectRecyclerAdapter(ArrayList<subjectModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SubjectRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_subjects,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectRecyclerAdapter.ViewHolder holder, int position) {
        holder.subName.setText(arrayList.get(position).getSubTitle());
        holder.subCode.setText(arrayList.get(position).getSubCode());
        holder.subCredits.setText(arrayList.get(position).getSubCreditHours());

        FirebaseDatabase.getInstance().getReference("Semesters")
                .child(arrayList.get(position).getSemesterKey())
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    holder.subSem.setText(task.getResult().getValue(semesterModel.class).getSemTitle().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subName,subCode, subCredits, subSem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subName = itemView.findViewById(R.id.teacherName);
            subCode=itemView.findViewById(R.id.teacherNumber);
            subCredits=itemView.findViewById(R.id.subCreditHours);
            subSem=itemView.findViewById(R.id.subSemester);

        }
    }
}
