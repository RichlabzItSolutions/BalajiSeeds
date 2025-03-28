package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.AdapterEmployeeList;
import com.balajiseeds.admin.models.ModelEmployee;
import com.balajiseeds.databinding.ActivityEmployeeListBinding;
import com.balajiseeds.models.ModelCities;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.WebServices;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {
    ActivityEmployeeListBinding binding;
    WebServices webServices;
    String SelectedCityId = "0";
    String SelectedStateId = "0";

    String prestateid, precityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(EmployeeListActivity.this);
        binding.textView.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.ivSearch.setOnClickListener(v -> {
            setData();
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        setStates();
        binding.tvAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeListActivity.this, AddEmployeeActivity.class));
            }
        });
    }

    public void setData() {
        binding.rvEmp.setLayoutManager(new LinearLayoutManager(EmployeeListActivity.this));
        String searchstr=String.valueOf(binding.etSearchEmp.getText());
        String state="",city="";
        if(!SelectedStateId.equals("0")){
            state=SelectedStateId;
        }
        if(!SelectedCityId.equals("0")){
            city=SelectedCityId;
        }
        ModelEmployee.EmpListRequest request=new ModelEmployee.EmpListRequest(searchstr,state,city);
        webServices.GetEmpList(request,new WebServices.onGetEmpList() {
            @Override
            public void getEmpList(List<ModelEmployee.EmpRequest> empList) {
                binding.rvEmp.setAdapter(new AdapterEmployeeList(EmployeeListActivity.this, empList));

            }
        });
    }

    private void setStates() {

        webServices.GetStates(new WebServices.onGetStates() {
            @Override
            public void getStates(List<ModelStates.State> stateList) {
                List<ModelSpinner> spinnerListcity = new ArrayList<>();
                spinnerListcity.add(new ModelSpinner("Select City", "0"));
                CustomSpinnerAdapter adaptercity = new CustomSpinnerAdapter(EmployeeListActivity.this, spinnerListcity);
                binding.spCity.setAdapter(adaptercity);
                Constants.stateList = stateList;
                getAllCities();
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select State", "0"));
                for (ModelStates.State s : stateList) {
                    spinnerList.add(new ModelSpinner(s.getStateName(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(EmployeeListActivity.this, spinnerList);
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
                        SelectedStateId = selectedModel.getId();
                        setCity(SelectedStateId);
                        setData();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });

    }

    private void getAllCities() {
        webServices.getAllCities(new WebServices.onGetCities() {
            @Override
            public void getCities(List<ModelCities.Cities> citiesList) {
                Constants.citiesList = citiesList;
                setData();
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
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(EmployeeListActivity.this, spinnerList);
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
                        SelectedCityId = selectedModel.getId();
                        setData();
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