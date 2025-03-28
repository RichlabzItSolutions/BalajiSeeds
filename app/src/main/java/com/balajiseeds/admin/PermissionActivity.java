package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.AdapterPermissionList;
import com.balajiseeds.databinding.ActivityHolidaysBinding;
import com.balajiseeds.databinding.ActivityPermissionBinding;
import com.balajiseeds.employee.PermissionsActivity;

import com.balajiseeds.employee.models.GetPermissionDetails;
import com.balajiseeds.utils.WebServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PermissionActivity extends AppCompatActivity {
   ActivityPermissionBinding binding;

    WebServices webServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(binding.getRoot());


        webServices = new WebServices(PermissionActivity.this);

        loadPermissionsList();
       /* webServices.adminAdditionalWorkingDayRequest(new WebServices.onGetAwd() {
            @Override
            public void getAwd(List<GetPermissionDetails> getPermissionDetails) {
                Log.e("listaws", "" + getPermissionDetails.size());

                binding.rvAditionalDays.setLayoutManager(new LinearLayoutManager(PermissionActivity.this));
                binding.rvAditionalDays.setAdapter(new AdapterPermissionList(PermissionActivity.this, getPermissionDetails));

            }
        });*/


        binding.txtAdminPermission.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });



    }

    private void loadPermissionsList() {


        webServices.adminAdditionalWorkingDayRequest(new WebServices.onGetAwd() {
            @Override
            public void getAwd(List<GetPermissionDetails> getPermissionDetails) {
                AdapterPermissionList adapter = new AdapterPermissionList(PermissionActivity.this, getPermissionDetails, new AdapterPermissionList.OnPermissionAdminUpdateListener() {
                    @Override
                    public void onPermissionAdminUpdated() {
                        loadPermissionsList();

                    }
                }, new AdapterPermissionList.OnPermissionAdminRejectListener() {
                    @Override
                    public void onPermissionAdminRejected() {
                        loadPermissionsList();

                    }
                } );
                binding.rvAditionalDays.setLayoutManager(new LinearLayoutManager(PermissionActivity.this));
                binding.rvAditionalDays.setAdapter(adapter);
            }
        });
    }
}