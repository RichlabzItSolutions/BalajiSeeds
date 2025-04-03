package com.balajiseeds.admin.adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.fragments.FragmentOrders;
import com.balajiseeds.admin.models.AdminOrders.AdminOrdersDetails;
import com.balajiseeds.admin.models.GetOrder.CancelOrderJson;
import com.balajiseeds.admin.models.GetOrder.GetOrderDetails;
import com.balajiseeds.admin.models.GetOrder.UpdateOrderJson;
import com.balajiseeds.admin.models.UpdateAmount.UpdateAmountDetails;
import com.balajiseeds.admin.models.UpdateAmount.UpdateAmountJson;
import com.balajiseeds.databinding.AdminItemOrderListBinding;
import com.balajiseeds.databinding.DialogSetAmountBinding;
import com.balajiseeds.employee.MyCartActivity;
import com.balajiseeds.employee.OrderDetailsActivity;

import com.balajiseeds.employee.PaidHistoryActivity;
import com.balajiseeds.employee.ProductOrderActivity;
import com.balajiseeds.employee.models.getCartnew.GetCartDetails;
import com.balajiseeds.employee.models.placeOrder.PlaceOrderJson;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.ViewHolder>{
    Context context;
    List<AdminOrdersDetails> orderList;
    WebServices webServices;
    DialogSetAmountBinding setAmountBinding;
    List<String> spinnerItems = new ArrayList<>();
    String paymentMode, OrderId;
    String ids;
    AdminOrdersDetails order;

    public AdapterOrders(Context context, List<AdminOrdersDetails> orderList) {
        this.context = context;
        this.orderList = orderList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public AdapterOrders.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminItemOrderListBinding binding=AdminItemOrderListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdapterOrders.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrders.ViewHolder holder, int position) {
         order=orderList.get(position);
        webServices=new WebServices(context);
        holder.binding.tvOrdrerDate.setText(order.getOrderDate());
        holder.binding.tvNoOfItems.setText(order.getTotalItems());
        holder.binding.tvPartyName.setText(order.getPartyName());
        holder.binding.tvPartyCode.setText(order.getPartyCode());
        holder.binding.OrderID.setText(order.getOrderId());
        holder.binding.tvTotalAmount.setText(order.getTotalAmount());
        holder.binding.tvPaidAmount.setText(order.getTotalPaidAmount());
        holder.binding.tvBalanceAmount.setText(order.getBalanceAmount());

         OrderId = order.getOrderId();
        // Log.d("rt", OrderId);

        holder.binding.tvPaidHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaidHistoryActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                intent.putExtra("orderNumber", order.getOrderNumber());
                context.startActivity(intent);
            }
        });

//        holder.binding.tvAmountEntry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ShowSetAmountDialog(holder, order.getOrderId(),position);
//            }
//        });

        if (order.getOrderStatus().equals("1")) {
            holder.binding.tvOrderUpdate.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.Delivered:
                            popupMenu.dismiss();
                            // showConfirmationDialog();

                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == DialogInterface.BUTTON_POSITIVE) {
                                        // Handle the action when "Yes" is clicked

                                         ids  = orderList.get(position).getOrderId();

                                        UpdateOrderJson request = new UpdateOrderJson(ids, "3");
                                        webServices.OrderAdminUpdate(request, new WebServices.onResponse() {
                                            @Override
                                            public void response() {

                                            }
                                        });

                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Are you sure want to Delivered this Product?")
                                    .setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener)
                                    .show();
                            break;
                        case R.id.inprogress:
                            popupMenu.dismiss();

                            String idss  = orderList.get(position).getOrderId();

                            UpdateOrderJson request = new UpdateOrderJson(idss, "2");
                            webServices.OrderAdminUpdate(request, new WebServices.onResponse() {
                                @Override
                                public void response() {

                                }
                            });

                            break;
                        default:
                            break;
                    }
                    return false;
                });

                popupMenu.show();

            });

        }

        holder.binding.tvViewOrder.setOnClickListener(v->{
            context.startActivity(new Intent(context, OrderDetailsActivity.class)
                    .putExtra("orderId",orderList.get(holder.getAdapterPosition()).getOrderId()));
        });
    }

    public void ShowSetAmountDialog( String orderId) {
        Dialog d_cancel = new Dialog(context);
        setAmountBinding = DialogSetAmountBinding.inflate(((Activity) context).getLayoutInflater());
        d_cancel.setContentView(setAmountBinding.getRoot());
        d_cancel.getWindow().setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        d_cancel.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d_cancel.setCancelable(false);
        setAmountBinding.tvTitle.setText("Set Amount");
        Log.d("bnm", orderId);

        setAmountBinding.ivClose.setOnClickListener(v -> d_cancel.dismiss());

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

        spinnerItems.clear();

        spinnerItems.add("Cheque");
        spinnerItems.add("Cash");
        spinnerItems.add("GPay");
        spinnerItems.add("Paytm");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,  R.layout.simple_spinner_item, spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setAmountBinding.paymentMode.setAdapter(adapter);

        setAmountBinding.paymentMode.setSelection(0, false);

        setAmountBinding.paymentMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paymentMode = (String) parent.getItemAtPosition((int) id);

                //    deliveryType = String.valueOf(binding.driverSpinner.getSelectedItemPosition());
                    Log.d("de",paymentMode);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case where no item is selected if needed
            }
        });

        setAmountBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = setAmountBinding.etEnterAmount.getText().toString().trim();
                String date = setAmountBinding.etSelectDate.getText().toString();
                String ReferenceID = setAmountBinding.etReferenceID.getText().toString().trim();

                if (amount.isEmpty() ) {
                    Toast.makeText(context, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }

               else if (date.isEmpty() ) {
                    Toast.makeText(context, "Please Select Date", Toast.LENGTH_SHORT).show();
                }

                else if (ReferenceID.isEmpty() ) {
                    Toast.makeText(context, "Please Enter Reference ID", Toast.LENGTH_SHORT).show();
                }

                else {

                    UpdateAmountJson request = new UpdateAmountJson(orderId, amount, paymentMode, ReferenceID,  date);
                    webServices.updateAmount(request, new WebServices.onGetUpdateAmountAdmin() {
                        @Override
                        public void getUpdateAmountAdmin(UpdateAmountDetails updateAmountDetails) {
                           // binding.rvCartItems.setAdapter(new AdapterItemCart(context));

                            d_cancel.dismiss();

                        }
                    });
                }
            }
        });
        d_cancel.show();
    }
    private void selectedDate() {
        if (!setAmountBinding.etSelectDate.getText().toString().isEmpty()) {
        }
    }
    @Override
    public int getItemCount() {
        return orderList.size() ;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        AdminItemOrderListBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=AdminItemOrderListBinding.bind(itemView.getRootView());
           binding.tvAmountEntry.setOnClickListener(view -> ShowSetAmountDialog( orderList.get(getAdapterPosition()).getOrderId()));
        }
    }
}
