package com.balajiseeds.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.balajiseeds.R;
import com.balajiseeds.employee.adapters.AdapterLeavesList;
import com.balajiseeds.databinding.EmpActivityLeaveListBinding;
import com.balajiseeds.employee.models.ModelLeaves;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.util.List;

public class LeaveListActivity extends BaseActivity {

    EmpActivityLeaveListBinding binding;
    WebServices webServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmpActivityLeaveListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(LeaveListActivity.this);
        binding.txtEmpLeavelist.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.tvAddLeave.setOnClickListener(v -> {
            startActivity(new Intent(this, AddLeaveActivity.class));
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Constants.leaveTypeList == null) {
            webServices.getLeavesTypeList(new WebServices.onFetchLeaveType() {
                @Override
                public void fetchedLeaveType(List<ModelLeaves.LeaveType> leaveTypeList) {
                    Constants.leaveTypeList = leaveTypeList;
                    fethLeaveList();
                }
            });
        } else {
            fethLeaveList();
        }


    }

    private void fethLeaveList() {
        webServices.getLeavesListEmp(new WebServices.onFetchLeavesEmp() {
            @Override
            public void fetchedLeaves(List<ModelLeaves.Leave> leaveList) {
                AdapterLeavesList adapterLeavesList = new AdapterLeavesList(LeaveListActivity.this, leaveList);
                binding.rvLeaveList.setLayoutManager(new LinearLayoutManager(LeaveListActivity.this));
                binding.rvLeaveList.setAdapter(adapterLeavesList);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }
}