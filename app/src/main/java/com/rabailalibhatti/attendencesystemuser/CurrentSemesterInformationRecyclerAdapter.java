package com.rabailalibhatti.attendencesystemuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CurrentSemesterInformationRecyclerAdapter extends RecyclerView.Adapter<CurrentSemesterInformationRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<subjectModel> arrayList;

    public CurrentSemesterInformationRecyclerAdapter(Context context, ArrayList<subjectModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CurrentSemesterInformationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_subjects_students, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentSemesterInformationRecyclerAdapter.ViewHolder holder, int position) {
        holder.subName.setText(arrayList.get(position).getSubTitle());
        holder.subCode.setText(arrayList.get(position).getSubCode());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subName, subCode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subName = itemView.findViewById(R.id.subjectName);
            subCode = itemView.findViewById(R.id.subjectCode);
        }
    }
}
