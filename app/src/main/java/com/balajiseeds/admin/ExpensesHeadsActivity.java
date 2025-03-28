package com.balajiseeds.admin;

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
import android.widget.Spinner;
import android.widget.Toast;
import com.balajiseeds.admin.adapters.AdapterExpensesHeads;
import com.balajiseeds.admin.adapters.AdapterHolidaysList;
import com.balajiseeds.admin.models.AddExpensesHeads.AddExpensesHeadsJson;
import com.balajiseeds.admin.models.AddExpensesHeads.EditExpensesHeadsJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.databinding.ActivityExpensesHeadsBinding;
import com.balajiseeds.databinding.AdminDialogAddExpensesHeadsBinding;
import com.balajiseeds.databinding.EmpDialogAddExpensesBinding;
import com.balajiseeds.employee.ExpensesActivity;
import com.balajiseeds.employee.adapters.AdapterExpenses;
import com.balajiseeds.employee.models.ModelExpenses;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpensesHeadsActivity extends AppCompatActivity {

    ActivityExpensesHeadsBinding binding;

    WebServices webServices;
    String SelectedEHId = "0";
    AdminDialogAddExpensesHeadsBinding binding1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpensesHeadsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webServices = new WebServices(ExpensesHeadsActivity.this);
        binding.tvAddExpenses.setOnClickListener(v -> {
            addExpensesDialog();
        });
        binding.txtEmpExpenseslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.rvExpensesList.setLayoutManager(new LinearLayoutManager(ExpensesHeadsActivity.this));

        /*binding.etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(com.balajiseeds.employee.ExpensesActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    DatePickerDialog dpd = new DatePickerDialog(com.balajiseeds.employee.ExpensesActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    Toast.makeText(com.balajiseeds.employee.ExpensesActivity.this, "Select from date first", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

    }

  /*  private void selectedDate() {
        if (!binding.etFromDate.getText().toString().isEmpty() && !binding.etToDate.getText().toString().isEmpty()) {
            setExpensesList();
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        setExpensesList();
    }

    private void addExpensesDialog() {
        Dialog dialog = new Dialog(ExpensesHeadsActivity.this);
        binding1 = AdminDialogAddExpensesHeadsBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding1.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        binding1.txtExpenses.setOnClickListener(v -> {
            dialog.dismiss();
        });


        dialog.setOnDismissListener(d -> {

        });
        dialog.setOnCancelListener(d -> {

        });

        binding1.tvSubmit.setOnClickListener(v -> {
            //amount,date,desc
            String amount = binding1.etAmount.getText().toString();
            String narration = binding1.etDescription.getText().toString();

            String expenseid = SelectedEHId;
            //check for empty of all above strings one by one
            if (amount.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            }  else if (narration.isEmpty()) {
                Toast.makeText(this, "Narration cannot be empty", Toast.LENGTH_SHORT).show();
            }else {
                AddExpensesHeadsJson request = new AddExpensesHeadsJson( amount, narration);
                webServices.addExpensesHeadsAdmin(request, new WebServices.onResponse() {
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

    private void editExpensesDialog(ModelExpenses.ExpensesHead expenses) {

        Dialog dialog = new Dialog(ExpensesHeadsActivity.this);
        binding1 = AdminDialogAddExpensesHeadsBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding1.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        binding1.etAmount.setText(expenses.getExpenseHead());
        binding1.etDescription.setText(expenses.getNarration());
        binding1.txtExpenses.setText("Edit Expenses Heads");

        dialog.setOnDismissListener(d -> {

        });
        dialog.setOnCancelListener(d -> {

        });
        binding1.txtExpenses.setOnClickListener(v -> {
            dialog.dismiss();
        });

        binding1.tvSubmit.setOnClickListener(v -> {
            //amount,date,desc
            String amount = binding1.etAmount.getText().toString();
            String narration = binding1.etDescription.getText().toString();
           // String expenseid = SelectedEHId;
            //check for empty of all above strings one by one
            if (amount.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (narration.isEmpty()) {
                Toast.makeText(this, "Narration cannot be empty", Toast.LENGTH_SHORT).show();
            }else {
                EditExpensesHeadsJson request = new EditExpensesHeadsJson( expenses.id, amount, narration, "1");
                webServices.updateExpensesHeadsAdmin(request, new WebServices.onResponse() {
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
        webServices.getExpenseHeadAdmin(new WebServices.onGetExpenseHeadAdmin() {
            @Override
            public void getExpenseHeadAdmin(List<ModelExpenses.ExpensesHead> expenseHeadAdminList) {
                binding.rvExpensesList.setLayoutManager(new LinearLayoutManager(ExpensesHeadsActivity.this));
                binding.rvExpensesList.setAdapter(new AdapterExpensesHeads(ExpensesHeadsActivity.this, expenseHeadAdminList, new AdapterExpensesHeads.onClick() {
                    @Override
                    public void onEditCliskedExpenseHeads(ModelExpenses.ExpensesHead expenses) {
                        editExpensesDialog(expenses);
                    }
                }));


/*
                AdapterExpensesHeads adapter = new AdapterExpensesHeads(ExpensesHeadsActivity.this, expenseHeadAdminList, new AdapterExpensesHeads.onClick() {
                    @Override
                    public void onEditCliskedExpenseHeads(ModelExpenses.Expenses expenses) {
                        editExpensesDialog(expenses);
                    }
                });*/


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }

}