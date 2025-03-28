package com.balajiseeds.employee;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.balajiseeds.databinding.EmpActivityExpensesListBinding;
import com.balajiseeds.databinding.EmpDialogAddExpensesBinding;
import com.balajiseeds.databinding.EmpDialogAddVehicleReadingBinding;
import com.balajiseeds.employee.adapters.AdapterExpenses;
import com.balajiseeds.employee.models.ModelExpenses;
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

public class ExpensesActivity extends BaseActivity {
    EmpActivityExpensesListBinding binding;
    WebServices webServices;
    String SelectedEHId = "0";
    ActivityResultLauncher<CropImageContractOptions> crop;
    EmpDialogAddExpensesBinding binding1;
    public static File fileToUpload;
    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_IMAGES};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmpActivityExpensesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(ExpensesActivity.this);
        binding.tvAddExpenses.setOnClickListener(v -> {
            addExpensesDialog();
        });
        binding.txtEmpExpenseslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.rvExpensesList.setLayoutManager(new LinearLayoutManager(ExpensesActivity.this));
        registerResult(new onfilePick() {
            @Override
            public void onFilePicked(File file) {
                fileToUpload = file;
            }
        });
        binding.etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(ExpensesActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    DatePickerDialog dpd = new DatePickerDialog(ExpensesActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    Toast.makeText(ExpensesActivity.this, "Select from date first", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void selectedDate() {
        if (!binding.etFromDate.getText().toString().isEmpty() && !binding.etToDate.getText().toString().isEmpty()) {
            setExpensesList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setExpensesList();
    }

    private void addExpensesDialog() {
        Dialog dialog = new Dialog(ExpensesActivity.this);
        binding1 = EmpDialogAddExpensesBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding1.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setExpensesHead(binding1.spExpensesHead, "");
        binding1.txtExpenses.setOnClickListener(v -> {
            dialog.dismiss();
        });
        checkPhotoNull(binding1);
        binding1.ivDelete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(ExpensesActivity.this, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                    fileToUpload = null;
                    binding1.ivExpencesImg.getImageView().setImageDrawable(null);
                    checkPhotoNull(binding1);
                }
            });

        });
        dialog.setOnDismissListener(d -> {
            fileToUpload = null;
        });
        dialog.setOnCancelListener(d -> {
            fileToUpload = null;
        });
        binding1.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(ExpensesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding1.tvDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
        binding1.ivExpencesImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchFilePicker();

            }
        });
        binding1.tvSubmit.setOnClickListener(v -> {
            //amount,date,desc
            String amount = binding1.etAmount.getText().toString();
            String date = binding1.tvDate.getText().toString();
            String desc = binding1.etDescription.getText().toString();
            String expenseid = SelectedEHId;
            //check for empty of all above strings one by one
            if (amount.isEmpty()) {
                Toast.makeText(this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (date.isEmpty()) {
                Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (desc.isEmpty()) {
                Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (expenseid.isEmpty() || expenseid.equals("0")) {
                Toast.makeText(this, "Select Expense Head", Toast.LENGTH_SHORT).show();
            } else {
                ModelExpenses.Expenses request = new ModelExpenses.Expenses(expenseid, amount, date, desc);
                webServices.addExpensesNew(request,fileToUpload, new WebServices.onResponse() {
                    @Override
                    public void response() {
                        dialog.dismiss();
                        setExpensesList();
                    }
                });
            }
        });
        dialog.show();
    }
    private void checkPhotoNull(EmpDialogAddExpensesBinding dbinding) {
        if (fileToUpload == null) {
            dbinding.ivDelete.setVisibility(View.GONE);
            dbinding.ivExpencesImg.getUploadTextView().setVisibility(View.VISIBLE);

        } else {
            dbinding.ivDelete.setVisibility(View.VISIBLE);
            dbinding.ivExpencesImg.getUploadTextView().setVisibility(View.GONE);
        }
    }
    private void LaunchFilePicker() {
        int result = ContextCompat.checkSelfPermission(ExpensesActivity.this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(ExpensesActivity.this, "You need to accept CAMERA permission to complete this task", Toast.LENGTH_SHORT).show();
            Constants.checkPermission(ExpensesActivity.this, permissions);
        } else {
            CropImageOptions cropImageOptions=new CropImageOptions();
            cropImageOptions.imageSourceIncludeGallery=true;
            cropImageOptions.outputCompressFormat= Bitmap.CompressFormat.PNG;
            crop.launch(new CropImageContractOptions(null, cropImageOptions));
        }
    }
    private void editExpensesDialog(ModelExpenses.Expenses expenses) {

        Dialog dialog = new Dialog(ExpensesActivity.this);
        binding1 = EmpDialogAddExpensesBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding1.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setExpensesHead(binding1.spExpensesHead, expenses.expenseId);
        binding1.tvDate.setText(expenses.getDate());
        binding1.etDescription.setText(expenses.getExpensesDescription());
        binding1.etAmount.setText(expenses.getAmount());
        binding1.txtExpenses.setText("Edit Expenses");
        checkPhotoNull(binding1);
        if(expenses.getReceipt()!=null){
            Glide.with(ExpensesActivity.this).load(expenses.getReceipt()).into(binding1.ivExpencesImg.getImageView());
            binding1.ivDelete.setVisibility(View.VISIBLE);
            binding1.ivExpencesImg.getUploadTextView().setVisibility(View.GONE);
        }
        binding1.ivDelete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(ExpensesActivity.this, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                    fileToUpload = null;
                    binding1.ivExpencesImg.getImageView().setImageDrawable(null);
                    checkPhotoNull(binding1);
                }
            });

        });
        binding1.ivExpencesImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchFilePicker();

            }
        });
        dialog.setOnDismissListener(d -> {
            fileToUpload = null;
        });
        dialog.setOnCancelListener(d -> {
            fileToUpload = null;
        });
        binding1.txtExpenses.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding1.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(ExpensesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding1.tvDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
        binding1.tvSubmit.setOnClickListener(v -> {
            //amount,date,desc
            String amount = binding1.etAmount.getText().toString();
            String date = binding1.tvDate.getText().toString();
            String desc = binding1.etDescription.getText().toString();
            String expenseid = SelectedEHId;
            //check for empty of all above strings one by one
            if (amount.isEmpty()) {
                Toast.makeText(this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (date.isEmpty()) {
                Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (desc.isEmpty()) {
                Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (expenseid.isEmpty() || expenseid.equals("0")) {
                Toast.makeText(this, "Select Expense Head", Toast.LENGTH_SHORT).show();
            } else {
                ModelExpenses.Expenses request = new ModelExpenses.Expenses(expenses.id, expenseid, amount, date, desc);
                webServices.editExpensesNew(request,fileToUpload, new WebServices.onResponse() {
                    @Override
                    public void response() {
                        dialog.dismiss();
                        setExpensesList();
                    }
                });
            }
        });
        dialog.show();
    }

    private void setExpensesList() {
        webServices.getExpenses(new ModelExpenses.getExpensesRequest(String.valueOf(binding.etFromDate.getText()), String.valueOf(binding.etToDate.getText())), new WebServices.onGetExpenses() {
            @Override
            public void getExpenses(List<ModelExpenses.Expenses> expenseList) {
                AdapterExpenses adapter = new AdapterExpenses(ExpensesActivity.this, expenseList, new AdapterExpenses.onClick() {
                    @Override
                    public void onEditClisked(ModelExpenses.Expenses expenses) {
                        editExpensesDialog(expenses);
                    }
                });
                binding.rvExpensesList.setAdapter(adapter);
            }
        });
    }

    private void setExpensesHead(Spinner sp, String preEHId) {
        webServices.getExpenseHead(new WebServices.onGetExpenseHead() {
            @Override
            public void getExpenseHead(List<ModelExpenses.ExpensesHead> expenseHeadList) {
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select Expenses Head", "0"));
                for (ModelExpenses.ExpensesHead s : expenseHeadList) {
                    spinnerList.add(new ModelSpinner(s.getExpenseHead(), s.getId()));
                    CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(ExpensesActivity.this, spinnerList);
                    sp.setAdapter(adapter);
                    for (int i = 0; i < spinnerList.size(); i++) {
                        if (spinnerList.get(i).getId().equals(preEHId)) {
                            sp.setSelection(i);
                            break;
                        }
                    }
                }
                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ModelSpinner selectedModel = (ModelSpinner) parent.getItemAtPosition(position);
                        SelectedEHId = selectedModel.getId();
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
                    File f = Constants.uriToFile(selectedFileUri, ExpensesActivity.this);
                    if (f != null) {
                        fileToUpload = f;
                        try {
                            binding1.ivExpencesImg.getImageView().setImageURI(selectedFileUri);
//                            Glide.with(ExpensesActivity.this).load(selectedFileUri).into( binding1.ivExpencesImg.getImageView());
                            checkPhotoNull(binding1);
                        } catch (Exception e) {
                            e.printStackTrace();
//                            try {
//                                adapter.updatePhoto(selectedFileUri);
//                                adapter.checkPhotoNull();
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                            }
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
        webServices.dismissDialog();
    }
}
