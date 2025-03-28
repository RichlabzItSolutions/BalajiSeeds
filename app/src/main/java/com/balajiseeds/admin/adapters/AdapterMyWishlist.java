package com.balajiseeds.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;

public class AdapterMyWishlist extends RecyclerView.Adapter<AdapterMyWishlist.ViewHolder> {
    @NonNull
    @Override
    public AdapterMyWishlist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyWishlist.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
