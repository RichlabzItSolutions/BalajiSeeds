package com.balajiseeds.employee;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.balajiseeds.databinding.EmpActivityVehicleReadingBinding;
import com.balajiseeds.databinding.EmpDialogAddVehicleReadingBinding;
import com.balajiseeds.employee.adapters.AdapterVehicleReading;
import com.balajiseeds.employee.models.ModelVehicleReading;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.WebServices;
import com.balajiseeds.utils.onfilePick;
import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.canhub.cropper.CropImageView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VehicleReadingActivity extends BaseActivity {
    public static File fileToUpload;
    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_IMAGES};
    public AdapterVehicleReading adapter;
    EmpActivityVehicleReadingBinding binding;
    EmpDialogAddVehicleReadingBinding dialog_reading_binding;
    WebServices webServices;
    String SelectedReadingId;
    String SelectedVTId = "0";
    String preVTId;
    ActivityResultLauncher<CropImageContractOptions> crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmpActivityVehicleReadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(VehicleReadingActivity.this);
        binding.txtEmpVehicleReading.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.tvAddReading.setOnClickListener(v -> {
            AddStartReadingDialog();
        });
        binding.rvVehicleReading.setLayoutManager(new LinearLayoutManager(VehicleReadingActivity.this));
        registerResult(new onfilePick() {
            @Override
            public void onFilePicked(File file) {
                fileToUpload = file;
            }
        });
        binding.etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(VehicleReadingActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    DatePickerDialog dpd = new DatePickerDialog(VehicleReadingActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    Toast.makeText(VehicleReadingActivity.this, "Select from date first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fetchVehicleReading();


    }

    private void selectedDate() {
        if (!binding.etFromDate.getText().toString().isEmpty() && !binding.etToDate.getText().toString().isEmpty()) {
            fetchVehicleReading();
        }
    }

    public void fetchVehicleReading() {
        webServices.fetchVehicleReading(new ModelVehicleReading.fetchReadingRequest(String.valueOf(binding.etFromDate.getText()), String.valueOf(binding.etToDate.getText())), new WebServices.onFetchVehicleReading() {
            @Override
            public void fetchedVehicleReading(List<ModelVehicleReading.Reading> vehicleReadingList) {
                adapter = new AdapterVehicleReading(VehicleReadingActivity.this, vehicleReadingList, crop);
                binding.rvVehicleReading.setAdapter(adapter);
            }
        });

    }

    public void AddStartReadingDialog() {
        Dialog dialog = new Dialog(VehicleReadingActivity.this);
        dialog_reading_binding = EmpDialogAddVehicleReadingBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialog_reading_binding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
        setVehicleType();
        checkPhotoNull(dialog_reading_binding);
        dialog_reading_binding.ivDelete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(VehicleReadingActivity.this, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                    fileToUpload = null;
                    dialog_reading_binding.ivReadingImg.getImageView().setImageDrawable(null);
                    checkPhotoNull(dialog_reading_binding);
                }
            });

        });
        dialog.setOnDismissListener(d -> {
            fileToUpload = null;
        });
        dialog.setOnCancelListener(d -> {
            fileToUpload = null;
        });
        dialog_reading_binding.txtVehicleReading.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog_reading_binding.ivReadingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        if(Constants.SelectedLeave!=null&&!Constants.SelectedLeave.getImageUrl().isEmpty()){
//                            PopupMenu popupMenu=new PopupMenu(VehicleReadingActivity.this,dialog_reading_binding.ivReadingImg);
//                            popupMenu.getMenuInflater().inflate(R.menu.option_menu_photo, popupMenu.getMenu());
//                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                                @Override
//                                public boolean onMenuItemClick(MenuItem item) {
//                                    if(item.getTitle().equals("View Photo")){
//                                        Intent intent=new Intent(VehicleReadingActivity.this, ImageViewerActivity.class);
//                                        intent.putExtra("imageUrl",Constants.SelectedLeave.getImageUrl());
//                                        startActivity(intent);
//                                        return true;
//                                    } else if (item.getTitle().equals("Edit Photo"))
//                                    {
//                                        LaunchFilePicker();
//                                        return true;
//                                    }
//                                    return false;
//                                }
//                            });
//                            popupMenu.show();
//                        }else {
//                            LaunchFilePicker();
//                        }
                LaunchFilePicker();

            }
        });
        dialog_reading_binding.tvSubmit.setOnClickListener(v -> {
            if (dialog_reading_binding.etReading.getText().toString().isEmpty()) {
                dialog_reading_binding.etReading.setError("Mandatory");
                dialog_reading_binding.etReading.requestFocus();
            } else if (SelectedVTId.equals("0")) {
                Toast.makeText(this, "Select Vehicle Type", Toast.LENGTH_SHORT).show();
            } else if (fileToUpload == null) {
                Toast.makeText(this, "Upload Photo", Toast.LENGTH_SHORT).show();
            } else {
                webServices.AddReading(new ModelVehicleReading.addReadingRequest("", SelectedVTId, dialog_reading_binding.etReading.getText().toString()), fileToUpload, new WebServices.onResponse() {
                    @Override
                    public void response() {
                        fileToUpload = null;
                        dialog.dismiss();
                        fetchVehicleReading();
                    }
                });
            }
        });
    }

    private void checkPhotoNull(EmpDialogAddVehicleReadingBinding dialogReadingBinding) {
        if (fileToUpload == null) {
            dialogReadingBinding.ivDelete.setVisibility(View.GONE);
            dialogReadingBinding.ivReadingImg.getUploadTextView().setVisibility(View.VISIBLE);
        } else {
            dialogReadingBinding.ivDelete.setVisibility(View.VISIBLE);
            dialogReadingBinding.ivReadingImg.getUploadTextView().setVisibility(View.GONE);
        }
    }

    private void LaunchFilePicker() {
        int result = ContextCompat.checkSelfPermission(VehicleReadingActivity.this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(VehicleReadingActivity.this, "You need to accept CAMERA permission to complete this task", Toast.LENGTH_SHORT).show();
            Constants.checkPermission(VehicleReadingActivity.this, permissions);
        } else {
            CropImageOptions cropImageOptions=new CropImageOptions();
            cropImageOptions.imageSourceIncludeGallery=false;
            cropImageOptions.outputCompressFormat= Bitmap.CompressFormat.PNG;
            crop.launch(new CropImageContractOptions(null, cropImageOptions));
        }
    }

    private void setVehicleType() {
        webServices.fetchVehicleType(new WebServices.onFetchVehicleType() {
            @Override
            public void fetchedVehicleType(List<ModelVehicleReading.VehicleType> vehicleTypeList) {
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select Vehicle Type", "0"));
                for (ModelVehicleReading.VehicleType s : vehicleTypeList) {
                    spinnerList.add(new ModelSpinner(s.getVehicleType(), s.getId()));
                    CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(VehicleReadingActivity.this, spinnerList);
                    dialog_reading_binding.spVehicleType.setAdapter(adapter);
                    for (int i = 0; i < spinnerList.size(); i++) {
                        if (spinnerList.get(i).getId().equals(preVTId)) {
                            dialog_reading_binding.spVehicleType.setSelection(i);
                            break;
                        }
                    }
                }
                dialog_reading_binding.spVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ModelSpinner selectedModel = (ModelSpinner) parent.getItemAtPosition(position);
                        SelectedVTId = selectedModel.getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


    }

    private void registerResult(onfilePick onfilePick) {
        crop = registerForActivityResult(new CropImageContract(), new ActivityResultCallback<CropImageView.CropResult>() {
            @Override
            public void onActivityResult(CropImageView.CropResult result) {
                Uri selectedFileUri = result.getUriContent();
                if (selectedFileUri != null) {
                    File f = Constants.uriToFile(selectedFileUri, VehicleReadingActivity.this);
                    if (f != null) {
                        fileToUpload = f;
                        try {
                            dialog_reading_binding.ivReadingImg.getImageView().setImageURI(selectedFileUri);
//                            Glide.with(VehicleReadingActivity.this).load(selectedFileUri).into( dialog_reading_binding.ivReadingImg.getImageView());
                            checkPhotoNull(dialog_reading_binding);
                        } catch (Exception e) {
                            try {
                                adapter.updatePhoto(selectedFileUri);
                                adapter.checkPhotoNull();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        onfilePick.onFilePicked(f);
                    }
                }

            }
        });


    }


}