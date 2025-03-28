package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.AdapterOrdersList;
import com.balajiseeds.databinding.ActivityOrdersListBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrdersListActivity extends AppCompatActivity {
    ActivityOrdersListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvOrdersList.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(OrdersListActivity.this));
        binding.rvOrders.setAdapter(new AdapterOrdersList());

        binding.tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(OrdersListActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.tvFromDate.setText(day + "-" + (month + 1) + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                String dateString = binding.tvToDate.getText().toString();
                if (dateString != null && !dateString.isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date date = dateFormat.parse(dateString);
                        long milliseconds = date.getTime();
                        dpd.getDatePicker().setMaxDate(milliseconds);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                dpd.show();
            }
        });
        binding.tvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(OrdersListActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.tvToDate.setText(day + "-" + (month + 1) + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
    }
}