package com.balajiseeds.admin.adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.models.AdminOrders.AdminOrdersDetails;
import com.balajiseeds.admin.models.PaidHistory.PaidHistoryDetails;
import com.balajiseeds.databinding.AdminItemOrderListBinding;

import com.balajiseeds.databinding.PaidHistoryLayoutBinding;
import com.balajiseeds.employee.PaidHistoryActivity;
import com.balajiseeds.utils.WebServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PaidHistoryAdminAdapter extends RecyclerView.Adapter<PaidHistoryAdminAdapter.ViewHolder>{

    Context context;
    List<PaidHistoryDetails> paidHistoryDetailsList;
    WebServices webServices;


    List<String> spinnerItems = new ArrayList<>();


    public PaidHistoryAdminAdapter(Context context, List<PaidHistoryDetails> paidHistoryDetailsList) {
        this.context = context;
        this.paidHistoryDetailsList = paidHistoryDetailsList;
        webServices=new WebServices(context);
        notifyDataSetChanged();

    }

  /*  public void updateOrders(List<AdminOrdersDetails> newOrderList) {
        this.orderList = newOrderList;
        notifyDataSetChanged(); // Refresh the RecyclerView
    }*/

    @NonNull
    @Override
    public PaidHistoryAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PaidHistoryLayoutBinding binding=PaidHistoryLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PaidHistoryAdminAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull PaidHistoryAdminAdapter.ViewHolder holder, int position) {
        PaidHistoryDetails order=paidHistoryDetailsList.get(position);

        holder.binding.tvDate.setText(order.getPaymentDate());
        holder.binding.tvAmount.setText(order.getAmount());
        holder.binding.tvPaymentMode.setText(order.getPaymentMode());
        holder.binding.tvReferenceId.setText(order.getReferenceId());

    }

    @Override
    public int getItemCount() {
        return paidHistoryDetailsList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PaidHistoryLayoutBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=PaidHistoryLayoutBinding.bind(itemView.getRootView());
        }
    }
}
