package com.balajiseeds.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.balajiseeds.R;
import com.balajiseeds.admin.ProductActivity;
import com.balajiseeds.databinding.ActivityProductBinding;
import com.balajiseeds.databinding.ActivityProductOrderBinding;
import com.balajiseeds.employee.fragments.FragmentMyOrders;

public class ProductOrderActivity extends AppCompatActivity {

    ActivityProductOrderBinding binding;

    String orderId, orderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        orderId = intent.getStringExtra("orderId");
        orderNumber = intent.getStringExtra("orderNumber");


        binding.orderId.setText(orderNumber);

        binding.continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductOrderActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

        binding.trackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentMyOrders fragmentMyOrders = new FragmentMyOrders();
               /* Bundle bundle = new Bundle();
                bundle.putString("orderId", orderId);
                bundle.putString("orderNumber", orderNumber);
                fragmentMyOrders.setArguments(bundle);*/

                // Navigate to the fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragmentMyOrders) // Replace with your container ID
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}