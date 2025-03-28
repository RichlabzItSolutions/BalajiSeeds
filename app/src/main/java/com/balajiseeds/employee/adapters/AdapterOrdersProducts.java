package com.balajiseeds.employee.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.databinding.EmpItemOrderProductBinding;
import com.balajiseeds.employee.models.ModelProducts;

import java.util.List;

public class AdapterOrdersProducts extends RecyclerView.Adapter<AdapterOrdersProducts.ViewHolder> {
    Context context;
    List<ModelProducts.Product> productList;
    boolean status;

    public AdapterOrdersProducts(Context context, List<ModelProducts.Product> productList,boolean status) {
        this.context = context;
        this.productList = productList;
        this.status=status;
    }

    @NonNull
    @Override
    public AdapterOrdersProducts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EmpItemOrderProductBinding binding= EmpItemOrderProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrdersProducts.ViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F3F3F3"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        ModelProducts.Product product=productList.get(position);
        holder.binding.tvName.setText(product.getProductTitle());
        holder.binding.tvQty.setText(""+product.getQty());
        if(status){
            holder.binding.tvDelete.setVisibility(View.VISIBLE);
        }
        else {
            holder.binding.tvDelete.setVisibility(View.INVISIBLE);
        }
        holder.binding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Coming soon..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EmpItemOrderProductBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=EmpItemOrderProductBinding.bind(itemView);
        }
    }
}
