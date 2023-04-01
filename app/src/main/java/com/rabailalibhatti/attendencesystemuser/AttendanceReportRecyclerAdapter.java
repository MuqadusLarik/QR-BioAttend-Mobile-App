package com.rabailalibhatti.attendencesystemuser;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AttendanceReportRecyclerAdapter extends RecyclerView.Adapter<AttendanceReportRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<StudentAttendenceModel> arrayList;
    private String subjectKey;

    public AttendanceReportRecyclerAdapter(Context context, ArrayList<StudentAttendenceModel> arrayList, String subKey) {
        this.context = context;
        this.arrayList = arrayList;
        this.subjectKey = subKey;
    }

    @NonNull
    @Override
    public AttendanceReportRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_attendence_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceReportRecyclerAdapter.ViewHolder holder, int position) {
        holder.date.setText(arrayList.get(position).getDate());
        holder.day.setText(getDayName(arrayList.get(position).getDate()));
        holder.totalCount.setText(String.valueOf(arrayList.get(position).getTotalNumber()));
        holder.presentCount.setText(String.valueOf(arrayList.get(position).getPresentCount()));
        holder.absentCount.setText(String.valueOf(arrayList.get(position).getAbsentCount()));
        holder.leaveCount.setText(String.valueOf(arrayList.get(position).getLeaveCount()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, day, totalCount, presentCount, absentCount, leaveCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            day = itemView.findViewById(R.id.day);
            totalCount = itemView.findViewById(R.id.totalCount);
            presentCount = itemView.findViewById(R.id.presentCount);
            absentCount = itemView.findViewById(R.id.absentCount);
            leaveCount = itemView.findViewById(R.id.leaveCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, date.getText().toString()+" clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, attenDence_check.class);
                    intent.putExtra("DATE", date.getText().toString());
                    intent.putExtra("SUBK", subjectKey);
                    context.startActivity(intent);
                }
            });
        }
    }

    public static String getDayName(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = dateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
            return days[dayOfWeek - 1];
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
