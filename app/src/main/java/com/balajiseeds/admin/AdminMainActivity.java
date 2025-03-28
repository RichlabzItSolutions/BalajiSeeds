package com.balajiseeds.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.balajiseeds.R;
import com.balajiseeds.admin.fragments.AccountFragment;
import com.balajiseeds.admin.fragments.FragmentHomeAdmin;
import com.balajiseeds.admin.fragments.FragmentOrders;
import com.balajiseeds.admin.fragments.FragmentProductList;
import com.balajiseeds.databinding.ActivityAdminMainBinding;
import com.balajiseeds.employee.EmployeeMainActivity;
import com.google.android.material.navigation.NavigationBarView;

public class AdminMainActivity extends AppCompatActivity {
    ActivityAdminMainBinding binding;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Intent serviceIntent = new Intent(this, ForegroundService.class);
//        serviceIntent.putExtra("inputExtra", "Foreground Service For Location & Logout");
//        ContextCompat.startForegroundService(this, serviceIntent);


        loadFrag(new FragmentHomeAdmin());
        binding.botnavAdminMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Home":
                        count = 0;
                        loadFrag(new FragmentHomeAdmin());
                        return true;
                    case "Orders":
                        loadFrag(new FragmentOrders());
                        return true;
                    case "Products":
                        loadFrag(new FragmentProductList());
                        return true;
                    case "Account":
                        loadFrag(new AccountFragment());
                        //Toast.makeText(AdminMainActivity.this, "Coming Soon...", Toast.LENGTH_SHORT).show();
                        return false;
                }
                return false;
            }
        });
    }

    public void loadFrag(Fragment frag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_admin_main, frag);
        if (count > 0) {
            ft.addToBackStack("");
        }
        count++;
        ft.commit();
    }

    public void setBotNav(int pos) {
        binding.botnavAdminMain.setSelectedItemId(pos);
    }


}