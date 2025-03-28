package com.balajiseeds.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.AdapterOrders;
import com.balajiseeds.admin.adapters.PaidHistoryAdminAdapter;
import com.balajiseeds.admin.models.AdminOrders.AdminOrdersDetails;
import com.balajiseeds.admin.models.PaidHistory.PaidHistoryDetails;
import com.balajiseeds.admin.models.PaidHistory.PaidHistoryJson;
import com.balajiseeds.admin.models.UpdateAmount.UpdateAmountJson;
import com.balajiseeds.databinding.ActivityPaidHistoryBinding;
import com.balajiseeds.databinding.ActivityProductOrderBinding;
import com.balajiseeds.utils.WebServices;

import java.util.List;

public class PaidHistoryActivity extends AppCompatActivity {

    ActivityPaidHistoryBinding binding;

    String orderId, orderNumber;
    WebServices webServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaidHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webServices=new WebServices(PaidHistoryActivity.this);

        Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        orderId = intent.getStringExtra("orderId");
        orderNumber = intent.getStringExtra("orderNumber");

        binding.txtPaidHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.rvPaidHistory.setLayoutManager(new LinearLayoutManager(PaidHistoryActivity.this));

        fetchPaidHistory();

    }

    private void fetchPaidHistory() {

        PaidHistoryJson request = new PaidHistoryJson(orderId);

        webServices.paidHistory(request, new WebServices.onGetPaidHistoryAdmin() {
            @Override
            public void getPaidHistoryAdmin(List<PaidHistoryDetails> orderList) {
                PaidHistoryAdminAdapter paidHistoryAdminAdapter = new PaidHistoryAdminAdapter(PaidHistoryActivity.this,orderList);
                binding.rvPaidHistory.setAdapter(paidHistoryAdminAdapter);
            }
        });
    }



}