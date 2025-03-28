package com.balajiseeds.admin.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.balajiseeds.admin.fragments.FragmentProductList;
import com.balajiseeds.admin.fragments.ProductVarietyFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentProductList();
            case 1:
                return new ProductVarietyFragment();
            default:
                return new FragmentProductList();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Number of tabs
    }

    @Override
    public long getItemId(int position) {
        // Return the position as the unique ID
        return position;
    }

    @Override
    public boolean containsItem(long itemId) {
        // Always return false to force recreation of fragments
        return false;
    }
}
