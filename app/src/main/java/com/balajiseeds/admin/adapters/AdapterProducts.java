package com.balajiseeds.admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.AddProductActivity;
import com.balajiseeds.admin.ProductActivity;
import com.balajiseeds.employee.models.ModelProducts;
import com.balajiseeds.employee.models.ModelsProducts;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {
    Context context;
    List<ModelsProducts.Product> productList;
    WebServices webServices;

    public AdapterProducts(Context context, List<ModelsProducts.Product> productList) {
        this.context = context;
        this.productList = productList;
        webServices = new WebServices(context);
    }

    @NonNull
    @Override
    public AdapterProducts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProducts.ViewHolder holder, int position) {
        holder.rl_main.setBackgroundTintList(context.getResources().getColorStateList(android.R.color.white, context.getTheme()));
        ModelsProducts.Product product = productList.get(position);
        if (product.getImages().size() > 0) {
            Glide.with(context).load(product.getImages().get(0).getImageUrl()).error(R.drawable.logo).placeholder(R.drawable.loading).into(holder.iv_img);
        } else {
            Glide.with(context).load(R.drawable.logo).placeholder(R.drawable.loading).into(holder.iv_img);
        }

        holder.tv_name.setText(product.getProductCrop());
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SelectedProduct = productList.get(holder.getAdapterPosition());
                //context.startActivity(new Intent(context, AddProductActivity.class));

                context.startActivity(new Intent(context, AddProductActivity.class)
                        .putExtra("productid",productList.get(holder.getAdapterPosition()).getId()));

            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                    @Override
                    public void YesClicked() {
                        webServices.deleteProductNew(productList.get(holder.getAdapterPosition()).getId(), new WebServices.onResponse() {
                            @Override
                            public void response() {
                                productList.remove(productList.get(holder.getAdapterPosition()));
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        });
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_edit, tv_delete;
        ImageView iv_img;
        RelativeLayout rl_main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_product_name);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            iv_img = itemView.findViewById(R.id.iv_image);
            rl_main=itemView.findViewById(R.id.rl_main);
        }
    }
}

