package com.rabailalibhatti.attendencesystemuser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherRecyclerAdapter extends RecyclerView.Adapter<TeacherRecyclerAdapter.ViewHolder> {
    private ArrayList<teacherModel> arrayList;

    public TeacherRecyclerAdapter(ArrayList<teacherModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TeacherRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_teachers,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherRecyclerAdapter.ViewHolder holder, int position) {
        holder.tName.setText(arrayList.get(position).getName());
        holder.tNumber.setText(arrayList.get(position).getContact());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tName, tNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tName = itemView.findViewById(R.id.teacherName);
            tNumber = itemView.findViewById(R.id.teacherNumber);


        }
    }
}
