package com.balajiseeds.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.balajiseeds.R;
import com.balajiseeds.admin.ExpensesActivity;
import com.balajiseeds.admin.adapters.AdapterExpenses;
import com.balajiseeds.databinding.ActivityExpensesHeadsBinding;
import com.balajiseeds.databinding.ActivityExpensesHeadsEmployeeBinding;
import com.balajiseeds.databinding.AdminActivityExpensesBinding;
import com.balajiseeds.employee.models.ModelExpenses;
import com.balajiseeds.models.ModelCities;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.WebServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpensesHeadsActivity extends AppCompatActivity {
    ActivityExpensesHeadsEmployeeBinding binding;

    WebServices webServices;
    String prestateid, precityid, SelectedStateId = "", SelectedCityId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpensesHeadsEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(this);
        setStates();
        binding.txtExpenses.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchExpenses();
            }
        });
        binding.etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(ExpensesHeadsActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    DatePickerDialog dpd = new DatePickerDialog(ExpensesHeadsActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    Toast.makeText(ExpensesHeadsActivity.this, "Select from date first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectedDate() {
        if (!binding.etFromDate.getText().toString().isEmpty() && !binding.etToDate.getText().toString().isEmpty()) {
            fetchExpenses();
        }
    }

    public void fetchExpenses() {
        String fromdate, todate, searchstr;
        fromdate = binding.etFromDate.getText().toString();
        todate = binding.etToDate.getText().toString();
        searchstr = String.valueOf(binding.etSearch.getText());
        ModelExpenses.getExpensesRequest request = new ModelExpenses.getExpensesRequest(fromdate, todate, SelectedStateId, SelectedCityId, searchstr);
        webServices.getExpenses(request, new WebServices.onGetExpenses() {
            @Override
            public void getExpenses(List<ModelExpenses.Expenses> expenseList) {
                binding.rvExpenses.setLayoutManager(new LinearLayoutManager(ExpensesHeadsActivity.this));
                binding.rvExpenses.setAdapter(new AdapterExpenses(ExpensesHeadsActivity.this, expenseList));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchExpenses();
    }

    private void setStates() {
        webServices.GetStates(new WebServices.onGetStates() {
            @Override
            public void getStates(List<ModelStates.State> stateList) {
                List<ModelSpinner> spinnerListcity = new ArrayList<>();
                spinnerListcity.add(new ModelSpinner("Select City", "0"));
                CustomSpinnerAdapter adaptercity = new CustomSpinnerAdapter(ExpensesHeadsActivity.this, spinnerListcity);
                binding.spCity.setAdapter(adaptercity);
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select State", "0"));
                for (ModelStates.State s : stateList) {
                    spinnerList.add(new ModelSpinner(s.getStateName(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(ExpensesHeadsActivity.this, spinnerList);
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
                            fetchExpenses();
                        } else {
                            setCity("0");
                            SelectedStateId = "";
                            fetchExpenses();
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
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(ExpensesHeadsActivity.this, spinnerList);
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
                            fetchExpenses();
                        } else {
                            SelectedCityId = "";
                            fetchExpenses();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

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