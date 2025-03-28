package com.balajiseeds.admin.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.ViewPagerAdapter;
import com.balajiseeds.databinding.FragmentProductListBinding;
import com.balajiseeds.databinding.FragmentProductListMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class FragmentProductListMain extends Fragment {

    FragmentProductListMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductListMainBinding.inflate(inflater, container, false);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Product List");
                        break;
                    case 1:
                        tab.setText("Product Variety");
                        break;
                }

            }

        }).attach();


        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                adapter.notifyItemChanged(position);
            }
        });

        return binding.getRoot();
    }
}