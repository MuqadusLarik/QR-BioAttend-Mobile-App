package com.techsam.attendencesystemuser.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techsam.attendencesystemuser.R;
import com.techsam.attendencesystemuser.admin.RegisterTeacher;
import com.techsam.attendencesystemuser.objects.Subject;

import java.util.ArrayList;

public class AddSubjectRecyclerAdapter extends RecyclerView.Adapter<AddSubjectRecyclerAdapter.ViewHolder> {
    private ArrayList<Subject> list;
    private Context context;

    public AddSubjectRecyclerAdapter(ArrayList<Subject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AddSubjectRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.designaddsubjects,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddSubjectRecyclerAdapter.ViewHolder holder, int position) {
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
            checkBox=itemView.findViewById(R.id.checkboxteacher);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int pos = getAdapterPosition();

                    Subject subject = new Subject();
                    subject.setSubCode(list.get(pos).getSubCode());
                    subject.setSubName(list.get(pos).getSubName());
                    subject.setSubId(list.get(pos).getSubId());

                    RegisterTeacher.checkBoxList.add(subject);


                }
            });

        }
    }
}
