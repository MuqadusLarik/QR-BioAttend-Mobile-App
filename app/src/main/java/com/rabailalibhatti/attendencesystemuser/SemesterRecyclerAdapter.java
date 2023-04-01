package com.rabailalibhatti.attendencesystemuser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SemesterRecyclerAdapter extends RecyclerView.Adapter<SemesterRecyclerAdapter.ViewHolder> {
    private ArrayList<semesterModel> arrayList;

    public SemesterRecyclerAdapter(ArrayList<semesterModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SemesterRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_semesters, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterRecyclerAdapter.ViewHolder holder, int position) {
        holder.semTitle.setText(arrayList.get(position).getSemTitle());
        holder.semDuration.setText(String.valueOf(arrayList.get(position).getSemNumber()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView semTitle, semDuration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            semTitle = itemView.findViewById(R.id.semTitle);
            semDuration = itemView.findViewById(R.id.semDuration);
        }
    }
}
