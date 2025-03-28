package com.balajiseeds.admin.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.databinding.DialogApproveLeaveBinding;
import com.balajiseeds.databinding.DialogRejectLeaveBinding;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaves_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLeavesList.ViewHolder holder, int position) {
        ModelLeaves.Leave leave = leaveList.get(position);
        holder.tv_name.setText(leave.getName());
        holder.tv_empid.setText(leave.getEmpId());
        holder.tv_from_date.setText(leave.getFromDate());
        holder.tv_to_date.setText(leave.getToDate());
        holder.tv_reason.setText(leave.getMessage());
        if (Constants.leaveTypeList != null) {
            for (ModelLeaves.LeaveType leaveType : Constants.leaveTypeList) {
                if (leaveType.getId().equals(leave.getLeaveType())) {
                    holder.tv_leavetype.setText(leaveType.getLeaveType());
                    break;
                } else {
                    holder.tv_leavetype.setText("unknown type");
                }
            }
        }
        if (leave.getLeaveStatus().equals("2")) {
            holder.tv_accept.setText("Accepted");
            holder.tv_accept.setEnabled(false);
            holder.tv_reject.setVisibility(View.GONE);
        } else if (leave.getLeaveStatus().equals("3")) {
            holder.tv_reject.setText("Rejected");
            holder.tv_reject.setEnabled(false);
            holder.tv_accept.setVisibility(View.GONE);
        } else if (leave.getLeaveStatus().equals("4")) {
            holder.tv_reject.setText("Cancelled by user");
            holder.tv_reject.setEnabled(false);
            holder.tv_accept.setVisibility(View.GONE);
        }
        holder.tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServices.adminApproveLeave(new ModelLeaves.DeleteLeaveRequest(leaveList.get(holder.getAdapterPosition()).getId()), new WebServices.onResponse() {
                    @Override
                    public void response() {
                        leaveList.get(holder.getAdapterPosition()).setLeaveStatus("2");
                        notifyItemChanged(holder.getAdapterPosition());
                        showSuccessDialog(holder);

                    }
                });
            }
        });
        holder.tv_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRejectDialog(holder);
            }
        });
    }

    private void showSuccessDialog(ViewHolder holder) {
        Dialog d_accept = new Dialog(context);
        DialogApproveLeaveBinding aproveBinding = DialogApproveLeaveBinding.inflate(((Activity) context).getLayoutInflater());
        d_accept.setContentView(aproveBinding.getRoot());
        d_accept.getWindow().setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        d_accept.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d_accept.setCancelable(false);
        ModelLeaves.Leave leave=leaveList.get(holder.getAdapterPosition());
        aproveBinding.tvFdate.setText(leave.getFromDate());
        aproveBinding.tvTdate.setText(leave.getToDate());
        aproveBinding.tvName.setText(leave.getName());
        aproveBinding.tvLeaveType.setText(holder.tv_leavetype.getText().toString());
        aproveBinding.tvReason.setText(leave.getSubject());
        aproveBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_accept.dismiss();
            }
        });
        d_accept.show();
    }

    private void showRejectDialog(ViewHolder holder) {
        Dialog d_reject = new Dialog(context);
        DialogRejectLeaveBinding rejectBinding = DialogRejectLeaveBinding.inflate(((Activity) context).getLayoutInflater());
        d_reject.setContentView(rejectBinding.getRoot());
        d_reject.getWindow().setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        d_reject.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d_reject.setCancelable(false);
        rejectBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_reject.dismiss();
            }
        });
        rejectBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = rejectBinding.etReason.getText().toString();
                if (reason.isEmpty() || reason.length() < 3) {
                    Toast.makeText(context, "Reason should me more than 3 characters", Toast.LENGTH_SHORT).show();
                } else {
                    webServices.adminRejectLeave(new ModelLeaves.adminRejectLeaveRequest(leaveList.get(holder.getAdapterPosition()).getId(), reason), new WebServices.onResponse() {
                        @Override
                        public void response() {
                            d_reject.dismiss();
                            leaveList.get(holder.getAdapterPosition()).setLeaveStatus("3");
                            notifyItemChanged(holder.getAdapterPosition());

                        }
                    });
                }
            }
        });
        d_reject.show();
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
        TextView tv_accept, tv_reject, tv_from_date, tv_to_date, tv_empid, tv_name, tv_leavetype, tv_reason;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_accept = itemView.findViewById(R.id.tv_accept);
            tv_reject = itemView.findViewById(R.id.tv_reject);
            tv_from_date = itemView.findViewById(R.id.tv_from_date);
            tv_to_date = itemView.findViewById(R.id.tv_to_date);
            tv_empid = itemView.findViewById(R.id.tv_empid);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_leavetype = itemView.findViewById(R.id.tv_leave_type);
            tv_reason = itemView.findViewById(R.id.tv_reason);
        }
    }
}
