package com.balajiseeds.admin;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.balajiseeds.R;
import com.balajiseeds.admin.models.AddProductJson;
import com.balajiseeds.admin.models.AddProductVariety.GetProductVarietyDetails;
import com.balajiseeds.admin.models.EditProductJson;
import com.balajiseeds.admin.models.ModelPackagingSize;
import com.balajiseeds.databinding.ActivityAddProductBinding;
import com.balajiseeds.employee.AddActivityActivity;
import com.balajiseeds.employee.adapters.AdapterAddImages;
import com.balajiseeds.employee.models.ModelProducts;
import com.balajiseeds.employee.models.ModelsProducts;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.WebServices;
import com.balajiseeds.utils.onfilePick;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddProductActivity extends AppCompatActivity {
    ActivityAddProductBinding binding;
    AdapterAddImages adapter;
    List<Object> objectList;
    List<MultipartBody.Part> imgList;
    WebServices webServices;
    String presizeid;
    String SelectedSizeId = "0";

    String SelectedProductVariety;
    String prestateid;
    private ActivityResultLauncher<CropImageContractOptions> crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerResult();
        webServices = new WebServices(AddProductActivity.this);
        binding.textView.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
//        binding.ivImage.getUploadTextView().setText("Upload Image");

        objectList = new ArrayList<>();
        if (Constants.SelectedProduct != null) {
            binding.etSkuCode.setText(Constants.SelectedProduct.getProductCode());
            binding.etProductTitle.setText(Constants.SelectedProduct.getProductCrop());
            binding.etDescription.setText(Constants.SelectedProduct.getDescription());
            //binding.etProductVariety.setText(Constants.SelectedProduct.getProductVariety());
            binding.etMrp.setText(Constants.SelectedProduct.getMrp());
            binding.etSellingPrice.setText(Constants.SelectedProduct.getDistributorPrice());
            binding.etPackagingSize.setText(Constants.SelectedProduct.getPackagingSize());
            binding.tvSubmit.setText("Update");
            binding.textView.setText("Update Product");
            objectList.addAll(Constants.SelectedProduct.getImages());
        }
        //setPackagingSize();


        webServices.getProductVariety( new WebServices.onGetProductVariety()  {
            @Override
            public void getProductVariety(List<GetProductVarietyDetails> ProductVarietyList) {
            /*    List<ModelSpinner> spinnerListcity = new ArrayList<>();
                spinnerListcity.add(new ModelSpinner("Select City", "0"));
                CustomSpinnerAdapter adaptercity = new CustomSpinnerAdapter(ExpensesActivity.this, spinnerListcity);*/
                // binding.spCity.setAdapter(adaptercity);
              /*  List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select State", "0"));
                for (ModelStates.State s : stateList) {
                    spinnerList.add(new ModelSpinner(s.getStateName(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddProductActivity.this, spinnerList);
                binding.spPackSize.setAdapter(adapter);
                for (int i = 0; i < spinnerList.size(); i++) {
                  *//*  if (spinnerList.get(i).getId().equals(prestateid)) {
                        binding.spPackSize.setSelection(i);
                        break;
                    }*//*
                }*/


                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select Product Variety", "0"));
                for (GetProductVarietyDetails s : ProductVarietyList) {
                    spinnerList.add(new ModelSpinner(s.getVarietyName(), s.getVarietyId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddProductActivity.this, spinnerList);
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
                            SelectedProductVariety = selectedModel.getId();
                            Log.d("ids", SelectedProductVariety);

                            //  setCity(SelectedStateId);
                           // fetchExpenses();
                        } else {
                            // setCity("0");
                            SelectedProductVariety = "";

                            Log.d("ids", SelectedProductVariety);

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });

        imgList = new ArrayList<>();
        adapter = new AdapterAddImages(objectList, AddProductActivity.this, crop);
        binding.rvImages.setLayoutManager(new GridLayoutManager(AddProductActivity.this, 3));
        binding.rvImages.setAdapter(adapter);
        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //skucode,producttitle,desc,mrp,sellingprice,packagingsize
                String productcode, producttitle,  productvariety, desc, mrp, sellingprice, packagingsize;
                productcode = binding.etSkuCode.getText().toString();
                producttitle = binding.etProductTitle.getText().toString();
                //productvariety = binding.etProductVariety.getText().toString();
                desc = binding.etDescription.getText().toString();
                mrp = binding.etMrp.getText().toString();
                sellingprice = binding.etSellingPrice.getText().toString();
                packagingsize = binding.etPackagingSize.getText().toString();
                //check for empty and show error
                if (productcode.isEmpty()) {
                    binding.etSkuCode.setError("Product Code is required");
                    binding.etSkuCode.requestFocus();
                } else if (producttitle.isEmpty()) {
                    binding.etProductTitle.setError("Product Title is required");
                    binding.etProductTitle.requestFocus();
                } else if (SelectedProductVariety.isEmpty()) {
                   // binding.etProductVariety.setError("Product Variety is required");
                  //  binding.etProductVariety.requestFocus();
                    Toast.makeText(AddProductActivity.this, "Please Select Product Variety", Toast.LENGTH_SHORT).show();

                } else if (desc.isEmpty()) {
                    binding.etDescription.setError("Description is required");
                    binding.etDescription.requestFocus();
                } else if (mrp.isEmpty()) {
                    binding.etMrp.setError("MRP is required");
                    binding.etMrp.requestFocus();
                } else if (sellingprice.isEmpty()) {
                    binding.etSellingPrice.setError("Distributor Price is required");
                    binding.etSellingPrice.requestFocus();
                } else if (packagingsize.isEmpty() || packagingsize.equals("0")) {
                    Toast.makeText(AddProductActivity.this, "Select Packing Size", Toast.LENGTH_SHORT).show();
                } else {

                   // MultipartBody.Part[] imageParts = new MultipartBody.Part[2];
                    //String partName;

                    for (File file : adapter.getuploadedFiles()) {
                        RequestBody imgrequestbody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part imgpart = MultipartBody.Part.createFormData("images[]", file.getName(), imgrequestbody);
                        imgList.add(imgpart);
                    }
                    if (Constants.SelectedProduct != null) {


                        webServices.getProductVariety( new WebServices.onGetProductVariety()  {
                            @Override
                            public void getProductVariety(List<GetProductVarietyDetails> ProductVarietyList) {

                                List<ModelSpinner> spinnerList = new ArrayList<>();
                                spinnerList.add(new ModelSpinner("Select Product Variety", "0"));
                                for (GetProductVarietyDetails s : ProductVarietyList) {
                                    spinnerList.add(new ModelSpinner(s.getVarietyName(), s.getVarietyId()));
                                }
                                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddProductActivity.this, spinnerList);
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
                                            SelectedProductVariety = selectedModel.getId();
                                            Log.d("ids", SelectedProductVariety);

                                            //  setCity(SelectedStateId);
                                            // fetchExpenses();
                                        } else {
                                            // setCity("0");
                                           // SelectedProductVariety = "";

                                           // Log.d("ids", SelectedProductVariety);

                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                            }
                        });

                        EditProductJson productRequestUpdate = new EditProductJson(Constants.SelectedProduct.getId(), productcode, producttitle, SelectedProductVariety,  desc,sellingprice, mrp , packagingsize, "1");
                        //productRequestUpdate.setDelete(adapter.getDeleteList());

                        Gson gsonUpdate = new Gson();
                        String jsonUpdate = gsonUpdate.toJson(productRequestUpdate);

                        RequestBody productDataUpdate = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonUpdate);

                        MultipartBody.Part[] imagePartsUpdate = new MultipartBody.Part[imgList.size()];
                        imagePartsUpdate = imgList.toArray(imagePartsUpdate);
                       /* webServices.updateProduct(imgList, jsonData, new WebServices.onResponse() {
                            @Override
                            public void response() {
                                Constants.SelectedProduct = null;
                                finish();
                            }
                        });*/

                        webServices.updateProductNew(productDataUpdate, imagePartsUpdate, new WebServices.onResponse() {
                            @Override
                            public void response() {
                                Constants.SelectedProduct = null;
                                finish();
                            }
                        });

                    } else {
                       AddProductJson productRequest = new AddProductJson(productcode, producttitle, SelectedProductVariety,  desc, mrp, sellingprice, packagingsize);
                       // productRequest.setDelete(adapter.getDeleteList());

                        Gson gson = new Gson();
                        String json = gson.toJson(productRequest);

                        RequestBody productData = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                        MultipartBody.Part[] imageParts = new MultipartBody.Part[imgList.size()];
                        imageParts = imgList.toArray(imageParts);

                       /* webServices.addProduct(imgList, jsonData, new WebServices.onResponse() {
                            @Override
                            public void response() {
                                finish();
                            }
                        });*/

                        webServices.addProductNew(productData, imageParts, new WebServices.onResponse() {
                            @Override
                            public void response() {
                                finish();
                            }
                        });

                    }
                }
            }
        });

    }

    /*public void setPackagingSize() {
        webServices.getPackagingSize(new WebServices.onGetPackagingSize() {
            @Override
            public void gotPackagingSize(List<ModelPackagingSize.PackagingSize> packagingSizeList) {
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select Packaging Size", "0"));
                for (ModelPackagingSize.PackagingSize s : packagingSizeList) {
                    spinnerList.add(new ModelSpinner(s.getTitle(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddProductActivity.this, spinnerList);
                binding.spPackSize.setAdapter(adapter);
                for (int i = 0; i < spinnerList.size(); i++) {
                    if (spinnerList.get(i).getId().equals(presizeid)) {
                        binding.spPackSize.setSelection(i);
                        break;
                    }
                }
                binding.spPackSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ModelSpinner selectedModel = (ModelSpinner) parent.getItemAtPosition(position);
                        if (!selectedModel.getId().equals("0")) {
                            SelectedSizeId = selectedModel.getId();
                        } else {
                            SelectedSizeId = "";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

    }*/

    private void registerResult() {
        crop = registerForActivityResult(new CropImageContract(), new ActivityResultCallback<CropImageView.CropResult>() {
            @Override
            public void onActivityResult(CropImageView.CropResult result) {
                Uri selectedFileUri = result.getUriContent();
                if (selectedFileUri != null) {
                    File f = Constants.uriToFile(selectedFileUri, AddProductActivity.this);
                    if (f != null) {
                        adapter.addTolist(selectedFileUri);
                    }
                }

            }
        });

    }

   /* private MultipartBody.Part prepareFilePart(String partName, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.SelectedProduct = null;
        webServices.dismissDialog();
    }
}