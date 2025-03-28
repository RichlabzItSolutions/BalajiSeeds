package com.balajiseeds.employee;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.balajiseeds.admin.HolidaysActivity;
import com.balajiseeds.admin.adapters.AdapterHolidaysList;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysJson;
import com.balajiseeds.databinding.EmpActivityPermissionsBinding;
import com.balajiseeds.databinding.EmpDialogAddAdditionalWorkBinding;
import com.balajiseeds.employee.adapters.AdapterPermissionList;
import com.balajiseeds.employee.models.GetPermissionDetails;
import com.balajiseeds.employee.models.ModelPermissionAWD;
import com.balajiseeds.utils.WebServices;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.MaterialCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PermissionsActivity extends AppCompatActivity {
    EmpActivityPermissionsBinding binding;
    EmpDialogAddAdditionalWorkBinding binding1;
    WebServices webServices;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmpActivityPermissionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webServices = new WebServices(PermissionsActivity.this);
       /* webServices.fetchAdditionalWorkingRequest(new WebServices.onGetAwd() {
            @Override
            public void getAwd(List<GetPermissionDetails> getPermissionDetails) {
                Log.e("listaws", "" + getPermissionDetails.size());

                binding.rvAditionalDays.setLayoutManager(new LinearLayoutManager(PermissionsActivity.this));
                binding.rvAditionalDays.setAdapter(new AdapterPermissionList(PermissionsActivity.this, getPermissionDetails, new AdapterPermissionList.OnPermissionUpdateListener()));

            }
        });*/

        loadPermissionsList();

        binding.txtEmpPermission.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        binding.tvAddExpenses.setOnClickListener(v -> {
            addPermissionDialog();
        });

    }

    private void selectedDate() {

    }

    private void loadPermissionsList() {
        webServices.fetchAdditionalWorkingRequest(new WebServices.onGetAwd() {
            @Override
            public void getAwd(List<GetPermissionDetails> getPermissionDetails) {
                AdapterPermissionList adapter = new AdapterPermissionList(PermissionsActivity.this, getPermissionDetails, new AdapterPermissionList.OnPermissionUpdateListener() {
                    @Override
                    public void onPermissionUpdated() {
                        // Reload permissions when an update occurs
                        loadPermissionsList();
                    }
                });
                binding.rvAditionalDays.setLayoutManager(new LinearLayoutManager(PermissionsActivity.this));
                binding.rvAditionalDays.setAdapter(adapter);
            }
        });
    }

    private void addPermissionDialog() {
        Dialog dialog = new Dialog(PermissionsActivity.this);
        binding1 = EmpDialogAddAdditionalWorkBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding1.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        binding1.txtAdditionalWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        binding1.tvFromDate.setText(date);
        binding1.tvToDate.setText(date);
        binding1.tvFromDate.setOnClickListener(v -> {
            DatePickerDialog dpd = new DatePickerDialog(PermissionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    binding1.tvFromDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            dpd.show();
        });

        binding1.tvToDate.setOnClickListener(v -> {
            DatePickerDialog dpd = new DatePickerDialog(PermissionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    binding1.tvToDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            dpd.show();

        });

        binding1.tvSubmit.setOnClickListener(v -> {
            String fromDate, toDate, reason;
            fromDate = binding1.tvFromDate.getText().toString();
            toDate = binding1.tvToDate.getText().toString();
            reason = binding1.etReason.getText().toString();
            if (fromDate.isEmpty()) {
                Toast.makeText(PermissionsActivity.this, "Please select From date", Toast.LENGTH_SHORT).show();
            } else if (toDate.isEmpty()) {
                Toast.makeText(PermissionsActivity.this, "Please select To date", Toast.LENGTH_SHORT).show();
            } else if (reason.isEmpty()) {
                Toast.makeText(PermissionsActivity.this, "Please enter reason", Toast.LENGTH_SHORT).show();
            } else {
                ModelPermissionAWD.awd awd = new ModelPermissionAWD.awd(fromDate, toDate, reason);
                webServices.requestAdditionalWorkingDay(awd, new WebServices.onResponse() {
                    @Override
                    public void response() {
                        dialog.dismiss();
                        loadPermissionsList();
                    }
                });

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }

}