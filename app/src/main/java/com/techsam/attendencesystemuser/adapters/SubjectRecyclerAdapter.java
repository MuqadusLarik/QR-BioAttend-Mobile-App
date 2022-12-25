package com.techsam.attendencesystemuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techsam.attendencesystemuser.R;
import com.techsam.attendencesystemuser.objects.Subject;

import java.util.ArrayList;

public class SubjectRecyclerAdapter extends RecyclerView.Adapter<SubjectRecyclerAdapter.ViewHolder> {
    private ArrayList<Subject> list;
    private Context context;

    public SubjectRecyclerAdapter(ArrayList<Subject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.designsubject,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectRecyclerAdapter.ViewHolder holder, int position) {
        holder.subName.setText(list.get(position).getSubName());
        holder.subCode.setText(list.get(position).getSubCode());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subName,subCode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subName = itemView.findViewById(R.id.subname);
            subCode=itemView.findViewById(R.id.subcode);
        }
    }
}
