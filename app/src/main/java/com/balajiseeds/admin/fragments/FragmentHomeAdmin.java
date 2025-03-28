package com.balajiseeds.admin.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.balajiseeds.LoginActivity;
import com.balajiseeds.OtpVerificationActivity;
import com.balajiseeds.R;
import com.balajiseeds.admin.ActivityListActivity;
import com.balajiseeds.admin.AdminMainActivity;
import com.balajiseeds.admin.AttendanceListActivity;
import com.balajiseeds.admin.EmployeeListActivity;
import com.balajiseeds.admin.ExpensesActivity;
import com.balajiseeds.admin.ExpensesHeadsActivity;
import com.balajiseeds.admin.HolidaysActivity;
import com.balajiseeds.admin.LeavesListActivity;
import com.balajiseeds.admin.PermissionActivity;
import com.balajiseeds.admin.VehicleReadingActivity;
import com.balajiseeds.databinding.FragmentHomeAdminBinding;
import com.balajiseeds.utils.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentHomeAdmin extends Fragment {
    FragmentHomeAdminBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeAdminBinding.inflate(inflater, container, false);
        BottomNavigationView botnav = getActivity().findViewById(R.id.botnav_admin_main);
        botnav.getMenu().getItem(0).setChecked(true);
        binding.cvEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EmployeeListActivity.class));
            }
        });
        binding.cvAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AttendanceListActivity.class));
            }
        });
        binding.cvLeaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LeavesListActivity.class));
            }
        });

        binding.cvExpensesHeaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ExpensesHeadsActivity.class));
            }
        });
        binding.cvProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AdminMainActivity) getContext()).loadFrag(new FragmentProductListMain());
            }
        });
        binding.cvOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AdminMainActivity) getContext()).loadFrag(new FragmentOrders());
            }
        });
        binding.cvLogout.setOnClickListener(v -> {
            new SharedPref(getContext()).clearAll();
            startActivity(new Intent(getContext(), LoginActivity.class));
            ((Activity) getContext()).finish();
        });
        binding.cvActivity.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ActivityListActivity.class));
        });

        binding.cvHolidays.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), HolidaysActivity.class));
        });

        binding.cvPermission.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PermissionActivity.class));
        });

        binding.cvVehicleReadings.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), VehicleReadingActivity.class));
        });
        binding.cvExpenses.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ExpensesActivity.class));
        });
        binding.cvChangePass.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), OtpVerificationActivity.class);
            i.putExtra("mob", new SharedPref(getContext()).getString(SharedPref.mobile));
            startActivity(i);
        });
        return binding.getRoot();
    }
}
