package com.rabailalibhatti.attendencesystemuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AttendenceRecyclerAdapter extends RecyclerView.Adapter<AttendenceRecyclerAdapter.ViewHolder> {
    private ArrayList<PresentAbsent> list;
    private Context context;

    public AttendenceRecyclerAdapter(ArrayList<PresentAbsent> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendenceRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.designstudentattendence,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendenceRecyclerAdapter.ViewHolder holder, int position) {
        holder.studentName.setText(list.get(position).getStudentName());
//        holder.studentRollNo.setText(list.get(position).get);
        if(list.get(position).getStatus()!=null){
            if(list.get(position).getStatus().equals("Present")){
                holder.present.setChecked(true);
            }else if(list.get(position).getStatus().equals("Absent")){
                holder.absent.setChecked(true);
            }else {
                holder.leave.setChecked(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName,studentRollNo;
        RadioButton absent,present,leave;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentname);
            studentRollNo = itemView.findViewById(R.id.studentroll);
            absent = itemView.findViewById(R.id.radio_absent);
            present = itemView.findViewById(R.id.radio_present);
            leave = itemView.findViewById(R.id.radio_leave);

            absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    PresentAbsent pa = new PresentAbsent();
                    pa.setStudentId(list.get(pos).getStudentId());
                    pa.setStudentName(list.get(pos).getStudentName());
                    pa.setStatus("Absent");
//                    AttendenceFragment.list2.add(pa);
                    AttendenceFragment.maps.put(list.get(pos).getStudentId(),pa);
                }
            });
            present.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                PresentAbsent pa = new PresentAbsent();
                pa.setStudentId(list.get(pos).getStudentId());
                pa.setStudentName(list.get(pos).getStudentName());
                pa.setStatus("Present");
//                    AttendenceFragment.list2.add(pa);

                AttendenceFragment.maps.put(list.get(pos).getStudentId(),pa);
            });

            leave.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                PresentAbsent pa = new PresentAbsent();
                pa.setStudentId(list.get(pos).getStudentId());
                pa.setStudentName(list.get(pos).getStudentName());
                pa.setStatus("Leave");
//                    AttendenceFragment.list2.add(pa);

                AttendenceFragment.maps.put(list.get(pos).getStudentId(),pa);
            });




        }
    }
}
