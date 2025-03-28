package com.balajiseeds.admin.adapters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.balajiseeds.admin.models.AcceptPermisoonJson;
import com.balajiseeds.databinding.EmpDialogAddAdditionalWorkBinding;
import com.balajiseeds.employee.models.DeletePemisoonJson;
import com.balajiseeds.employee.models.GetPermissionDetails;
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

    public interface OnPermissionAdminUpdateListener {
        void onPermissionAdminUpdated();
    }

    OnPermissionAdminUpdateListener updateAdminListener;


    public interface OnPermissionAdminRejectListener {
        void onPermissionAdminRejected();
    }

    OnPermissionAdminRejectListener rejectAdminListener;

    public AdapterPermissionList(Context context, List<GetPermissionDetails> permissionList, OnPermissionAdminUpdateListener updateAdminListener, OnPermissionAdminRejectListener rejectAdminListener) {
        this.context = context;
        this.permissionList = permissionList;
        this.updateAdminListener = updateAdminListener;
        this.rejectAdminListener = rejectAdminListener;

    }

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

          //  fetchExpenses();

            // webServices = new WebServices(HolidaysActivity.this);

            //  api = RetrofitClient.getRetrofitInstance().create(API.class);

            //  getHolidaysList();


        } else {


        }

        holder.tv_from_date.setText(permission.getFromDate());
        holder.tv_to_date.setText(permission.getToDate());
//        holder.tv_exp_head.setText(expenses.getExpenseId());
       /* holder.tv_from_date.setText(holidays.getFromDate());
        holder.tv_to_date.setText(holidays.getToDate());*/
        holder.tv_title.setText(permission.getEmpName());
        holder.tv_Description.setText(permission.getReason());
        holder.tv_id.setText(permission.getEmpId());
       /* holder.tv_State.setText(holidays.getStateName());
        holder.tv_Description.setText(holidays.getDescription());
        holder.tv_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        if (permission.getStatus().equals("2")){

            holder.tv_Delete.setText("Rejected");
            holder.tv_Delete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_material_cancel, 0, 0, 0);

            holder.tv_Edit.setText("Accept");
            holder.tv_Edit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_checkmark_icon, 0, 0, 0);

            holder.tv_Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Constants.ShowAcceptDialog(context, new Constants.OnYesClicked() {
                        @Override
                        public void YesClicked() {

                            String ids  = permissionList.get(position).getId();
                            Log.d("vbnm", ids);

                            AcceptPermisoonJson request = new AcceptPermisoonJson(ids);
                            webServices.acceptAdditionalWorkingDay(request, new WebServices.onResponse() {
                                @Override
                                public void response() {

                                    if (updateAdminListener != null) {
                                        updateAdminListener.onPermissionAdminUpdated();
                                    }
                                }
                            });
                        }
                    });

                }
            });


        }



        if (permission.getStatus().equals("1")){

            holder.tv_Edit.setText("Accepted");
            holder.tv_Edit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_checkmark_icon, 0, 0, 0);

            holder.tv_Delete.setText("Reject");
            holder.tv_Delete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_material_cancel, 0, 0, 0);


            holder.tv_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Constants.ShowRejectDialog(context, new Constants.OnYesClicked() {
                        @Override
                        public void YesClicked() {

                            String ids  = permissionList.get(position).getId();
                            Log.d("vbnm", ids);

                            AcceptPermisoonJson request = new AcceptPermisoonJson(ids);
                            webServices.rejectAdditionalWorkingDay(request, new WebServices.onResponse() {
                                @Override
                                public void response() {

                                    if (rejectAdminListener != null) {
                                        rejectAdminListener.onPermissionAdminRejected();
                                    }
                                }
                            });
                        }
                    });

                }
            });
        }
        if (permission.getStatus().equals("0")){

            holder.tv_Delete.setText("Reject");
            holder.tv_Delete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_material_cancel, 0, 0, 0);


            holder.tv_Edit.setText("Accept");
            holder.tv_Edit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_checkmark_icon, 0, 0, 0);


            holder.tv_Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Constants.ShowAcceptDialog(context, new Constants.OnYesClicked() {
                        @Override
                        public void YesClicked() {

                            String ids  = permissionList.get(position).getId();
                            Log.d("vbnm", ids);

                            AcceptPermisoonJson request = new AcceptPermisoonJson(ids);
                            webServices.acceptAdditionalWorkingDay(request, new WebServices.onResponse() {
                                @Override
                                public void response() {

                                    if (updateAdminListener != null) {
                                        updateAdminListener.onPermissionAdminUpdated();
                                    }
                                }
                            });
                        }
                    });

                }
            });


            holder.tv_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Constants.ShowRejectDialog(context, new Constants.OnYesClicked() {
                        @Override
                        public void YesClicked() {

                            String ids  = permissionList.get(position).getId();
                            Log.d("vbnm", ids);

                            AcceptPermisoonJson request = new AcceptPermisoonJson(ids);
                            webServices.rejectAdditionalWorkingDay(request, new WebServices.onResponse() {
                                @Override
                                public void response() {

                                    if (rejectAdminListener != null) {
                                        rejectAdminListener.onPermissionAdminRejected();
                                    }
                                }
                            });
                        }
                    });

                }
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
       //webServices.dismissDialog();
    }
//    public interface onClick{
//        public void onEditClisked(ModelExpenses.Expenses expenses);
//
//    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_from_date, tv_id, tv_to_date, tv_title, tv_Description, tv_Edit, tv_Delete;

        //        tv_edit,tv_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_from_date = itemView.findViewById(R.id.tv_from_date);
            tv_to_date = itemView.findViewById(R.id.tv_to_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_id = itemView.findViewById(R.id.tv_id);
//            tv_edit=itemView.findViewById(R.id.tv_edit);
//            tv_delete=itemView.findViewById(R.id.tv_delete);
            tv_Description = itemView.findViewById(R.id.tv_Description);
            tv_Edit = itemView.findViewById(R.id.tv_Edit);
            tv_Delete = itemView.findViewById(R.id.tv_Delete);

        }
    }
}
