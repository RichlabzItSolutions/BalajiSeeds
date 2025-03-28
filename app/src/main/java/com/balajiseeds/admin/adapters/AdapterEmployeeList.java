package com.balajiseeds.admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.AddEmployeeActivity;
import com.balajiseeds.admin.TrackEmployeeActivity;
import com.balajiseeds.admin.models.ModelChangeStatus;
import com.balajiseeds.admin.models.ModelEmployee;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdapterEmployeeList extends RecyclerView.Adapter<AdapterEmployeeList.ViewHolder> {
    Context context;
    List<ModelEmployee.EmpRequest> empList;
    WebServices webServices;
    String status;


    public AdapterEmployeeList(Context context, List<ModelEmployee.EmpRequest> empList) {
        this.context = context;
        this.empList = empList;
        webServices = new WebServices(context);
    }

    @NonNull
    @Override
    public AdapterEmployeeList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_employee_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEmployeeList.ViewHolder holder, int position) {
        ModelEmployee.EmpRequest emp = empList.get(position);
        holder.tv_state.setText(Constants.getStateNameById(emp.getState()));
        holder.tv_city.setText(Constants.getCityNameById(emp.getCity()));
        holder.tv_emp_name.setText(emp.getName());
        holder.tv_emp_code.setText(emp.getEmpCode());
        holder.tv_emp_mob.setText(emp.getMobile());
        if (emp.getStatus().equals("1")) {
            holder.sw_active.setChecked(true);
            holder.sw_active.setText("Active");
        } else {
            holder.sw_active.setChecked(false);
            holder.sw_active.setText("InActive");
        }
        holder.sw_active.setOnClickListener(v->{
            if (holder.sw_active.isChecked()) {
                status = "1";
                holder.sw_active.setText("Active");
            } else {
                status = "0";
                holder.sw_active.setText("InActive");
            }
            webServices.changeEmpStatus(new ModelChangeStatus(status, empList.get(holder.getAdapterPosition()).getId()), new WebServices.onResponse() {
                @Override
                public void response() {

                }
            });
        });
//        holder.sw_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    status = "1";
//                    holder.sw_active.setText("Active");
//                } else {
//                    status = "0";
//                    holder.sw_active.setText("InActive");
//                }
//                webServices.changeEmpStatus(new ModelChangeStatus(status, empList.get(holder.getAdapterPosition()).getId()), new WebServices.onResponse() {
//                    @Override
//                    public void response() {
//
//                    }
//                });
//            }
//        });
        holder.tv_delete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                    ModelEmployee.deleteEmpRequest request = new ModelEmployee.deleteEmpRequest(empList.get(holder.getAdapterPosition()).getId());
                    webServices.DeleteEmp(request, new WebServices.onResponse() {
                        @Override
                        public void response() {
                            empList.remove(empList.get(holder.getAdapterPosition()));
                            notifyDataSetChanged();
                        }
                    });
                }
            });

        });
        holder.tv_edit.setOnClickListener(v -> {
            Constants.SelectedEMP = empList.get(holder.getAdapterPosition());
            context.startActivity(new Intent(context, AddEmployeeActivity.class));
        });

    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        webServices.dismissDialog();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_state, tv_city, tv_emp_code, tv_emp_name, tv_emp_mob, tv_edit, tv_delete;
        Switch sw_active;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_city = itemView.findViewById(R.id.tv_city);
            tv_emp_code = itemView.findViewById(R.id.tv_emp_code);
            tv_emp_name = itemView.findViewById(R.id.tv_emp_name);
            tv_emp_mob = itemView.findViewById(R.id.tv_emp_mob);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            sw_active = itemView.findViewById(R.id.switch_active);
        }
    }
}
