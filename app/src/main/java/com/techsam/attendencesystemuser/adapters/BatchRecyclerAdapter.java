package com.techsam.attendencesystemuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techsam.attendencesystemuser.R;
import com.techsam.attendencesystemuser.objects.Batch;

import java.util.ArrayList;

public class BatchRecyclerAdapter extends RecyclerView.Adapter<BatchRecyclerAdapter.ViewHolder> {
    private ArrayList<Batch> list;
    private Context context;

    public BatchRecyclerAdapter(ArrayList<Batch> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BatchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.designbatch,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchRecyclerAdapter.ViewHolder holder, int position) {
        holder.batchTitle.setText(list.get(position).getBatchTitle());
        holder.startYear.setText(list.get(position).getStartYear());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView batchTitle,startYear,endYear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            batchTitle = itemView.findViewById(R.id.batch);
            startYear = itemView.findViewById(R.id.startyear);


        }
    }
}
