package com.balajiseeds.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.balajiseeds.R;
import com.balajiseeds.admin.PermissionActivity;
import com.balajiseeds.admin.adapters.AdapterOrders;
import com.balajiseeds.admin.adapters.AdapterPermissionList;
import com.balajiseeds.admin.models.AdminOrders.AdminOrdersDetails;
import com.balajiseeds.admin.models.GetOrder.GetOrderDetails;
import com.balajiseeds.databinding.FragmentOrdersBinding;


import com.balajiseeds.employee.EmployeeMainActivity;
import com.balajiseeds.utils.WebServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FragmentOrders extends Fragment {
    FragmentOrdersBinding binding;

    WebServices webServices;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);

        webServices=new WebServices(getContext());
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        /*binding.rvOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvOrders.setAdapter(new AdapterOrders());*/
        BottomNavigationView botnav = getActivity().findViewById(R.id.botnav_admin_main);
        botnav.getMenu().getItem(1).setChecked(true);

       /* BottomNavigationView botnav=((EmployeeMainActivity)getContext()).findViewById(R.id.botnav_employee_main);
        botnav.getMenu().getItem(1).setChecked(true);*/

        binding.tvOrders.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        //fetchOrders();
        return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        fetchAdminOrders();
    }


    private void fetchAdminOrders() {

        webServices.AdminOrders( new WebServices.onGetOrdersAdminNew() {
            @Override
            public void getOrdersAdminNew(List<AdminOrdersDetails> orderList) {
                AdapterOrders adapterOrders = new AdapterOrders(getContext(),orderList);
                binding.rvOrders.setAdapter(adapterOrders);
            }
        });
    }



}
