package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.AdapterLeavesList;
import com.balajiseeds.databinding.ActivityLeavesListBinding;
import com.balajiseeds.employee.models.ModelLeaves;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class LeavesListActivity extends AppCompatActivity {
    ActivityLeavesListBinding binding;
    WebServices webServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeavesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(LeavesListActivity.this);
        setSpinner();
        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.etSearchEmp.getText().toString().isEmpty() && binding.etSearchEmp.getText().toString().length() >= 3) {
                    fetchLeaves();
                } else {
                    Toast.makeText(LeavesListActivity.this, "Search should be at least 3 characters", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchLeaves();
    }

    private void fetchLeaves() {
        ModelLeaves.adminFetchLeaveRequest request = new ModelLeaves.adminFetchLeaveRequest(String.valueOf((binding.spMonth.getSelectedItemPosition() + 1)), binding.spYear.getSelectedItem().toString(), String.valueOf(binding.etSearchEmp.getText()));
        webServices.adminFetchLeaveList(request, new WebServices.onFetchLeavesEmp() {
            @Override
            public void fetchedLeaves(List<ModelLeaves.Leave> leaveList) {
                binding.rvLeaves.setLayoutManager(new LinearLayoutManager(LeavesListActivity.this));
                webServices.getLeavesTypeList(new WebServices.onFetchLeaveType() {
                    @Override
                    public void fetchedLeaveType(List<ModelLeaves.LeaveType> leaveTypeList) {
                        Constants.leaveTypeList = leaveTypeList;
                        binding.rvLeaves.setAdapter(new AdapterLeavesList(LeavesListActivity.this, leaveList));
                    }
                });
                binding.spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        fetchLeaves();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                binding.spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        fetchLeaves();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    public void setSpinner() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        // Example: Get an array of years from 2020 to 2030
        int startYear = 2020;
        int endYear = 2030;
        int totalYears = endYear - startYear + 1;
        String[] yearsArray = new String[totalYears]; // Initialize the array properly
        for (int i = 0; i < totalYears; i++) {
            yearsArray[i] = String.valueOf(startYear + i);
        }

        // Example: Get an array of months from January to December
        String[] monthsArray = new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        // Create adapters for month and year spinners
        ArrayAdapter<String> adapter_month = new ArrayAdapter<>(this, R.layout.simple_spinner_item, monthsArray);
        adapter_month.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter_year = new ArrayAdapter<>(this, R.layout.simple_spinner_item, yearsArray);
        adapter_year.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        // Set adapters to spinners
        binding.spMonth.setAdapter(adapter_month);
        binding.spYear.setAdapter(adapter_year);

        // Find index of current year and month in the arrays
        int currentYearIndex = Arrays.asList(yearsArray).indexOf(String.valueOf(currentYear));
        int currentMonthIndex = currentMonth;

        // Set the selection in the spinners
        binding.spMonth.setSelection(currentMonthIndex);
        binding.spYear.setSelection(currentYearIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }
}