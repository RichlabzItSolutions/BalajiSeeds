package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.AdapterMyWishlist;
import com.balajiseeds.databinding.ActivityMyWishlistBinding;

public class MyWishlistActivity extends AppCompatActivity {
    ActivityMyWishlistBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.rvMyWishlist.setLayoutManager(new LinearLayoutManager(MyWishlistActivity.this));
        binding.rvMyWishlist.setAdapter(new AdapterMyWishlist());
        binding.tvMyWishlist.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }
}