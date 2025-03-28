package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.balajiseeds.R;
import com.balajiseeds.databinding.ActivityAttendanceDetailBinding;
import com.balajiseeds.employee.models.ModelMonthlyAttendance;

import com.balajiseeds.utils.CalendarDay;
import com.balajiseeds.utils.WebServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendanceDetailActivity extends AppCompatActivity {

    ActivityAttendanceDetailBinding binding;
    WebServices webServices;
    private int currentYear;
    private int currentMonth;
    private String[] monthsArray;
    private String[] yearsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(AttendanceDetailActivity.this);
        binding.tvAttendance.setText(getIntent().getStringExtra("name") + " Attendance");
        binding.tvAttendance.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
      //  binding.spMonth
        setSpinner();
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
        binding.spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SetMonthlyAttendance();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SetMonthlyAttendance();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SetMonthlyAttendance() {
        String month1, year1;
        month1 = String.format("%02d", binding.spMonth.getSelectedItemPosition());
        year1 = binding.spYear.getSelectedItem().toString();
        ModelMonthlyAttendance.MonthlyAttendanceRequest request = new ModelMonthlyAttendance.MonthlyAttendanceRequest("" + (Integer.valueOf(month1) + 1), year1, getIntent().getStringExtra("id"), "");
        webServices.getMonthlyAttendanceEmp(request, new WebServices.onGetMonthlyAttEmp() {
            @Override
            public void Attendance(ModelMonthlyAttendance.MonthlyAttendanceResponse response) {
                List<Integer> holiday = new ArrayList<>();
                List<Integer> present = new ArrayList<>();
                List<Integer> absent = new ArrayList<>();
                List<Integer> leaves =new ArrayList<>();
                for (ModelMonthlyAttendance.Attendance att : response.getData()) {
                    String day = "";
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Date date = sdf.parse(att.getDate());
                        day = new SimpleDateFormat("dd", Locale.getDefault()).format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (!day.isEmpty()) {
                        Log.e("att===", "=====" + att.getStatus());
                        switch (att.getStatus()) {
                            case "1":
                                present.add(Integer.valueOf(day));
                                break;
                            case "2":
                                absent.add(Integer.valueOf(day));
                                break;
                            case "3":
                                holiday.add(Integer.valueOf(day));
                            case "4":
                                leaves.add(Integer.valueOf(day));
                                break;
                        }
                    }


                }
                int year = Integer.valueOf(year1);
                int month = Integer.valueOf(month1);
                Calendar c = Calendar.getInstance();
                c.set(year, month, 1);
                Calendar c2 = Calendar.getInstance();
                c2.set(year, (month - 1), 1);
                int numberOfDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                int numberOfDaysprev = c2.getActualMaximum(Calendar.DAY_OF_MONTH);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                numberOfDaysprev = (numberOfDaysprev - dayOfWeek) + 3;
                List<CalendarDay> calendarDayList = new ArrayList<>();
                for (int j = 0; j < dayOfWeek - 2; j++) {
                    calendarDayList.add(new CalendarDay(numberOfDaysprev + j, (month - 1), null));
                }
                for (int i = 1; i <= numberOfDays; i++) {
                    calendarDayList.add(new CalendarDay(i, month, null));
                }

                for (int day : holiday) {
                    day = day + (dayOfWeek - 3);

                    if (day > 0){
                        calendarDayList.get(day).setStatus(CalendarDay.Status.HOLIDAY);
                    }
                    else{


                    }
                }
                for (int day : present) {
                    day = day + (dayOfWeek - 3);
                    if (day > 0) {

                        calendarDayList.get(day).setStatus(CalendarDay.Status.PRESENT);
                    }
                    else{

                    }

                }
                for (int day : absent) {
                    day = day + (dayOfWeek - 3);

                    if (day > 0) {
                        calendarDayList.get(day).setStatus(CalendarDay.Status.ABSENT);

                    } else {
                        // Handle case where index is -1 or 0
                        // For example, do nothing or log an error
                    }
                }
                for (int day : leaves) {
                    day = day + (dayOfWeek - 3);

                    if (day > 0){
                        calendarDayList.get(day).setStatus(CalendarDay.Status.LEAVES);

                    }
                    else{

                    }
                }

                binding.customCalendarView.setCalendar(calendarDayList);

                binding.tvHolidays.setText("" + holiday.size());
                binding.tvAbsent.setText("" + absent.size());
                binding.tvPresent.setText("" + present.size());
                binding.tvWorkingDays.setText("" + (numberOfDays - holiday.size()));
                binding.tvLeaves.setText(""+leaves.size());
                binding.tvSalary.setText(getIntent().getStringExtra("salary"));
                Double Salary = Double.valueOf(getIntent().getStringExtra("salary"));
                Double perday = Salary / numberOfDays;
                Double cutoff = perday * absent.size();
                Double payable = Salary - cutoff;
                binding.tvPayable.setText(String.format("%.2f", payable));
            }
        });
    }

}
