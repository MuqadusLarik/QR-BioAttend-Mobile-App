package com.rabailalibhatti.attendencesystemuser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BatchRecyclerAdapter extends RecyclerView.Adapter<BatchRecyclerAdapter.ViewHolder> {
    private ArrayList<batchModel> arrayList;

    public BatchRecyclerAdapter(ArrayList<batchModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public BatchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_batches,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchRecyclerAdapter.ViewHolder holder, int position) {
        holder.batchTitle.setText(arrayList.get(position).getBatchTitle());
        holder.startYear.setText(arrayList.get(position).getStartYear());
        holder.endYear.setText(arrayList.get(position).getEndYear());
        holder.status.setText(arrayList.get(position).getBatchStatus());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView batchTitle,startYear,endYear, status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            batchTitle = itemView.findViewById(R.id.teacherName);
            startYear = itemView.findViewById(R.id.teacherNumber);
            endYear = itemView.findViewById(R.id.subCreditHours);
            status = itemView.findViewById(R.id.status);
        }
    }
}
