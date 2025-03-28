package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SpinnerAdapter;

import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.AdapterDailyAttendanceList;
import com.balajiseeds.admin.adapters.AdapterMonthlyAttendanceList;
import com.balajiseeds.admin.models.ModelAttendance;
import com.balajiseeds.databinding.ActivityAttendanceListBinding;
import com.balajiseeds.employee.models.ModelMonthlyAttendance;
import com.balajiseeds.models.ModelCities;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.WebServices;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AttendanceListActivity extends AppCompatActivity {
    ActivityAttendanceListBinding binding;
    WebServices webServices;
    String prestateid, precityid, SelectedStateId = "", SelectedCityId = "";
    private int currentYear;
    private int currentMonth;
    private String[] monthsArray;
    private String[] yearsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(AttendanceListActivity.this);
        binding.rvAttendance.setLayoutManager(new LinearLayoutManager(AttendanceListActivity.this));
        binding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.ivSearch.setOnClickListener(v -> {
            SetMonthlyAttendance();
        });
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        binding.tvDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
        setStates();

        setSpinner();
//        SetMonthlyAttendance();
        SetDailyAttendance();
        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(AttendanceListActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.tvDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                        SetDailyAttendance();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        binding.tlAttendance.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getText().toString()) {
                    case "Daily Attendance":
                        SetDailyAttendance();
                        break;
                    case "Monthly Attendance":
                        SetMonthlyAttendance();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void SetMonthlyAttendance() {
        binding.llMonthlyAtt.setVisibility(View.VISIBLE);
        binding.llDailyAtt.setVisibility(View.GONE);
        String month, year;
        month = String.format("%02d", binding.spMonth.getSelectedItemPosition() + 1);
        year = binding.spYear.getSelectedItem().toString();
        ModelMonthlyAttendance.MonthlyAttendanceRequest request = new ModelMonthlyAttendance.MonthlyAttendanceRequest(month, year, binding.etSearchEmp.getText().toString());
        webServices.adminFetchMonthlyAtt(request, new WebServices.onGetMonthlyAttendanceAdmin() {
            @Override
            public void getAttendance(List<ModelMonthlyAttendance.UserAttendance> attendanceList) {

                binding.rvAttendance.setAdapter(new AdapterMonthlyAttendanceList(AttendanceListActivity.this, attendanceList));
            }
        });

    }

    private void SetDailyAttendance() {
        binding.llMonthlyAtt.setVisibility(View.GONE);
        binding.llDailyAtt.setVisibility(View.VISIBLE);
        ModelAttendance.getDailyAttendanceRequest request = new ModelAttendance.getDailyAttendanceRequest(binding.tvDate.getText().toString(), SelectedStateId, SelectedCityId);
        webServices.getDailyAttendance(request, new WebServices.oGetDailyAttendance() {
            @Override
            public void getDailyAttendance(List<ModelAttendance.DailyAttendance> dailyAttendanceList) {
                binding.rvAttendance.setAdapter(new AdapterDailyAttendanceList(AttendanceListActivity.this, dailyAttendanceList));
            }
        });

    }

    private void setStates() {
        webServices.GetStates(new WebServices.onGetStates() {
            @Override
            public void getStates(List<ModelStates.State> stateList) {
                List<ModelSpinner> spinnerListcity = new ArrayList<>();
                spinnerListcity.add(new ModelSpinner("Select City", "0"));
                CustomSpinnerAdapter adaptercity = new CustomSpinnerAdapter(AttendanceListActivity.this, spinnerListcity);
                binding.spCity.setAdapter(adaptercity);
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select State", "0"));
                for (ModelStates.State s : stateList) {
                    spinnerList.add(new ModelSpinner(s.getStateName(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AttendanceListActivity.this, spinnerList);
                binding.spState.setAdapter(adapter);
                for (int i = 0; i < spinnerList.size(); i++) {
                    if (spinnerList.get(i).getId().equals(prestateid)) {
                        binding.spState.setSelection(i);
                        break;
                    }
                }
                binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ModelSpinner selectedModel = (ModelSpinner) parent.getItemAtPosition(position);
                        if (!selectedModel.getId().equals("0")) {
                            SelectedStateId = selectedModel.getId();
                            setCity(SelectedStateId);
                            SetDailyAttendance();
                        } else {
                            setCity("0");
                            SelectedStateId = "";
                            SetDailyAttendance();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });

    }

    private void setCity(String selectedStateId) {
        webServices.GetCities(new ModelCities.CitiesRequest(selectedStateId), new WebServices.onGetCities() {
            @Override
            public void getCities(List<ModelCities.Cities> citiesList) {
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select City", "0"));
                for (ModelCities.Cities s : citiesList) {
                    spinnerList.add(new ModelSpinner(s.getCityName(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AttendanceListActivity.this, spinnerList);
                binding.spCity.setAdapter(adapter);
                for (int i = 0; i < spinnerList.size(); i++) {
                    if (spinnerList.get(i).getId().equals(precityid)) {
                        binding.spCity.setSelection(i);
                        break;
                    }
                }
                binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ModelSpinner selectedModel = (ModelSpinner) parent.getItemAtPosition(position);
                        if (!selectedModel.getId().equals("0")) {
                            SelectedCityId = selectedModel.getId();
                            SetDailyAttendance();
                        } else {
                            SelectedCityId = "";
                            SetDailyAttendance();
                        }

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

}