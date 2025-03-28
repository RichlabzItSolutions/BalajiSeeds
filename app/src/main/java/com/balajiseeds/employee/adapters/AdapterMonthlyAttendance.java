package com.balajiseeds.employee.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.employee.models.ModelMonthlyAttendance;

import java.util.List;

public class AdapterMonthlyAttendance extends RecyclerView.Adapter<AdapterMonthlyAttendance.ViewHolder> {
    Context context;
    List<ModelMonthlyAttendance.Attendance> attendancesList;

    public AdapterMonthlyAttendance(Context context, List<ModelMonthlyAttendance.Attendance> attendancesList) {
        this.context = context;
        this.attendancesList = attendancesList;
    }

    @NonNull
    @Override
    public AdapterMonthlyAttendance.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_item_monthly_attendance, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMonthlyAttendance.ViewHolder holder, int position) {
        ModelMonthlyAttendance.Attendance attendance = attendancesList.get(position);
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F7F8FA"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.tv_date.setText(attendance.getDate());
        holder.tv_login_time.setText(attendance.getLoginTime());
        //status 1=Present, 2=Absent, 3=Holiday, 4=Leave
        if (attendance.getStatus().equals("1")) {
            holder.tv_status.setTextColor(Color.parseColor("#278B2B"));
            holder.tv_status.setText("Present");
        } else if (attendance.getStatus().equals("2")) {
            holder.tv_status.setTextColor(Color.parseColor("#CB7097"));
            holder.tv_status.setText("Absent");
        } else if (attendance.getStatus().equals("3")) {
            holder.tv_status.setTextColor(Color.parseColor("#5EA5C8"));
            holder.tv_status.setText("Holiday");
        } else if (attendance.getStatus().equals("4")) {
            holder.tv_status.setTextColor(Color.parseColor("#EB7F23"));
            holder.tv_status.setText("Leave");
        } else {
            holder.tv_status.setTextColor(Color.parseColor("#4B4B4B"));
            holder.tv_status.setText("-");
        }


    }

    @Override
    public int getItemCount() {
        return attendancesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_login_time, tv_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_login_time = itemView.findViewById(R.id.tv_login_time);
            tv_status = itemView.findViewById(R.id.tv_status);
        }
    }
}
