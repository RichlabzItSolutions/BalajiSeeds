package com.balajiseeds.admin;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.balajiseeds.R;
import com.balajiseeds.admin.models.ModelEmployee;
import com.balajiseeds.databinding.ActivityAddEmployeeBinding;
import com.balajiseeds.models.ModelCities;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.WebServices;

import java.util.ArrayList;
import java.util.List;

public class AddEmployeeActivity extends AppCompatActivity {
    WebServices webServices;
    ActivityAddEmployeeBinding binding;
    String SelectedCityId = "0";
    String SelectedStateId = "0";

    String prestateid, precityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(AddEmployeeActivity.this);
        if (Constants.SelectedEMP != null) {
            binding.tvBack.setText("Edit Employee");
            ModelEmployee.EmpRequest Emp = Constants.SelectedEMP;
            prestateid = Emp.getState();
            precityid = Emp.getCity();
            binding.etEmpName.setText(Emp.getName());
            binding.etEmpCode.setText(Emp.getEmpCode());
            binding.etEmpMob.setText(Emp.getMobile());
            binding.etEmpEmail.setText(Emp.getEmail());
            binding.etEmpAddress.setText(Emp.getAddress());
            binding.etEmpSalary.setText("" + Emp.getSalary());

        } else {
            binding.tvBack.setText("Add Employee");
        }

        binding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        setStates();
        binding.tvSubmit.setOnClickListener(v -> {
            //String name, String empCode, String mobile, String email, String state, String city, String address, Integer salary
            String name = binding.etEmpName.getText().toString();
            String empCode = binding.etEmpCode.getText().toString();
            String mobile = binding.etEmpMob.getText().toString();
            String email = binding.etEmpEmail.getText().toString();
            String address = binding.etEmpAddress.getText().toString();
            String salary = binding.etEmpSalary.getText().toString();
            if (name.isEmpty()) {
                binding.etEmpName.setError("Mandatory");
                binding.etEmpName.requestFocus();
            } else if (empCode.isEmpty()) {
                binding.etEmpCode.setError("Mandatory");
                binding.etEmpCode.requestFocus();
            } else if (mobile.isEmpty()) {
                binding.etEmpMob.setError("Mandatory");
                binding.etEmpMob.requestFocus();
            } else if (email.isEmpty()) {
                binding.etEmpEmail.setError("Mandatory");
                binding.etEmpEmail.requestFocus();
            } else if (address.isEmpty()) {
                binding.etEmpAddress.setError("Mandatory");
                binding.etEmpAddress.requestFocus();
            } else if (salary.isEmpty()) {
                binding.etEmpSalary.setError("Mandatory");
                binding.etEmpSalary.requestFocus();
            } else if (SelectedStateId.isEmpty() || SelectedStateId.equals("0")) {
                Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
            } else if (SelectedCityId.isEmpty() || SelectedCityId.equals("0")) {
                Toast.makeText(this, "Select City", Toast.LENGTH_SHORT).show();
            } else {
                if (Constants.SelectedEMP != null) {
                    //update
                    ModelEmployee.EmpRequest empRequest = new ModelEmployee.EmpRequest(
                            Constants.SelectedEMP.getId(),
                            name,
                            empCode,
                            mobile,
                            email,
                            SelectedStateId,
                            SelectedCityId,
                            address,
                            Integer.valueOf(salary));
                    webServices.UpdateEmp(empRequest, new WebServices.onAddEmp() {
                        @Override
                        public void onEmpAdded() {
                            finish();
                        }
                    });

                } else {
                    //add
                    //String name, String empCode, String mobile, String email, String state, String city, String address, Integer salary
                    ModelEmployee.EmpRequest empRequest = new ModelEmployee.EmpRequest(
                            name,
                            empCode,
                            mobile,
                            email,
                            SelectedStateId,
                            SelectedCityId,
                            address,
                            Integer.valueOf(salary));
                    webServices.AddEmp(empRequest, new WebServices.onAddEmp() {
                        @Override
                        public void onEmpAdded() {
                            finish();
                        }
                    });
                }
            }
        });

    }

    private void setStates() {
        webServices.GetStates(new WebServices.onGetStates() {
            @Override
            public void getStates(List<ModelStates.State> stateList) {
                List<ModelSpinner> spinnerListcity = new ArrayList<>();
                spinnerListcity.add(new ModelSpinner("Select City", "0"));
                CustomSpinnerAdapter adaptercity = new CustomSpinnerAdapter(AddEmployeeActivity.this, spinnerListcity);
                binding.spCity.setAdapter(adaptercity);
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select State", "0"));
                for (ModelStates.State s : stateList) {
                    spinnerList.add(new ModelSpinner(s.getStateName(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddEmployeeActivity.this, spinnerList);
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
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddEmployeeActivity.this, spinnerList);
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
        Constants.SelectedEMP = null;
        webServices.dismissDialog();
    }
}