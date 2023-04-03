package com.rabailalibhatti.attendencesystemuser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendenceRecyclerViewAdapter extends RecyclerView.Adapter<AttendenceRecyclerViewAdapter.ViewHolder> {
    View view;
    private Context context;
    private ArrayList<studentModel> studentModels;
    private AttendenceListener attendenceListener;
    private HashMap<String, String> hashMap = new HashMap<>();

    public AttendenceRecyclerViewAdapter(Context context, ArrayList<studentModel> studentModels, AttendenceListener attendenceListener) {
        this.context = context;
        this.studentModels = studentModels;
        this.attendenceListener = attendenceListener;
    }

    public View getView(){
        return view;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.data_attendence_take, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.number.setText(""+(position+1));
        holder.studentname.setText(studentModels.get(position).getName());

        String key = studentModels.get(holder.getAbsoluteAdapterPosition()).getKey();
        if (hashMap.containsKey(key)) {
            String status = hashMap.get(key);
            switch (status) {
                case "present":
                    holder.present.setChecked(true);
                    attendenceListener.onAttendenceChange(hashMap);
                    break;
                case "absent":
                    holder.absent.setChecked(true);
                    attendenceListener.onAttendenceChange(hashMap);
                    break;
                case "leave":
                    holder.leave.setChecked(true);
                    attendenceListener.onAttendenceChange(hashMap);
                    break;
            }
        } else {
            holder.present.setChecked(false);
            holder.absent.setChecked(false);
            holder.leave.setChecked(false);
            attendenceListener.onAttendenceChange(hashMap);
        }
        
        if (studentModels != null && studentModels.size() > 0){
            holder.present.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.present.isChecked()){
                        holder.absent.setChecked(false);
                        holder.leave.setChecked(false);
                        hashMap.put(key, "present");
                    }
                    else{
                        hashMap.remove(key);
                    }
                    attendenceListener.onAttendenceChange(hashMap);
                }
            });
            holder.absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.absent.isChecked()){
                        holder.present.setChecked(false);
                        holder.leave.setChecked(false);
                        hashMap.put(key, "absent");
                    }
                    else{
                        hashMap.remove(key);
                    }
                    attendenceListener.onAttendenceChange(hashMap);
                }
            });
            holder.leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.leave.isChecked()){
                        holder.present.setChecked(false);
                        holder.absent.setChecked(false);
                        hashMap.put(key, "leave");
                    }
                    else{
                        hashMap.remove(key);
                    }
                    attendenceListener.onAttendenceChange(hashMap);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return studentModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentname, number;
        public CheckBox leave, present, absent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentname = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            leave = itemView.findViewById(R.id.checkLeave);
            present = itemView.findViewById(R.id.checkPresent);
            absent = itemView.findViewById(R.id.checkAbsent);
        }
    }
}
