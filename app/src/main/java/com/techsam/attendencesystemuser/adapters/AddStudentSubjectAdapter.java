package com.techsam.attendencesystemuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techsam.attendencesystemuser.R;
import com.techsam.attendencesystemuser.admin.RegisterStudent;
import com.techsam.attendencesystemuser.objects.Subject;

import java.util.ArrayList;

public class AddStudentSubjectAdapter extends RecyclerView.Adapter<AddStudentSubjectAdapter.ViewHolder> {
    private ArrayList<Subject> list;
    private Context context;

    public AddStudentSubjectAdapter(ArrayList<Subject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AddStudentSubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkboxstudent,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddStudentSubjectAdapter.ViewHolder holder, int position) {
        holder.subName.setText(list.get(position).getSubName());
        holder.subCode.setText(list.get(position).getSubCode());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subName,subCode;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subName = itemView.findViewById(R.id.subjectname);
            subCode=itemView.findViewById(R.id.subjectcode);
            checkBox=itemView.findViewById(R.id.checkboxstudent);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bol) {
                    int position = getAdapterPosition();

                    Subject subject = new Subject();
                    subject.setSubCode(list.get(position).getSubCode());
                    subject.setSubName(list.get(position).getSubName());
                    subject.setSubId(list.get(position).getSubId());
                    RegisterStudent.checkBoxList.add(subject);


                }
            });

        }
    }
}
