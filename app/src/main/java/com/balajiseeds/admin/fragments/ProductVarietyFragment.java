package com.balajiseeds.admin.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.balajiseeds.R;
import com.balajiseeds.admin.HolidaysActivity;
import com.balajiseeds.admin.adapters.AdapterHolidaysList;
import com.balajiseeds.admin.adapters.AdapterProductVarietyList;
import com.balajiseeds.admin.models.AddProductVariety.AddProductVarietyJson;
import com.balajiseeds.admin.models.AddProductVariety.GetProductVarietyDetails;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysJson;
import com.balajiseeds.databinding.AdminDialogAddProductVarietyBinding;
import com.balajiseeds.databinding.EmpDialogAddAdditionalWorkBinding;
import com.balajiseeds.databinding.FragmentProductVarietyBinding;
import com.balajiseeds.employee.PermissionsActivity;
import com.balajiseeds.employee.models.ModelPermissionAWD;
import com.balajiseeds.utils.SharedPref;
import com.balajiseeds.utils.WebServices;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ProductVarietyFragment extends Fragment {

    FragmentProductVarietyBinding binding;

    AdminDialogAddProductVarietyBinding binding1;

    WebServices webServices;

    String token, Status ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductVarietyBinding.inflate(inflater, container, false);

        webServices = new WebServices(getContext());

        binding.textView.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        getProductVariety();

        SharedPref sharedPref = new SharedPref(getActivity());
        if (sharedPref.getString(SharedPref.token) != null && !sharedPref.getString(SharedPref.token).isEmpty()) {
            Log.d("token", "" + sharedPref.getString(SharedPref.token));

            token = sharedPref.getString(SharedPref.token);



            // webServices = new WebServices(HolidaysActivity.this);

            //  api = RetrofitClient.getRetrofitInstance().create(API.class);

            //  getHolidaysList();



        } else {


        }


        binding.tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addProductVarietyDialog();

            }
        });

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        getProductVariety();
    }


    public void getProductVariety() {
       // GetHolidaysJson request = new GetHolidaysJson(SelectedStateId);
        webServices.getProductVariety( new WebServices.onGetProductVariety() {
            @Override
            public void getProductVariety(List<GetProductVarietyDetails> ProductVarietyList) {
                binding.rvProductVariety.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.rvProductVariety.setAdapter(new AdapterProductVarietyList(getActivity(), ProductVarietyList));
            }
        });
    }


    private void addProductVarietyDialog() {
        Dialog dialog = new Dialog(getActivity());
        binding1 = AdminDialogAddProductVarietyBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding1.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        binding1.txtAddProductVariety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        binding1.tvSubmit.setOnClickListener(v -> {
            String VarietyName;
            VarietyName = binding1.tvVarietyName.getText().toString();

            if (VarietyName.isEmpty()) {
                Toast.makeText(getActivity(), "Please Enter Variety Name", Toast.LENGTH_SHORT).show();
            } else {
                AddProductVarietyJson addProductVarietyJson = new AddProductVarietyJson(VarietyName, "1");
                webServices.addProductVariety(addProductVarietyJson, new WebServices.onResponse() {
                    @Override
                    public void response() {
                        dialog.dismiss();
                        getProductVariety();
                    }
                });

            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }

}