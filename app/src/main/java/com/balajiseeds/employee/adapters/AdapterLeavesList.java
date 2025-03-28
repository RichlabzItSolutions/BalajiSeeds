package com.balajiseeds.employee.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.databinding.DialogApproveLeaveBinding;
import com.balajiseeds.databinding.DialogRejectLeaveBinding;
import com.balajiseeds.employee.AddLeaveActivity;
import com.balajiseeds.employee.models.ModelLeaves;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.util.List;

public class AdapterLeavesList extends RecyclerView.Adapter<AdapterLeavesList.ViewHolder> {
    Context context;
    List<ModelLeaves.Leave> leaveList;
    WebServices webServices;

    public AdapterLeavesList(Context context, List<ModelLeaves.Leave> leaveList) {
        this.context = context;
        this.leaveList = leaveList;
        webServices = new WebServices(context);
        webServices.getLeavesTypeList(new WebServices.onFetchLeaveType() {
            @Override
            public void fetchedLeaveType(List<ModelLeaves.LeaveType> leaveTypeList) {
                Constants.leaveTypeList = leaveTypeList;
            }
        });

    }

    @NonNull
    @Override
    public AdapterLeavesList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_item_leaves_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLeavesList.ViewHolder holder, int position) {
        ModelLeaves.Leave leave = leaveList.get(position);
        holder.tv_fdate.setText(leave.getFromDate());
        holder.tv_tdate.setText(leave.getToDate());
        for (ModelLeaves.LeaveType leaveType : Constants.leaveTypeList) {
            if (leaveType.getId().equals(leave.getLeaveType())) {
                holder.tv_leavetype.setText(leaveType.getLeaveType());
                break;
            } else {
                holder.tv_leavetype.setText("unknown type");
            }
        }

        holder.tv_subject.setText(leave.getSubject());
        holder.tv_reason.setText(leave.getMessage());
        holder.tv_reject_reason.setText(leave.getLeaveRejectReason());
        //default-1, approval-2, reject-3
        if (leave.getLeaveStatus().equals("3")) {
            holder.tr_reject_reason.setVisibility(View.VISIBLE);
        } else {
            holder.tr_reject_reason.setVisibility(View.GONE);
        }
        switch (leave.getLeaveStatus()) {
            case "1":
                holder.tv_status.setText("In Progress");
                break;
            case "2":
                holder.tv_status.setText("Approved");
                break;
            case "3":
                holder.tv_status.setText("Rejected");
                break;
            case "4":
                holder.tv_status.setText("Cancelled by user");
                break;
        }
        if (leave.getLeaveStatus().equals("1")) {
            holder.tv_edit.setVisibility(View.VISIBLE);
            holder.tv_delete.setVisibility(View.VISIBLE);
            holder.tv_cancel.setVisibility(View.VISIBLE);
        } else if (leave.getLeaveStatus().equals("2")) {
            holder.tv_edit.setVisibility(View.GONE);
            holder.tv_delete.setVisibility(View.GONE);
            holder.tv_cancel.setVisibility(View.VISIBLE);
        } else if (leave.getLeaveStatus().equals("3")) {
            holder.tv_edit.setVisibility(View.GONE);
            holder.tv_delete.setVisibility(View.GONE);
            holder.tv_cancel.setVisibility(View.GONE);
        } else if (leave.getLeaveStatus().equals("4")) {
            holder.tv_edit.setVisibility(View.GONE);
            holder.tv_delete.setVisibility(View.GONE);
            holder.tv_cancel.setVisibility(View.GONE);
        }
        holder.tv_cancel.setOnClickListener(v -> {
            ShowCancelDialog(holder);
        });


        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SelectedLeave = leaveList.get(holder.getAdapterPosition());
                context.startActivity(new Intent(context, AddLeaveActivity.class));
            }
        });
        holder.tv_delete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                    webServices.deleteLeave(new ModelLeaves.DeleteLeaveRequest(leaveList.get(holder.getAdapterPosition()).getId()), new WebServices.onResponse() {
                        @Override
                        public void response() {
                            leaveList.remove(leaveList.get(holder.getAdapterPosition()));
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    });
                }
            });
        });
    }

    private void ShowCancelDialog(ViewHolder holder) {
        Dialog d_cancel = new Dialog(context);
        DialogRejectLeaveBinding cancelBinding = DialogRejectLeaveBinding.inflate(((Activity) context).getLayoutInflater());
        d_cancel.setContentView(cancelBinding.getRoot());
        d_cancel.getWindow().setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        d_cancel.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d_cancel.setCancelable(false);
        cancelBinding.tvTitle.setText("Cancel Leave Request");
        cancelBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_cancel.dismiss();
            }
        });
        cancelBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = cancelBinding.etReason.getText().toString();
                if (reason.isEmpty() || reason.length() < 3) {
                    Toast.makeText(context, "Reason should me more than 3 characters", Toast.LENGTH_SHORT).show();
                } else {
                    webServices.cancelLeaveEmp(new ModelLeaves.cancelLeaveRequest(leaveList.get(holder.getAdapterPosition()).getId(), reason), new WebServices.onResponse() {
                        @Override
                        public void response() {
                            d_cancel.dismiss();
                            leaveList.get(holder.getAdapterPosition()).setLeaveStatus("4");
                            notifyItemChanged(holder.getAdapterPosition());

                        }
                    });
                }
            }
        });
        d_cancel.show();
    }


    @Override
    public int getItemCount() {
        return leaveList.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        webServices.dismissDialog();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_fdate, tv_tdate, tv_name, tv_leavetype, tv_subject, tv_reason, tv_reject_reason, tv_edit, tv_delete, tv_status, tv_cancel;
        TableRow tr_reject_reason;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_fdate = itemView.findViewById(R.id.tv_from_date);
            tv_tdate = itemView.findViewById(R.id.tv_to_date);
            tv_leavetype = itemView.findViewById(R.id.tv_leave_type);
            tv_subject = itemView.findViewById(R.id.tv_subject);
            tv_reason = itemView.findViewById(R.id.tv_reason);
            tv_reject_reason = itemView.findViewById(R.id.tv_rejected_reason);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_status = itemView.findViewById(R.id.tv_status);
            tr_reject_reason = itemView.findViewById(R.id.tr_reject_reason);
            tv_cancel = itemView.findViewById(R.id.tv_cancel);
        }
    }
}
