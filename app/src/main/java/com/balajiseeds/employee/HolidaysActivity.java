package com.balajiseeds.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.balajiseeds.R;
import com.balajiseeds.admin.AddHolidaysActivity;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysJson;
import com.balajiseeds.databinding.ActivityHolidaysEmployesBinding;
import com.balajiseeds.employee.adapters.AdapterHolidaysList;
import com.balajiseeds.utils.API;
import com.balajiseeds.utils.SharedPref;
import com.balajiseeds.utils.WebServices;

import java.util.List;

public class HolidaysActivity extends AppCompatActivity {

    ActivityHolidaysEmployesBinding  binding;

    API api;
    String token, Status ;
    WebServices webServices;
    String stateId  = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHolidaysEmployesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webServices = new WebServices(this);

        binding.txtAdHolidaysList.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });


        SharedPref sharedPref = new SharedPref(HolidaysActivity.this);
        if (sharedPref.getString(SharedPref.token) != null && !sharedPref.getString(SharedPref.token).isEmpty()) {
            Log.d("token", "" + sharedPref.getString(SharedPref.token));

            token = sharedPref.getString(SharedPref.token);

            fetchExpenses();

        } else {


        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchExpenses();
    }

    public void fetchExpenses() {
       // GetHolidaysJson request = new GetHolidaysJson(stateId);
        webServices.geEmployeHolidays( new WebServices.onGetEmpHolidays() {
            @Override
            public void getEmpHolidays(List<GetHolidaysDetails> holodaysList) {
                binding.rvHolidaysList.setLayoutManager(new LinearLayoutManager(HolidaysActivity.this));
                binding.rvHolidaysList.setAdapter(new AdapterHolidaysList(HolidaysActivity.this, holodaysList));
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }

}