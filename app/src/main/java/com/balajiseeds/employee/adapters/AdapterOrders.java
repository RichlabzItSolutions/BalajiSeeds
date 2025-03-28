package com.balajiseeds.employee.adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.AddHolidaysActivity;
import com.balajiseeds.databinding.DialogRejectLeaveBinding;
import com.balajiseeds.databinding.DialogSetAmountBinding;
import com.balajiseeds.databinding.EmpItemOrderListBinding;
import com.balajiseeds.employee.OrderDetailsActivity;
import com.balajiseeds.employee.models.ModelLeaves;
import com.balajiseeds.employee.models.ModelOrder;
import com.balajiseeds.employee.models.MyOrders.EmpOrdersDetails;
import com.balajiseeds.models.ModelCities;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.ViewHolder>{

    Context context;
    List<EmpOrdersDetails> orderList;
    WebServices webServices;

    DialogSetAmountBinding setAmountBinding;

    private String[] paymentTypeArray;

    List<String> spinnerItems = new ArrayList<>();

    public AdapterOrders(Context context, List<EmpOrdersDetails> orderList) {
        this.context = context;
        this.orderList = orderList;
        webServices=new WebServices(context);
       /* webServices.GetStates(new WebServices.onGetStates() {
            @Override
            public void getStates(List<ModelStates.State> stateList) {
                Constants.stateList=stateList;
            }
        });
        webServices.getAllCities(new WebServices.onGetCities() {
            @Override
            public void getCities(List<ModelCities.Cities> citiesList) {
                Constants.citiesList=citiesList;
            }
        });*/

    }

    @NonNull
    @Override
    public AdapterOrders.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EmpItemOrderListBinding binding=EmpItemOrderListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrders.ViewHolder holder, int position) {
        EmpOrdersDetails order=orderList.get(position);
       /* holder.binding.tvOrdrerDate.setText(order.getOrderDate());
        holder.binding.tvNoOfItems.setText(order.getOrderNumber());
        holder.binding.tvPartyName.setText(order.getPartyName());

        holder.binding.tvPartyCode.setText(order.getPartyCode());
        holder.binding.OrderID.setText(order.getOrderId());*/

        holder.binding.tvOrdrerDate.setText(order.getOrderDate());
        holder.binding.tvNoOfItems.setText(order.getTotalItems());
        holder.binding.tvPartyName.setText(order.getPartyName());

        holder.binding.tvPartyCode.setText(order.getPartyCode());
        holder.binding.OrderID.setText(order.getOrderId());


        holder.binding.tvTotalAmount.setText(order.getTotalAmount());
        holder.binding.tvPaidAmount.setText(order.getTotalPaidAmount());
        holder.binding.tvBalanceAmount.setText(order.getBalanceAmount());



       /* holder.binding.tvPaidHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.binding.tvAmountEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowSetAmountDialog(holder);

            }
        });
*/

       /* holder.binding.tvCustomerName.setText(order.getCustomerName());
        holder.binding.tvCustomerMob.setText(order.getNumber());
        if(Constants.citiesList!=null) {
            holder.binding.tvCity.setText(Constants.getCityNameById(order.getCity()));
        }
        if(Constants.stateList!=null) {
            holder.binding.tvState.setText(Constants.getStateNameById(order.getState()));
        }*/
       /* holder.binding.tvStatus.setText(Constants.getOrderStatus(order.getStatus()));
        holder.binding.tvViewOrder.setOnClickListener(v->{
            context.startActivity(new Intent(context, OrderDetailsActivity.class)
                    .putExtra("orderId",orderList.get(holder.getAdapterPosition()).getOrderId()));
        });*/
    }


 /*   private void ShowSetAmountDialog(AdapterOrders.ViewHolder holder) {
        Dialog d_cancel = new Dialog(context);
        setAmountBinding = DialogSetAmountBinding.inflate(((Activity) context).getLayoutInflater());
        d_cancel.setContentView(setAmountBinding.getRoot());
        d_cancel.getWindow().setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        d_cancel.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d_cancel.setCancelable(false);
        setAmountBinding.tvTitle.setText("Set Amount");
        setAmountBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_cancel.dismiss();
            }
        });

        setAmountBinding.etSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        setAmountBinding.etSelectDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                        selectedDate();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

       *//* paymentTypeArray = new String[]{
                "Online", "Cash"
        };

        // Create adapters for month and year spinners
        ArrayAdapter<String> adapter_month = new ArrayAdapter<>(context, R.layout.simple_spinner_item, paymentTypeArray);
        adapter_month.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        setAmountBinding.spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*//*



        spinnerItems.add("Online");  // Add a dummy item to show as "not selected"
        spinnerItems.add("Cash");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,  R.layout.simple_spinner_item, spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setAmountBinding.paymentMode.setAdapter(adapter);

        setAmountBinding.paymentMode.setSelection(0, false);


        setAmountBinding.paymentMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  deliveryType = (String) parent.getItemAtPosition((int) id);

            //    deliveryType = String.valueOf(binding.driverSpinner.getSelectedItemPosition());
            //    Log.d("de",deliveryType);


              //  homeFragmentViewModel.homePost(foodTitle, foodCategory, deliveryType, rangeOfDelivery, area);
                //deliveryType = selectedDriver.get(selectedDriver);
                //Toast.makeText(context, "Selected: " + selectedDriver, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case where no item is selected if needed
            }
        });



        setAmountBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = setAmountBinding.etEnterAmount.getText().toString();
                String date = setAmountBinding.etSelectDate.getText().toString();
                String ReferenceID = setAmountBinding.etReferenceID.getText().toString();

                if (amount.isEmpty() ) {
                    Toast.makeText(context, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }

                if (date.isEmpty() ) {
                    Toast.makeText(context, "Please Select Date", Toast.LENGTH_SHORT).show();
                }

                if (ReferenceID.isEmpty() ) {
                    Toast.makeText(context, "Please Enter Reference ID", Toast.LENGTH_SHORT).show();
                }

              *//*  if (reason.isEmpty() || reason.length() < 3) {
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
                }*//*
            }
        });
        d_cancel.show();
    }*/



    private void selectedDate() {
        if (!setAmountBinding.etSelectDate.getText().toString().isEmpty()) {
            // setExpensesList();
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EmpItemOrderListBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=EmpItemOrderListBinding.bind(itemView.getRootView());
        }
    }
}
