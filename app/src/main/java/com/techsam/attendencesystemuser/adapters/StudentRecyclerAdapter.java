package com.techsam.attendencesystemuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techsam.attendencesystemuser.R;
import com.techsam.attendencesystemuser.models.Student;

import java.util.ArrayList;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> {
    private ArrayList<Student> list;
    private Context context;

    public StudentRecyclerAdapter(ArrayList<Student> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.designstudentlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentRecyclerAdapter.ViewHolder holder, int position) {
        holder.studentName.setText(list.get(position).getName());
        holder.studentRollNo.setText(list.get(position).getRollNo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName,studentRollNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentname);
            studentRollNo = itemView.findViewById(R.id.studentroll);


        }
    }
}
