package com.rabailalibhatti.attendencesystemuser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectsStudentRecyclerAdapter extends RecyclerView.Adapter<SubjectsStudentRecyclerAdapter.ViewHolder> {
    private ArrayList<studentModel> arrayList;

    public SubjectsStudentRecyclerAdapter(ArrayList<studentModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SubjectsStudentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_subjects_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsStudentRecyclerAdapter.ViewHolder holder, int position) {
            holder.stdName.setText(arrayList.get(position).getName());
            holder.stdRoll.setText(arrayList.get(position).getRollNumber());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stdName, stdRoll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stdName = itemView.findViewById(R.id.stdName);
            stdRoll = itemView.findViewById(R.id.stdRoll);
        }
    }
}
