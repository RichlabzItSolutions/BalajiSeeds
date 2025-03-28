package com.balajiseeds.employee.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.balajiseeds.R;
import com.balajiseeds.admin.AddProductActivity;
import com.balajiseeds.admin.models.AddProductVariety.DeleteProductVarietyJson;
import com.balajiseeds.databinding.FragmentAddOrdersBinding;
import com.balajiseeds.employee.models.AddOrder.AddOrderJson;
import com.balajiseeds.utils.WebServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AddOrdersFragment extends Fragment {
    FragmentAddOrdersBinding binding;

    WebServices webServices;

    String token, Status ;

    String partyId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddOrdersBinding.inflate(inflater, container, false);

        BottomNavigationView botnav = getActivity().findViewById(R.id.botnav_employee_main);
        botnav.getMenu().getItem(2).setChecked(true);

        webServices = new WebServices(getContext());


        /*binding.textView.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });*/

        binding.tvSubmit.setOnClickListener(v -> {


               String partyCode =   binding.etPartyCode.getText().toString().trim();
               String partyName =  binding.etPartyName.getText().toString().trim();


                if (partyCode.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Party Code", Toast.LENGTH_SHORT).show();
                } else if (partyName.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Party Name", Toast.LENGTH_SHORT).show();
                }

                else{

                    AddOrderJson request = new AddOrderJson(partyName, partyCode);
                    webServices.addOrders(request, new WebServices.onResponseAddOrder() {
                        @Override
                        public void responseAddOrder(String partyId) {

                            // removeAtPosition(position);8

                          //  Toast.makeText(getActivity(), "Please Select Product Variety", Toast.LENGTH_SHORT).show();

                            Fragment fragment = new FragmentProductList();

                            // If needed, pass data to the next fragment using a Bundle
                            Bundle bundle = new Bundle();
                            bundle.putString("partyId", partyId);
                            fragment.setArguments(bundle);


                           if (partyId != null){


                               // Replace the current fragment
                               FragmentManager fragmentManager = getParentFragmentManager();
                               FragmentTransaction transaction = fragmentManager.beginTransaction();
                               transaction.replace(R.id.fragment_container, fragment); // Replace with your container ID
                               transaction.addToBackStack(null); // Add to backstack if you want to navigate back
                               transaction.commit();
                           }

                            else{



                            }

                        }
                    });
                }

        });

        return binding.getRoot();

    }

}