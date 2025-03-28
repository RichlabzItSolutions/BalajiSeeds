package com.balajiseeds.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.balajiseeds.R;
import com.balajiseeds.databinding.ActivityOrderDetailsBinding;
import com.balajiseeds.employee.adapters.AdapterOrdersProducts;
import com.balajiseeds.employee.models.ModelOrder;
import com.balajiseeds.models.ModelCities;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.util.List;

public class OrderDetailsActivity extends BaseActivity {
    ActivityOrderDetailsBinding binding;
    WebServices webServices;
    String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices=new WebServices(OrderDetailsActivity.this);
        binding.tvOrderDetails.setOnClickListener(v->{
            getOnBackPressedDispatcher().onBackPressed();
        });
        orderId=getIntent().getStringExtra("orderId");
        //getStateCity();
        getOrderDetails();

    }

    /*private void getStateCity() {
        webServices.GetStates(new WebServices.onGetStates() {
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
        });
    }*/

    private void getOrderDetails() {
        webServices.getOrderDetails(orderId, new WebServices.onGetOrders() {
            @Override
            public void getOrders(List<ModelOrder.Order> orderList) {
                ModelOrder.Order order=orderList.get(0);
                binding.tvDate.setText(order.getOrderDate());
                binding.tvNos.setText(order.getTotalItems());
                binding.tvStatus.setText(Constants.getOrderStatus(order.getStatus()));
                binding.tvCustomerName.setText(order.getName());
                binding.tvCustomerMob.setText(order.getMobile());
                binding.tvCity.setText(order.getCity_name());
                binding.tvState.setText(order.getState_name());
             /*   binding.tvCustomerName.setText(order.getCustomerName());
                if(Constants.stateList!=null){
                    binding.tvState.setText(Constants.getStateNameById(order.getState()));
                }
                if(Constants.citiesList!=null){
                    binding.tvCity.setText(Constants.getCityNameById(order.getCity()));
                }
                binding.tvCustomerMob.setText(order.getNumber());*/
                binding.rvProducts.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this));
                boolean status=false;
                if(order.getStatus().equals("1")){
                    status=true;
                }
                binding.rvProducts.setAdapter(new AdapterOrdersProducts(OrderDetailsActivity.this,order.getProducts(),status));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }
}