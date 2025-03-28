package com.balajiseeds.employee;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.balajiseeds.databinding.EmpActivityActivityListBinding;
import com.balajiseeds.employee.adapters.AdapterActivityList;
import com.balajiseeds.employee.models.ModelActivity;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityListActivity extends BaseActivity {
    EmpActivityActivityListBinding binding;
    WebServices webServices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmpActivityActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(ActivityListActivity.this);
        binding.rvActivity.setLayoutManager(new LinearLayoutManager(ActivityListActivity.this));
        binding.tvAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SelectedActivity = null;
                startActivity(new Intent(ActivityListActivity.this, AddActivityActivity.class));
            }
        });
        binding.txtActivityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(ActivityListActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.etFromDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                        selectedDate();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                String dateString = binding.etToDate.getText().toString();
                if (dateString != null && !dateString.isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date date = dateFormat.parse(dateString);
                        long milliseconds = date.getTime();
                        dpd.getDatePicker().setMaxDate(milliseconds);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                dpd.show();
            }
        });
        binding.etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.etFromDate.getText().toString().isEmpty()) {
                    DatePickerDialog dpd = new DatePickerDialog(ActivityListActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            binding.etToDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                            selectedDate();
                        }
                    }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    String dateString = binding.etFromDate.getText().toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date date = dateFormat.parse(dateString);
                        long milliseconds = date.getTime();
                        dpd.getDatePicker().setMinDate(milliseconds);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dpd.show();
                } else {
                    Toast.makeText(ActivityListActivity.this, "Select from date first", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        fetchActivityList();

    }

    private void fetchActivityList() {
        webServices.getActivityEmp(new ModelActivity.getActivityRequest(String.valueOf(binding.etFromDate.getText()), String.valueOf(binding.etToDate.getText())), new WebServices.onGetActivityList() {
            @Override
            public void getActivityList(List<ModelActivity.Data> activityList) {
                AdapterActivityList adapterActivityList = new AdapterActivityList(ActivityListActivity.this, activityList);
                binding.rvActivity.setAdapter(adapterActivityList);
            }
        });

    }

    private void selectedDate() {
        if (!binding.etFromDate.getText().toString().isEmpty() && !binding.etToDate.getText().toString().isEmpty()) {
            fetchActivityList();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchActivityList();
    }
}
