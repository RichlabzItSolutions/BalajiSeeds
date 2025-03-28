package com.balajiseeds.employee;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.balajiseeds.R;
import com.balajiseeds.databinding.EmpActivityMonthlyAttendanceBinding;
import com.balajiseeds.employee.adapters.AdapterMonthlyAttendance;
import com.balajiseeds.employee.models.ModelMonthlyAttendance;
import com.balajiseeds.utils.WebServices;

import java.util.Arrays;
import java.util.Calendar;

public class MonthlyAttendanceActivity extends BaseActivity {

    EmpActivityMonthlyAttendanceBinding binding;
    WebServices webServices;
    private int currentYear;
    private int currentMonth;
    private String[] monthsArray;
    private String[] yearsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmpActivityMonthlyAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webServices = new WebServices(MonthlyAttendanceActivity.this);
        binding.tvMonthlyAttendance.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setSpinner();


    }

    private void setData() {
        String month, year;
        month = String.format("%02d", binding.spMonth.getSelectedItemPosition() + 1);
        year = binding.spYear.getSelectedItem().toString();
        webServices.getMonthlyAttendanceEmp(new ModelMonthlyAttendance.MonthlyAttendanceRequest(month, year), new WebServices.onGetMonthlyAttEmp() {
            @Override
            public void Attendance(ModelMonthlyAttendance.MonthlyAttendanceResponse response) {
                binding.tvWorkingDays.setText("" + response.getTotalWorkingDays());
                binding.tvAbsent.setText("" + response.getTotalAbsent());
                binding.tvHolidays.setText("" + response.getTotalHolidays());
                binding.tvPresent.setText("" + response.getTotalPresent());
                binding.tvLeaves.setText("" + response.getTotalLeaves());
                binding.rvAttendance.setLayoutManager(new LinearLayoutManager(MonthlyAttendanceActivity.this));
                binding.rvAttendance.setAdapter(new AdapterMonthlyAttendance(MonthlyAttendanceActivity.this, response.getData()));
            }
        });
    }

    public void setSpinner() {
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);

        // Example: Get an array of years from 2020 to 2030
        int startYear = 2020;
        int endYear = 2030;
        int totalYears = endYear - startYear + 1;
        yearsArray = new String[totalYears]; // Initialize the array properly
        for (int i = 0; i < totalYears; i++) {
            yearsArray[i] = String.valueOf(startYear + i);
        }

        // Example: Get an array of months from January to December
        monthsArray = new String[]{
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

}