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
import com.balajiseeds.admin.AttendanceDetailActivity;
import com.balajiseeds.employee.models.ModelMonthlyAttendance;

import java.util.List;

public class AdapterMonthlyAttendanceList extends RecyclerView.Adapter<AdapterMonthlyAttendanceList.ViewHolder> {
    Context context;
    List<ModelMonthlyAttendance.UserAttendance> attendanceList;

    public AdapterMonthlyAttendanceList(Context context, List<ModelMonthlyAttendance.UserAttendance> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public AdapterMonthlyAttendanceList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monthly_attendance, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMonthlyAttendanceList.ViewHolder holder, int position) {
        ModelMonthlyAttendance.UserAttendance attendance = attendanceList.get(position);
        holder.tv_empid.setText(attendance.getEmpId());
        holder.tv_name.setText(attendance.getName());
        holder.tv_salary.setText(attendance.getSalary());
        holder.tv_present.setText("Present : " + attendance.getTotalPresent());
        holder.tv_absent.setText("Absent : " + attendance.getTotalAbsent());
        holder.tv_holidays.setText("Holidays : " + attendance.getTotalHolidays());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AttendanceDetailActivity.class).putExtra("name", attendanceList.get(holder.getAdapterPosition()).getName()).putExtra("salary", attendanceList.get(holder.getAdapterPosition()).getSalary()).putExtra("id", attendanceList.get(holder.getAdapterPosition()).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_empid, tv_name, tv_salary, tv_present, tv_absent, tv_holidays;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_empid = itemView.findViewById(R.id.tv_empid);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_salary = itemView.findViewById(R.id.tv_salary);
            tv_present = itemView.findViewById(R.id.tv_present);
            tv_absent = itemView.findViewById(R.id.tv_absent);
            tv_holidays = itemView.findViewById(R.id.tv_holidays);

        }
    }
}
