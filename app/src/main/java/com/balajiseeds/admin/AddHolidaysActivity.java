package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.AdapterHolidaysList;
import com.balajiseeds.admin.models.AddHoilidays.AddHolidaysJson;
import com.balajiseeds.admin.models.AddHoilidays.AddHolidaysResponse;
import com.balajiseeds.admin.models.EditHolidays.EditHolidaysJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysJson;
import com.balajiseeds.admin.models.ModelEmployee;
import com.balajiseeds.databinding.ActivityAddHolidaysBinding;
import com.balajiseeds.databinding.ActivityHolidaysBinding;
import com.balajiseeds.employee.ExpensesActivity;
import com.balajiseeds.employee.models.ModelExpenses;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.utils.API;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.CustomWaitDialog;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.RetrofitClient;
import com.balajiseeds.utils.SharedPref;
import com.balajiseeds.utils.WebServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHolidaysActivity extends AppCompatActivity {

    ActivityAddHolidaysBinding binding;

    API api;
    String token, id, holidayTitle, fromDate, toDate, description, states, status , type;

    WebServices webServices;

    String prestateid,  SelectedStateId = "" ;

    String SelectedEHId = "0";



    CustomWaitDialog cwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddHolidaysBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        webServices = new WebServices(AddHolidaysActivity.this);

        api = RetrofitClient.getRetrofitInstance().create(API.class);



        //  Log.d("njo",  token);

        SharedPref sharedPref = new SharedPref(AddHolidaysActivity.this);
        if (sharedPref.getString(SharedPref.token) != null && !sharedPref.getString(SharedPref.token).isEmpty()) {
            Log.d("token", "" + sharedPref.getString(SharedPref.token));

            token = sharedPref.getString(SharedPref.token);


        } else {


        }



        if (Constants.SelectedHOLIDAY != null) {
            binding.textView.setText("Edit Holidays");
            GetHolidaysDetails Emp = Constants.SelectedHOLIDAY;
            binding.etHolidayTitle.setText(Emp.getHolidayTitle());
            binding.etFromDate.setText(Emp.getFromDate());
            binding.etToDate.setText(Emp.getToDate());
            binding.etDescription.setText(Emp.getDescription());
            prestateid = Emp.getStateId();

            id = Emp.getId();
           // binding.spPackSize.setSelection(emp);
            binding.tvSubmit.setText("Update");

        } else {
            binding.textView.setText("Add Holidays");
        }



      /*  Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity

        try {

            type = intent.getStringExtra("type");
            id = intent.getStringExtra("id");
            holidayTitle = intent.getStringExtra("holidayTitle");
            fromDate = intent.getStringExtra("fromDate");
            toDate = intent.getStringExtra("toDate");
            description = intent.getStringExtra("description");
            states = intent.getStringExtra("states");
            status = intent.getStringExtra("status");


            if (!(type.isEmpty())){

                binding.textView.setText("Edit Holidays");
                binding.etHolidayTitle.setText(holidayTitle);
                binding.etFromDate.setText(fromDate);
                binding.etToDate.setText(toDate);
                binding.etDescription.setText(description);
                binding.etHolidayTitle.setText(holidayTitle);
                binding.tvSubmit.setText("Update");

            }


        } catch (Exception e) {
            // This will catch any exception, because they are all descended from Exception
            System.out.println("Error " + e.getMessage());

        }
*/

        binding.textView.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });


        setStates();

        binding.etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(AddHolidaysActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.etFromDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                        selectedDate();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
        binding.etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.etFromDate.getText().toString().isEmpty()) {
                    DatePickerDialog dpd = new DatePickerDialog(AddHolidaysActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    Toast.makeText(AddHolidaysActivity.this, "Select from date first", Toast.LENGTH_SHORT).show();
                }
            }
        });











      /*  if (!(type.isEmpty())){*/



            binding.tvSubmit.setOnClickListener(v -> {
                //amount,date,desc
                holidayTitle = binding.etHolidayTitle.getText().toString();
                fromDate = binding.etFromDate.getText().toString();
                toDate = binding.etToDate.getText().toString();

                description = binding.etDescription.getText().toString();
                //String expenseid = SelectedEHId;
                //check for empty of all above strings one by one
                if (holidayTitle.isEmpty()) {
                    Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (fromDate.isEmpty()) {
                    Toast.makeText(this, "From Date cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (toDate.isEmpty()) {
                    Toast.makeText(this, "To Date cannot be empty", Toast.LENGTH_SHORT).show();
                }else if (description.isEmpty()) {
                    Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (SelectedStateId.isEmpty() || SelectedStateId.equals("0")) {
                    Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
                } else {


                    if (Constants.SelectedHOLIDAY != null) {

                       // api.updateHolidays(token , new EditHolidaysJson(id, holidayTitle, fromDate, toDate, description, Arrays.asList(SelectedStateId), "1")).enqueue(new Callback<AddHolidaysResponse>() {

                      /*  api.updateHolidays(token , new EditHolidaysJson("55", "holidayTitle", "05-08-2024", "05-08-2024", "Edid Mubarak", Collections.singletonList("[3,2]"), "1")).enqueue(new Callback<AddHolidaysResponse>() {
                            @Override
                            public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                                // cwd.dismiss();
                                if(response.body()!=null){
                                    AddHolidaysResponse updateHolidaysResponse= response.body();

                                    Log.d("kol", updateHolidaysResponse.getMessage());

                                    showToast(updateHolidaysResponse.getMessage());

                                    //  Status =  GetHolidaysResponse.getStatus();
                                    //holodaysList = new ArrayList<>();
                                    //   holodaysList = getHolidaysResponse.getData();



                                }
                                else {
                                    // showToast("Something went wrong");
                                }
                            }

                            @Override
                            public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                                //cwd.dismiss();
                                // showToast("something went wrong");
                                t.printStackTrace();
                            }
                        });*/
                        //update
                      /*  EditHolidaysJson empRequest = new EditHolidaysJson(
                                id,
                                holidayTitle,
                                fromDate,
                                toDate,
                                description,
                                Arrays.asList(SelectedStateId),
                                "1");*/


                        EditHolidaysJson request = new EditHolidaysJson(id, holidayTitle, fromDate, toDate, description, SelectedStateId,"1");

                        webServices.UpdateHoliday(request, new WebServices.onResponse() {
                            @Override
                            public void response() {

                                finish();
                                // getOnBackPressedDispatcher().onBackPressed();
                                //dialog.dismiss();
                                // setExpensesList();
                            }
                        });

                      /*  api.updateHolidays(token , new EditHolidaysJson(id, holidayTitle, fromDate, toDate, description, Arrays.asList(SelectedStateId), "1")).enqueue(new Callback<AddHolidaysResponse>() {
                            @Override
                            public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {

                                AddHolidaysResponse updateHolidaysResponse= response.body();
                                // cwd.dismiss();
                                if(response.body()!=null){


                                    Log.d("kol", updateHolidaysResponse.getMessage());

                                    //  Status =  GetHolidaysResponse.getStatus();
                                    //holodaysList = new ArrayList<>();
                                    //   holodaysList = getHolidaysResponse.getData();

                                    showToast( response.message());

                                }
                                else {
                                     showToast("Something went wrong");
                                }
                            }

                            @Override
                            public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                                //cwd.dismiss();
                                // showToast("something went wrong");
                                t.printStackTrace();
                            }
                        });*/
                    }
                    else{




                        AddHolidaysJson request = new AddHolidaysJson(holidayTitle, fromDate, toDate, description,  Arrays.asList(SelectedStateId),"1");
                        webServices.addHolidays(request,  new WebServices.onResponse() {
                            @Override
                            public void response() {

                                finish();
                               // getOnBackPressedDispatcher().onBackPressed();
                                //dialog.dismiss();
                                // setExpensesList();
                            }
                        });

                    }


                  /*  api.updateHolidays(token , new EditHolidaysJson(id, holidayTitle, fromDate, toDate, description, Arrays.asList(SelectedStateId), status)).enqueue(new Callback<AddHolidaysResponse>() {
                        @Override
                        public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                            // cwd.dismiss();
                            if(response.body()!=null){
                                AddHolidaysResponse updateHolidaysResponse= response.body();

                                Log.d("kol", updateHolidaysResponse.getMessage());

                                //  Status =  GetHolidaysResponse.getStatus();
                                //holodaysList = new ArrayList<>();
                                //   holodaysList = getHolidaysResponse.getData();



                            }
                            else {
                                // showToast("Something went wrong");
                            }
                        }

                        @Override
                        public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                            //cwd.dismiss();
                            // showToast("something went wrong");
                            t.printStackTrace();
                        }
                    });
*/
                }

            });

       /* }

        else{
*/


/*
            binding.tvSubmit.setOnClickListener(v -> {
                //amount,date,desc
                holidayTitle = binding.etHolidayTitle.getText().toString();
                fromDate = binding.etFromDate.getText().toString();
                toDate = binding.etToDate.getText().toString();

                description = binding.etDescription.getText().toString();
                //String expenseid = SelectedEHId;
                //check for empty of all above strings one by one
                if (holidayTitle.isEmpty()) {
                    Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (fromDate.isEmpty()) {
                    Toast.makeText(this, "From Date cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (toDate.isEmpty()) {
                    Toast.makeText(this, "To Date cannot be empty", Toast.LENGTH_SHORT).show();
                }else if (description.isEmpty()) {
                    Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (SelectedStateId.isEmpty() || SelectedStateId.equals("0")) {
                    Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
                } else {


                    AddHolidaysJson request = new AddHolidaysJson(holidayTitle, fromDate, toDate, description,  Arrays.asList(SelectedStateId),"1");
                    webServices.addHolidays(request,  new WebServices.onResponse() {
                        @Override
                        public void response() {

                            getOnBackPressedDispatcher().onBackPressed();
                            //dialog.dismiss();
                            // setExpensesList();
                        }
                    });



                }



            });
*/


        //}








        //Arrays.asList(SelectedStateId);






    }


    private void selectedDate() {
        if (!binding.etFromDate.getText().toString().isEmpty() && !binding.etToDate.getText().toString().isEmpty()) {
           // setExpensesList();
        }
    }

    private void setStates() {
        webServices.GetStates(new WebServices.onGetStates() {
            @Override
            public void getStates(List<ModelStates.State> stateList) {
            /*    List<ModelSpinner> spinnerListcity = new ArrayList<>();
                spinnerListcity.add(new ModelSpinner("Select City", "0"));
                CustomSpinnerAdapter adaptercity = new CustomSpinnerAdapter(ExpensesActivity.this, spinnerListcity);*/
                // binding.spCity.setAdapter(adaptercity);
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select State", "0"));
                for (ModelStates.State s : stateList) {
                    spinnerList.add(new ModelSpinner(s.getStateName(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddHolidaysActivity.this, spinnerList);
                binding.spPackSize.setAdapter(adapter);
                for (int i = 0; i < spinnerList.size(); i++) {
                    if (spinnerList.get(i).getId().equals(prestateid)) {
                        binding.spPackSize.setSelection(i);
                        break;
                    }
                }
                binding.spPackSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ModelSpinner selectedModel = (ModelSpinner) parent.getItemAtPosition(position);
                        if (!selectedModel.getId().equals("0")) {
                            SelectedStateId = selectedModel.getId();
                            //  setCity(SelectedStateId);
                           // fetchExpenses();
                        } else {
                            // setCity("0");
                            SelectedStateId = "";

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });

    }

    public void showToast(String msg) {
        Toast.makeText(AddHolidaysActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
    }



   /* public void fetchExpenses() {
        AddHolidaysJson request = new AddHolidaysJson(SelectedStateId);
        webServices.addHolidays(request, new WebServices.onGetHolidays() {
            @Override
            public void addHolidays(List<GetHolidaysDetails> holodaysList) {
                binding.rvHolidaysList.setLayoutManager(new LinearLayoutManager(AddHolidaysActivity.this));
                binding.rvHolidaysList.setAdapter(new AdapterHolidaysList(AddHolidaysActivity.this, holodaysList));
            }
        });
    }*/

/*
    public void EditHolidays(){

        api.updateHolidays(token , new EditHolidaysJson(id, holidayTitle, fromDate, toDate, description, Arrays.asList(SelectedStateId), status)).enqueue(new Callback<AddHolidaysResponse>() {
            @Override
            public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                // cwd.dismiss();
                if(response.body()!=null){
                    AddHolidaysResponse updateHolidaysResponse= response.body();

                    Log.d("kol", updateHolidaysResponse.getMessage());

                  //  Status =  GetHolidaysResponse.getStatus();
                    //holodaysList = new ArrayList<>();
                 //   holodaysList = getHolidaysResponse.getData();



                }
                else {
                    // showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                //cwd.dismiss();
                // showToast("something went wrong");
                t.printStackTrace();
            }
        });

    }
*/
}