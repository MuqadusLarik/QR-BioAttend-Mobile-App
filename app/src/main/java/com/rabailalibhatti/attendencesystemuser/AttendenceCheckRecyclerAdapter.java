package com.rabailalibhatti.attendencesystemuser;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendenceCheckRecyclerAdapter extends RecyclerView.Adapter<AttendenceCheckRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<StudentAttendenceModel> arrayList;
    private HashMap<String, String> hashMap = new HashMap<>();

    public AttendenceCheckRecyclerAdapter(Context context, ArrayList<StudentAttendenceModel> arrayList, HashMap<String, String> hashMap) {
        this.context = context;
        this.arrayList = arrayList;
        this.hashMap = hashMap;
    }

    @NonNull
    @Override
    public AttendenceCheckRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_attendence_check, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendenceCheckRecyclerAdapter.ViewHolder holder, int position) {
        holder.number.setText("" + (position + 1));
//            Log.d("dub", "Key: " + entry.getKey() + " Value: " + entry.getValue());
//            Log.d("dub", "Key: " + hashMap.keySet().toArray()[position].toString() + " Value: " + hashMap.get(hashMap.keySet().toArray()[position]));
        FirebaseDatabase.getInstance().getReference("Students").child(hashMap.keySet().toArray()[position].toString())
                .get().addOnCompleteListener(task -> {
                    holder.name.setText(task.getResult().getValue(studentModel.class).getName());

                    if (hashMap.get(hashMap.keySet().toArray()[position]).equals("present")){
                        holder.status.setText("Present");
                        holder.status.setBackgroundColor(Color.GREEN);
                        holder.status.setTextColor(Color.BLACK);
                    }
                    else if (hashMap.get(hashMap.keySet().toArray()[position]).equals("absent")){
                        holder.status.setText("Absent");
                        holder.status.setBackgroundColor(Color.RED);
                        holder.status.setTextColor(Color.BLACK);
                    }
                    else if (hashMap.get(hashMap.keySet().toArray()[position]).equals("leave")){
                        holder.status.setText("Leave");
                        holder.status.setBackgroundColor(Color.GRAY);
                        holder.status.setTextColor(Color.WHITE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return hashMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, status, number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            status = itemView.findViewById(R.id.checkStatus);
        }
    }
}
