package com.rabailalibhatti.attendencesystemuser;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TeacherSubjectsRecyclerAdapter extends RecyclerView.Adapter<TeacherSubjectsRecyclerAdapter.ViewHolder> {
    private ArrayList<teacherSubjectsModel> teacherSubjectsList;

    public TeacherSubjectsRecyclerAdapter(ArrayList<teacherSubjectsModel> teacherSubjectsList) {
        this.teacherSubjectsList = teacherSubjectsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_teacher_subjects, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        teacherSubjectsModel teacherSubjects = teacherSubjectsList.get(position);
        holder.teacherNameTextView.setText(teacherSubjects.getTeacherName());

        LinearLayout subjectsListLayout = holder.subjectsListTextView;
        subjectsListLayout.removeAllViews();

        List<String> subjectsList = teacherSubjects.getSubKeys();
        for (String subject : subjectsList) {
            FirebaseDatabase.getInstance().getReference("Subjects").child(subject).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    TextView subjectTextView = new TextView(holder.itemView.getContext());
                    subjectTextView.setText(task.getResult().getValue(subjectModel.class).getSubTitle());
                    subjectsListLayout.addView(subjectTextView);
                    Log.d("dub", "onComplete: "+task.getResult().getValue(subjectModel.class).getSubTitle());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return teacherSubjectsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView teacherNameTextView;
        private LinearLayout subjectsListTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherNameTextView = itemView.findViewById(R.id.teacher_name_text_view);
            subjectsListTextView = itemView.findViewById(R.id.subjects_list_layout);
        }
    }
}
