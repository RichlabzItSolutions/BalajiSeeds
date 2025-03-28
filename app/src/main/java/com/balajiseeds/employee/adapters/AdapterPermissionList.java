package com.balajiseeds.employee.adapters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.AddHolidaysActivity;
import com.balajiseeds.admin.models.DeleteHolidays.DeleteHolidaysJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.databinding.EmpDialogAddAdditionalWorkBinding;
import com.balajiseeds.employee.PermissionsActivity;
import com.balajiseeds.employee.models.DeletePemisoonJson;
import com.balajiseeds.employee.models.GetPermissionDetails;
import com.balajiseeds.employee.models.ModelPermissionAWD;
import com.balajiseeds.employee.models.UpdatePermissionJson;
import com.balajiseeds.utils.API;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.RetrofitClient;
import com.balajiseeds.utils.SharedPref;
import com.balajiseeds.utils.WebServices;

import java.util.Calendar;
import java.util.List;

public class AdapterPermissionList extends RecyclerView.Adapter<AdapterPermissionList.ViewHolder> {
    Context context;
    EmpDialogAddAdditionalWorkBinding binding1;
    List<GetPermissionDetails> permissionList;
    //    onClick onClick;
    WebServices webServices;
    API api;
    String token, Status;
    String type = "";
    String id  = "";


    public interface OnPermissionUpdateListener {
        void onPermissionUpdated();
    }

    OnPermissionUpdateListener updateListener;

    public AdapterPermissionList(Context context, List<GetPermissionDetails> permissionList, OnPermissionUpdateListener updateListener) {
        this.context = context;
        this.permissionList = permissionList;
        this.updateListener = updateListener;
    }


   /* public void updateData(List<GetPermissionDetails> newPermissionList) {
        this.permissionList = newPermissionList;
        notifyDataSetChanged(); // refresh the adapter with new data
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_permisson_emp_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetPermissionDetails permission = permissionList.get(position);

        webServices = new WebServices(context);
        api = RetrofitClient.getRetrofitInstance().create(API.class);
        SharedPref sharedPref = new SharedPref(context);
        if (sharedPref.getString(SharedPref.token) != null && !sharedPref.getString(SharedPref.token).isEmpty()) {
            Log.d("token", "" + sharedPref.getString(SharedPref.token));
            token = sharedPref.getString(SharedPref.token);

        } else {

        }

        holder.tv_from_date.setText(permission.getFromDate());
        holder.tv_to_date.setText(permission.getToDate());
        holder.tv_title.setText(permission.getEmpName());
        holder.tv_Description.setText(permission.getReason());
        holder.tv_id.setText(permission.getEmpId());
        if (permission.getStatus().equals("1")){
            holder.tv_Edit.setText("Accepted");
            holder.tv_Edit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_checkmark_icon, 0, 0, 0);


            holder.tv_Delete.setVisibility(View.GONE);
           /* holder.tv_Delete.setText("Delete");
            holder.tv_Delete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_material_cancel, 0, 0, 0);

            holder.tv_Delete.setOnClickListener(v -> {
                Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                    @Override
                    public void YesClicked() {

                        String ids  = permissionList.get(position).getId();
                        Log.d("vbnm", ids);

                        DeletePemisoonJson request = new DeletePemisoonJson(ids);
                        webServices.deletePermisoon(request, new WebServices.onResponse() {
                            @Override
                            public void response() {
                                // removeAtPosition(position);8
                                permissionList.remove(permissionList.get(holder.getAdapterPosition()));
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        });
                    }
                });
            });*/

        }

        if (permission.getStatus().equals("2")){

            holder.tv_Edit.setText("Rejected");
            holder.tv_Edit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_checkmark_icon, 0, 0, 0);

            holder.tv_Delete.setVisibility(View.GONE);
        }

        if (permission.getStatus().equals("0")){

            holder.tv_Edit.setText("Edit");
            holder.tv_Edit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_awesome_edit, 0, 0, 0);

            holder.tv_Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog = new Dialog(context);
                    binding1 = EmpDialogAddAdditionalWorkBinding.inflate(LayoutInflater.from(context));
                    dialog.setContentView(binding1.getRoot());
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    dialog.show();

                    binding1.tvFromDate.setText(permissionList.get(position).getFromDate());
                    binding1.tvToDate.setText(permissionList.get(position).getToDate());
                    binding1.etReason.setText(permissionList.get(position).getReason());

                    binding1.txtAdditionalWork.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    binding1.tvFromDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    binding1.tvFromDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                                }
                            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                            dpd.show();
                        }
                    });

                    binding1.tvToDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    binding1.tvToDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                                }
                            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                            dpd.show();
                        }
                    });

                    binding1.tvSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String fromDate, toDate, reason;
                            fromDate = binding1.tvFromDate.getText().toString();
                            toDate = binding1.tvToDate.getText().toString();
                            reason = binding1.etReason.getText().toString();
                            if (fromDate.isEmpty()) {
                                Toast.makeText(context, "Please select From date", Toast.LENGTH_SHORT).show();
                            } else if (toDate.isEmpty()) {
                                Toast.makeText(context, "Please select To date", Toast.LENGTH_SHORT).show();
                            } else if (reason.isEmpty()) {
                                Toast.makeText(context, "Please enter reason", Toast.LENGTH_SHORT).show();
                            } else {

                                String ids  = permissionList.get(position).getId();
                                Log.d("vbnm", ids);
                                UpdatePermissionJson awd = new UpdatePermissionJson(ids,fromDate, toDate, reason);
                                webServices.editAdditionalWorkingDay(awd, new WebServices.onResponse() {
                                    @Override
                                    public void response() {
                                        dialog.dismiss();
                                        if (updateListener != null) {
                                            updateListener.onPermissionUpdated();
                                        }
                                    }
                                });

                            }

                        }
                    });

                }
            });

            holder.tv_Delete.setOnClickListener(v -> {
                Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                    @Override
                    public void YesClicked() {

                        String ids  = permissionList.get(position).getId();
                        Log.d("vbnm", ids);

                        DeletePemisoonJson request = new DeletePemisoonJson(ids);
                        webServices.deletePermisoon(request, new WebServices.onResponse() {
                            @Override
                            public void response() {
                                // removeAtPosition(position);8
                                permissionList.remove(permissionList.get(holder.getAdapterPosition()));
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        });
                    }
                });
            });

        }

    }

    @Override
    public int getItemCount() {
        return permissionList.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

    }
    public void removeAtPosition(int position) {
        permissionList.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_from_date, tv_to_date,tv_id, tv_title, tv_Description, tv_Edit, tv_Delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_from_date = itemView.findViewById(R.id.tv_from_date);
            tv_to_date = itemView.findViewById(R.id.tv_to_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_Description = itemView.findViewById(R.id.tv_Description);
            tv_Edit = itemView.findViewById(R.id.tv_Edit);
            tv_Delete = itemView.findViewById(R.id.tv_Delete);

        }
    }
}
