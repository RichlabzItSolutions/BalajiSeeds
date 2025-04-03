package com.balajiseeds.employee.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.balajiseeds.R;
import com.balajiseeds.admin.models.GetOrder.GetOrderDetails;
import com.balajiseeds.databinding.EmpFragmentMyOrdersBinding;
import com.balajiseeds.employee.EmployeeMainActivity;

import com.balajiseeds.employee.adapters.AdapterOrders;
import com.balajiseeds.employee.models.ModelOrder;
import com.balajiseeds.employee.models.MyOrders.EmpOrdersDetails;
import com.balajiseeds.utils.WebServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FragmentMyOrders extends Fragment {
    EmpFragmentMyOrdersBinding binding;
    WebServices webServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=EmpFragmentMyOrdersBinding.inflate(getLayoutInflater());
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getActivity() != null) {
            // Use getActivity() safely without casting
        }
        else {
            BottomNavigationView botnav=((EmployeeMainActivity)getContext()).findViewById(R.id.botnav_employee_main);

            botnav.getMenu().getItem(1).setChecked(true);
        }

        webServices=new WebServices(getContext());
        binding.tvOrders.setOnClickListener(v->{
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchOrders();
    }

    private void fetchOrders() {
       /* webServices.getOrder( new WebServices.onGetOrders() {
            @Override
            public void getOrders(List<ModelOrder.Order> orderList) {
                AdapterOrders adapterOrders = new AdapterOrders(getContext(),orderList);
                binding.rvOrders.setAdapter(adapterOrders);
            }
        });*/
        webServices.EmpOrders( new WebServices.onGetOrdersEmp() {
            @Override
            public void getOrdersEmp(List<EmpOrdersDetails> orderList) {
                AdapterOrders adapterOrders = new AdapterOrders(getContext(),orderList);
                binding.rvOrders.setAdapter(adapterOrders);
            }
        });
    }
}