package com.balajiseeds.employee.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.balajiseeds.LoginActivity;
import com.balajiseeds.OtpVerificationActivity;
import com.balajiseeds.databinding.FragmentAccount2Binding;
import com.balajiseeds.employee.ActivityListActivity;
import com.balajiseeds.employee.EmployeeMainActivity;
import com.balajiseeds.employee.ExpensesActivity;
import com.balajiseeds.employee.HolidaysActivity;
import com.balajiseeds.employee.LeaveListActivity;
import com.balajiseeds.employee.MonthlyAttendanceActivity;
import com.balajiseeds.employee.PermissionsActivity;
import com.balajiseeds.employee.VehicleReadingActivity;
import com.balajiseeds.utils.ForegroundService;
import com.balajiseeds.utils.SharedPref;


public class AccountFragment extends Fragment {


    FragmentAccount2Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccount2Binding.inflate(inflater, container, false);

        binding.cvLogout.setOnClickListener(v -> {
            try {
                Intent serviceIntent = new Intent(getContext(), ForegroundService.class);
                getContext().stopService(serviceIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SharedPref(getContext()).clearAll();
            startActivity(new Intent(getContext(), LoginActivity.class));
            ((Activity) getContext()).finish();
        });
        binding.cvAttendance.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MonthlyAttendanceActivity.class));
        });
        binding.cvLeaves.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LeaveListActivity.class));
        });
        binding.cvVehicalReading.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), VehicleReadingActivity.class));
        });

        binding.cvHolidays.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), HolidaysActivity.class));
        });
        binding.cvChangePass.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), OtpVerificationActivity.class);
            i.putExtra("mob", new SharedPref(getContext()).getString(SharedPref.mobile));
            startActivity(i);
        });
        binding.cvActivity.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ActivityListActivity.class));
        });
        binding.cvExpences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ExpensesActivity.class));
            }
        });
        binding.cvOrders.setOnClickListener(v->{
            ((EmployeeMainActivity)getContext()).loadFrag(new FragmentMyOrders());
        });
        binding.cvPermission.setOnClickListener(v->{
            startActivity(new Intent(getContext(), PermissionsActivity.class));
        });


        return binding.getRoot();
    }
}