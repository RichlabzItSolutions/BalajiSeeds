package com.balajiseeds.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balajiseeds.admin.ProductActivity;
import com.balajiseeds.admin.adapters.AdapterItemCart;
import com.balajiseeds.admin.models.AddProductVariety.DeleteProductVarietyJson;
import com.balajiseeds.admin.models.GetOrder.CancelOrderJson;
import com.balajiseeds.databinding.ActivityMyCartBinding;
import com.balajiseeds.databinding.DialogShippingDetailsBinding;
import com.balajiseeds.employee.models.ModelCart;
import com.balajiseeds.employee.models.ModelOrder;
import com.balajiseeds.employee.models.getCartnew.GetCartDetails;
import com.balajiseeds.employee.models.getCartnew.GetCartNewResponse;
import com.balajiseeds.employee.models.placeOrder.PlaceOrderJson;
import com.balajiseeds.models.ModelCities;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.WebServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartActivity extends BaseActivity {
    ActivityMyCartBinding binding;
    WebServices webServices;
    DialogShippingDetailsBinding dbinding;
    String SelectedStateId="0",SelectedCityId="0",prestateid,precityid;

    String partyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices=new WebServices(MyCartActivity.this);
        binding.rvCartItems.setLayoutManager(new LinearLayoutManager(MyCartActivity.this));


        Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        partyId = intent.getStringExtra("partyId");
       /* webServices.getCart(new WebServices.onGetCart() {
            @Override
            public void getCart(List<ModelCart.Cart> cartList) {
                binding.rvCartItems.setAdapter(new AdapterItemCart(MyCartActivity.this,cartList));
            }
        });*/


        PlaceOrderJson request = new PlaceOrderJson(partyId);
        webServices.getTheCart(request, new WebServices.onGetCartNew() {
            @Override
            public void getCartNew(List<GetCartDetails> cartList) {
                binding.rvCartItems.setAdapter(new AdapterItemCart(MyCartActivity.this,cartList));
            }
        });






        binding.tvMyCart.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        binding.tvSubmit.setOnClickListener(v ->

               // ModelOrder.addOrderRequest request=new ModelOrder.addOrderRequest(name,mob,SelectedStateId,SelectedCityId,pincode,address);
     /*   webServices.addOrder( new WebServices.onResponse() {
            @Override
            public void response() {

                //finish();
                Intent intent = new Intent(MyCartActivity.this, ProductOrderActivity.class);
                startActivity(intent);
            }
        }));*/

                webServices.placeTheOrder(request, new WebServices.onResponsePlace() {
                    @Override
                    public void responsePlace(String orderId, String orderNumber) {


                        //finish();
                        Intent intent = new Intent(MyCartActivity.this, ProductOrderActivity.class);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("orderNumber", orderNumber);
                        startActivity(intent);
                    }
                }));

    }
    /*public void showShippingDialog(){
        Dialog dialog=new Dialog(MyCartActivity.this);
        dbinding=DialogShippingDetailsBinding.inflate(getLayoutInflater());
        dialog.setContentView(dbinding.getRoot());
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        dialog.show();
        setStates();
        dbinding.tvBack.setOnClickListener(v->{
            dialog.dismiss();
        });
        dbinding.tvSubmit.setOnClickListener(v->{
            //name,mob,pincode,address
            String name,mob,pincode,address;
            name=dbinding.etFullname.getText().toString();
            mob=dbinding.etMob.getText().toString();
            pincode=dbinding.etPincode.getText().toString();
            address=dbinding.etAddress.getText().toString();
            if(name.isEmpty()){
                dbinding.etFullname.setError("Please Enter Name");
                dbinding.etFullname.requestFocus();
            }else if (mob.isEmpty()||mob.length()<10){
                dbinding.etMob.setError("Invalid Mobile Number");
                dbinding.etMob.requestFocus();
            }
            else if(pincode.isEmpty()||pincode.length()<6){
                dbinding.etPincode.setError("Invalid Pincode");
                dbinding.etPincode.requestFocus();
            }
            else if(address.isEmpty()){
                dbinding.etAddress.setError("Please Enter Address");
                dbinding.etAddress.requestFocus();
            } else if (SelectedStateId.isEmpty()||SelectedStateId.equals("0")) {
                Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
            } else if (SelectedCityId.isEmpty()||SelectedCityId.equals("0")) {
                Toast.makeText(this, "Select City", Toast.LENGTH_SHORT).show();
            }
            else {
                ModelOrder.addOrderRequest request=new ModelOrder.addOrderRequest(name,mob,SelectedStateId,SelectedCityId,pincode,address);
                webServices.addOrder(request, new WebServices.onResponse() {
                    @Override
                    public void response() {
                        dialog.dismiss();
                        finish();
                    }
                });
            }

        });
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }

    private void setStates() {
        webServices.GetStates(new WebServices.onGetStates() {
            @Override
            public void getStates(List<ModelStates.State> stateList) {
                List<ModelSpinner> spinnerListcity = new ArrayList<>();
                spinnerListcity.add(new ModelSpinner("Select City", "0"));
                CustomSpinnerAdapter adaptercity = new CustomSpinnerAdapter(MyCartActivity.this, spinnerListcity);
                dbinding.spCity.setAdapter(adaptercity);
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select State", "0"));
                for (ModelStates.State s : stateList) {
                    spinnerList.add(new ModelSpinner(s.getStateName(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(MyCartActivity.this, spinnerList);
                dbinding.spState.setAdapter(adapter);
                for (int i = 0; i < spinnerList.size(); i++) {
                    if (spinnerList.get(i).getId().equals(prestateid)) {
                        dbinding.spState.setSelection(i);
                        break;
                    }
                }
                dbinding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(MyCartActivity.this, spinnerList);
                dbinding.spCity.setAdapter(adapter);
                for (int i = 0; i < spinnerList.size(); i++) {
                    if (spinnerList.get(i).getId().equals(precityid)) {
                        dbinding.spCity.setSelection(i);
                        break;
                    }
                }
                dbinding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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



}