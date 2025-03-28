package com.balajiseeds.employee;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.balajiseeds.ImageViewerDialogFragment;
import com.balajiseeds.R;
import com.balajiseeds.databinding.EmpActivityAddLeaveBinding;
import com.balajiseeds.employee.models.ModelLeaves;
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

public class AddLeaveActivity extends BaseActivity {

    public static File fileToUpload;
    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_IMAGES};
    EmpActivityAddLeaveBinding binding;
    WebServices webServices;
    String SelectedLeaveId = "0";
    String preLeaveId;
    ActivityResultLauncher<CropImageContractOptions> crop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmpActivityAddLeaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(AddLeaveActivity.this);
        registerResult(new onfilePick() {
            @Override
            public void onFilePicked(File file) {
                fileToUpload = file;
                checkSelectedLeave();
            }
        });
        binding.txtAddLeave.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.SelectedLeave != null) {
                    Constants.ShowDeleteDialog(AddLeaveActivity.this, new Constants.OnYesClicked() {
                        @Override
                        public void YesClicked() {
                            Constants.SelectedLeave.setImageUrl("");
                            checkSelectedLeave();
                        }
                    });

                } else {
                    Constants.ShowDeleteDialog(AddLeaveActivity.this, new Constants.OnYesClicked() {
                        @Override
                        public void YesClicked() {
                            fileToUpload = null;
                            checkSelectedLeave();
                        }
                    });

                }
            }
        });
        checkSelectedLeave();
        binding.txtAddLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(AddLeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.etFromDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
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
                    DatePickerDialog dpd = new DatePickerDialog(AddLeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            binding.etToDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                        }
                    }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    String dateString = binding.etFromDate.getText().toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date date = dateFormat.parse(dateString);
                        long milliseconds = date.getTime();
                        dpd.getDatePicker().setMinDate(milliseconds);

                    } catch (ParseException e) {
                        dpd.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                        e.printStackTrace();
                    }
                    dpd.show();
                } else {
                    Toast.makeText(AddLeaveActivity.this, "Select from date first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.ivFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.SelectedLeave != null && !Constants.SelectedLeave.getImageUrl().isEmpty()) {
                    PopupMenu popupMenu = new PopupMenu(AddLeaveActivity.this, binding.ivFile);
                    popupMenu.getMenuInflater().inflate(R.menu.option_menu_photo, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals("View Photo")) {
//                                Intent intent = new Intent(AddLeaveActivity.this, ImageViewerDialogFragment.class);
//                                intent.putExtra("imageUrl", Constants.SelectedLeave.getImageUrl());
                                ArrayList<String> arrayList=new ArrayList<String>();;
                                arrayList.add(Constants.SelectedLeave.getImageUrl());
                                Constants.showImageViewerDialog(getSupportFragmentManager(),arrayList,0);
//                                startActivity(intent);
                                return true;
                            } else if (item.getTitle().equals("Edit Photo")) {
                                LaunchFilePicker();
                                return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                } else {
                    LaunchFilePicker();
                }
            }
        });
        binding.tvSubmit.setOnClickListener(v -> {
            String fromdate = binding.etFromDate.getText().toString();
            String todate = binding.etToDate.getText().toString();
            String leavetype = SelectedLeaveId;
            String subject = binding.etSubject.getText().toString();
            String message = binding.etMsg.getText().toString();
            if (fromdate.isEmpty()) {
                binding.etFromDate.setError("From Date is required");
                binding.etFromDate.requestFocus();
            } else if (todate.isEmpty()) {
                binding.etToDate.setError("To Date is required");
                binding.etToDate.requestFocus();
            } else if (subject.isEmpty()) {
                binding.etSubject.setError("Subject is required");
                binding.etSubject.requestFocus();
            } else if (message.isEmpty()) {
                binding.etMsg.setError("Message is required");
                binding.etMsg.requestFocus();
            } else if (leavetype.equals("0")) {
                Toast.makeText(this, "Select leave type", Toast.LENGTH_SHORT).show();
            } else {
                if (Constants.SelectedLeave != null) {
//                    if(!Constants.SelectedLeave.getImageUrl().isEmpty()) {
                    //String id, String leaveType, String subject, String message, String fromDate, String toDate
                    ModelLeaves.Leave leave = new ModelLeaves.Leave(Constants.SelectedLeave.getId(), leavetype, subject, message, fromdate, todate);
                    webServices.updateLeave(leave, fileToUpload, new WebServices.onResponse() {
                        @Override
                        public void response() {
                            finish();
                        }
                    });
//                    }
//                    else {
//                        Toast.makeText(this, "Upload Proof", Toast.LENGTH_SHORT).show();
//                    }
                } else {
//                    if (fileToUpload==null) {
//                        Toast.makeText(this, "Upload Proof", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                String leaveType, String subject, String message, String fromDate, String toDate
                    ModelLeaves.Leave leave = new ModelLeaves.Leave(leavetype, subject, message, fromdate, todate);
                    webServices.AddLeave(leave, fileToUpload, new WebServices.onResponse() {
                        @Override
                        public void response() {
                            fileToUpload = null;
                            finish();
                        }
                    });
//                    }
                }
            }
        });
    }

    private void checkSelectedLeave() {
        if (Constants.SelectedLeave != null) {
            binding.txtAddLeave.setText("Edit Leave");
            binding.etFromDate.setText(Constants.SelectedLeave.getFromDate());
            binding.etToDate.setText(Constants.SelectedLeave.getToDate());
            binding.etSubject.setText(Constants.SelectedLeave.getSubject());
            binding.etMsg.setText(Constants.SelectedLeave.getMessage());
            if (Constants.SelectedLeave.getImageUrl() != null && !Constants.SelectedLeave.getImageUrl().isEmpty()) {
                Glide.with(AddLeaveActivity.this).load(Constants.SelectedLeave.getImageUrl()).placeholder(R.drawable.loading).into(binding.ivFile.getImageView());
                binding.ivFile.getUploadTextView().setVisibility(View.GONE);
                binding.ivDelete.setVisibility(View.VISIBLE);
            } else {
                if (fileToUpload != null) {
                    binding.ivFile.getUploadTextView().setVisibility(View.GONE);
                    binding.ivDelete.setVisibility(View.VISIBLE);
                } else {
                    binding.ivFile.getImageView().setImageDrawable(null);
                    binding.ivFile.getUploadTextView().setVisibility(View.VISIBLE);
                    binding.ivDelete.setVisibility(View.GONE);
                }

            }

            preLeaveId = Constants.SelectedLeave.getLeaveType();
            setLeaveTypes();
        } else {
            if (fileToUpload != null) {
                binding.ivFile.getUploadTextView().setVisibility(View.GONE);
                binding.ivDelete.setVisibility(View.VISIBLE);
            } else {
                binding.ivFile.getImageView().setImageDrawable(null);
                binding.ivFile.getUploadTextView().setVisibility(View.VISIBLE);
                binding.ivDelete.setVisibility(View.GONE);
            }
            binding.txtAddLeave.setText("Add Leave");
            setLeaveTypes();
        }
    }

    private void LaunchFilePicker() {
        int result = ContextCompat.checkSelfPermission(AddLeaveActivity.this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(AddLeaveActivity.this, "You need to accept CAMERA permission to complete this task", Toast.LENGTH_SHORT).show();
            Constants.checkPermission(AddLeaveActivity.this, permissions);
        } else {
            crop.launch(new CropImageContractOptions(null, new CropImageOptions()));
        }
    }

    private void setLeaveTypes() {
        webServices.getLeavesTypeList(new WebServices.onFetchLeaveType() {
            @Override
            public void fetchedLeaveType(List<ModelLeaves.LeaveType> leaveTypeList) {
                Constants.leaveTypeList = leaveTypeList;
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select Leave Type", "0"));
                for (ModelLeaves.LeaveType s : leaveTypeList) {
                    spinnerList.add(new ModelSpinner(s.getLeaveType(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddLeaveActivity.this, spinnerList);
                binding.spLeaveType.setAdapter(adapter);
                for (int i = 0; i < spinnerList.size(); i++) {
                    if (spinnerList.get(i).getId().equals(preLeaveId)) {
                        binding.spLeaveType.setSelection(i);
                        break;
                    }
                }
                binding.spLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ModelSpinner selectedModel = (ModelSpinner) parent.getItemAtPosition(position);
                        SelectedLeaveId = selectedModel.getId();
                        preLeaveId=SelectedLeaveId;
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
                    File f = Constants.uriToFile(selectedFileUri, AddLeaveActivity.this);
                    if (f != null) {
                        try {
                            binding.ivFile.getImageView().setImageURI(selectedFileUri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        onfilePick.onFilePicked(f);
                    }
                }

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.SelectedLeave = null;
        fileToUpload = null;
        webServices.dismissDialog();
    }
}
