package com.balajiseeds.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.balajiseeds.R;
import com.balajiseeds.databinding.EmpActivityDetailBinding;
import com.balajiseeds.employee.adapters.AdapterImages;
import com.balajiseeds.employee.models.ModelActivity;
import com.balajiseeds.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ActivityDetailActivity extends BaseActivity {

    EmpActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmpActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textView.setOnClickListener(v->{
            getOnBackPressedDispatcher().onBackPressed();
        });

        binding.tvDate.setText(Constants.SelectedActivity.getDate());
        binding.tvTitle.setText(Constants.SelectedActivity.getTitle());
        binding.tvLocation.setText(Constants.SelectedActivity.getLocation());
        binding.tvDesc.setText(Constants.SelectedActivity.getDescription());

        List<String> photolist = new ArrayList<>();
        for (ModelActivity.Photo p : Constants.SelectedActivity.getPhotos()) {
            photolist.add(p.getImageUrl());
        }
        binding.rvImages.setLayoutManager(new GridLayoutManager(ActivityDetailActivity.this, 3));
        binding.rvImages.setAdapter(new AdapterImages(photolist, ActivityDetailActivity.this,getSupportFragmentManager()));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.SelectedActivity = null;
    }
}