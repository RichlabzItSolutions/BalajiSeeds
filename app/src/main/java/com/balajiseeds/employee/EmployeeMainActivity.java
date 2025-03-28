package com.balajiseeds.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.balajiseeds.R;
import com.balajiseeds.databinding.ActivityEmployeeMainBinding;
import com.balajiseeds.employee.fragments.AccountFragment;
import com.balajiseeds.employee.fragments.AddOrdersFragment;
import com.balajiseeds.employee.fragments.FragmentHomeEmployee;
import com.balajiseeds.employee.fragments.FragmentMyOrders;
import com.balajiseeds.employee.fragments.FragmentProductList;
import com.balajiseeds.utils.ForegroundService;
import com.google.android.material.navigation.NavigationBarView;

public class EmployeeMainActivity extends BaseActivity {
    ActivityEmployeeMainBinding binding;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service For Location & Logout");
        ContextCompat.startForegroundService(this, serviceIntent);


        loadFrag(new FragmentHomeEmployee());
        binding.botnavEmployeeMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Home":
                        count = 0;
                        loadFrag(new FragmentHomeEmployee());
                        return true;
                    case "Orders":
                        loadFrag(new FragmentMyOrders());
                        return true;
                    case "Products":
                       // loadFrag(new FragmentProductList());
                        loadFrag(new AddOrdersFragment());
                        return true;
                    case "Account":
                        loadFrag(new AccountFragment());
                       // Toast.makeText(EmployeeMainActivity.this, "Coming Soon...", Toast.LENGTH_SHORT).show();
                        return false;
                }
                return false;
            }
        });
    }

    public void loadFrag(Fragment frag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_employee_main, frag);
        if (count > 0) {
            ft.addToBackStack("");
        }
        count++;
        ft.commit();
    }

    public void setBotNav(int pos) {
        binding.botnavEmployeeMain.setSelectedItemId(pos);
    }


}