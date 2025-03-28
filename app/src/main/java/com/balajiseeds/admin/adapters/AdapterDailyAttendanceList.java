package com.balajiseeds.admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.TrackEmployeeActivity;
import com.balajiseeds.admin.models.ModelAttendance;

import java.util.List;

public class AdapterDailyAttendanceList extends RecyclerView.Adapter<AdapterDailyAttendanceList.ViewHolder> {
    Context context;
    List<ModelAttendance.DailyAttendance> attendanceList;

    public AdapterDailyAttendanceList(Context context, List<ModelAttendance.DailyAttendance> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public AdapterDailyAttendanceList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_attendance_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDailyAttendanceList.ViewHolder holder, int position) {
        ModelAttendance.DailyAttendance attendance = attendanceList.get(position);
        holder.tv_emp_code.setText(attendance.getEmpCode());
        holder.tv_name.setText(attendance.getName());
        holder.tv_mob.setText(attendance.getMobile());
        holder.tv_login_time.setText(attendance.getLoginTime());
        holder.tv_track_emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TrackEmployeeActivity.class);
                i.putExtra("empid", attendanceList.get(holder.getAdapterPosition()).getEmpId());
                i.putExtra("empName", attendanceList.get(holder.getAdapterPosition()).getName());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_emp_code, tv_name, tv_mob, tv_login_time, tv_track_emp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_emp_code = itemView.findViewById(R.id.tv_emp_code);
            tv_name = itemView.findViewById(R.id.tv_emp_name);
            tv_mob = itemView.findViewById(R.id.tv_emp_mob);
            tv_login_time = itemView.findViewById(R.id.tv_login_time);
            tv_track_emp = itemView.findViewById(R.id.tv_track_emp);
        }
    }
}
