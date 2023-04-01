package com.rabailalibhatti.attendencesystemuser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssignSubjectsShowRecyclerAdapter extends RecyclerView.Adapter<AssignSubjectsShowRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<assignSubjectsShowList> arrayList;

    public AssignSubjectsShowRecyclerAdapter(Context context, ArrayList<assignSubjectsShowList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AssignSubjectsShowRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_assignsubjects, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignSubjectsShowRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.subjectName.setText(arrayList.get(position).getSubjectName());

        FirebaseDatabase.getInstance().getReference("Subjects").child(arrayList.get(position).getSubjectKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("Semesters").child(task.getResult().getValue(subjectModel.class).getSemesterKey())
                            .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    holder.subSemester.setText(task.getResult().getValue(semesterModel.class).getSemTitle());
                                }
                            });
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAssignSubject(arrayList.get(position).getSubjectKey());
                arrayList.remove(position);
                // Notify the adapter about the item removal
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });
    }

    private void removeAssignSubject(String subKey){
        DatabaseReference subjectRef = FirebaseDatabase.getInstance().getReference("Subjects").child(subKey);
        subjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjectModel subject = snapshot.getValue(subjectModel.class);

                subject.setAssignedTo("");

                subjectRef.setValue(subject).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Subject UnAssigned", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Retrieval failed
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName, subSemester;
        ImageView deleteButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            subSemester = itemView.findViewById(R.id.subSemester);
            deleteButton = itemView.findViewById(R.id.delbtn);
        }
    }
}
