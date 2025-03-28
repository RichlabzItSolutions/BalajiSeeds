package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.MediaController;

import com.balajiseeds.LoginActivity;
import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.AdapterExpenses;
import com.balajiseeds.admin.adapters.AdapterHolidaysList;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysResponse;
import com.balajiseeds.databinding.ActivityHolidaysBinding;
import com.balajiseeds.employee.models.ModelExpenses;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.utils.API;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.RetrofitClient;
import com.balajiseeds.utils.SharedPref;
import com.balajiseeds.utils.WebServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HolidaysActivity extends AppCompatActivity {
    ActivityHolidaysBinding binding;
    API api;
    String token, Status ;
    WebServices webServices;
    String stateId  = "";
    //  List<GetHolidaysDetails> holodaysList;
    String prestateid,  SelectedStateId = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHolidaysBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webServices = new WebServices(this);


        binding.txtAdHolidaysList.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        binding.tvAddHolidays.setOnClickListener(v -> {
            startActivity(new Intent(HolidaysActivity.this, AddHolidaysActivity.class));
        });


        SharedPref sharedPref = new SharedPref(HolidaysActivity.this);
        if (sharedPref.getString(SharedPref.token) != null && !sharedPref.getString(SharedPref.token).isEmpty()) {
            Log.d("token", "" + sharedPref.getString(SharedPref.token));

            token = sharedPref.getString(SharedPref.token);

            setStates();
            fetchExpenses();

           // webServices = new WebServices(HolidaysActivity.this);

          //  api = RetrofitClient.getRetrofitInstance().create(API.class);

          //  getHolidaysList();



        } else {


        }

      // Log.d("njo",  token);



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
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(HolidaysActivity.this, spinnerList);
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
                            fetchExpenses();
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


    @Override
    protected void onResume() {
        super.onResume();
        fetchExpenses();
    }


    public void fetchExpenses() {
        GetHolidaysJson request = new GetHolidaysJson(SelectedStateId);
        webServices.getHolidays(request, new WebServices.onGetHolidays() {
            @Override
            public void getHolidays(List<GetHolidaysDetails> holodaysList) {
                binding.rvHolidaysList.setLayoutManager(new LinearLayoutManager(HolidaysActivity.this));
                binding.rvHolidaysList.setAdapter(new AdapterHolidaysList(HolidaysActivity.this, holodaysList));
            }
        });
    }

/*
    public void getHolidaysList(){

        api.getHolidays(token, new GetHolidaysJson(stateId)).enqueue(new Callback<GetHolidaysResponse>() {
            @Override
            public void onResponse(Call<GetHolidaysResponse> call, Response<GetHolidaysResponse> response) {
                // cwd.dismiss();
                if(response.body()!=null){
                    GetHolidaysResponse getHolidaysResponse= response.body();

                    Log.d("kol", getHolidaysResponse.getMessage());

                  //  Status =  GetHolidaysResponse.getStatus();
                    //holodaysList = new ArrayList<>();
                 //   holodaysList = getHolidaysResponse.getData();



                }
                else {
                    // showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<GetHolidaysResponse> call, Throwable t) {
                //cwd.dismiss();
                // showToast("something went wrong");
                t.printStackTrace();
            }
        });

    }
*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }





}