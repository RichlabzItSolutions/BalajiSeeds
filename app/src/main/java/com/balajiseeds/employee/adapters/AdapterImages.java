package com.balajiseeds.employee.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.ImageViewerDialogFragment;
import com.balajiseeds.R;
import com.balajiseeds.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ViewHolder> {
    List<String> fileList;
    Context context;
    FragmentManager fm;


    public AdapterImages(List<String> fileList, Context context,FragmentManager fm) {
        this.fileList = fileList;
        this.context = context;
        this.fm=fm;

    }

    @NonNull
    @Override
    public AdapterImages.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imageview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImages.ViewHolder holder, int position) {
        Glide.with(context).load(fileList.get(position)).placeholder(R.drawable.loading).into(holder.iv_img);
        holder.iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ImageViewerDialogFragment.class);
//                intent.putExtra("imageUrl", fileList.get(holder.getAdapterPosition()));
//                context.startActivity(intent);
                ArrayList<String> arrayList=new ArrayList<String>();
                arrayList.addAll(fileList);
                Constants.showImageViewerDialog(fm,arrayList, holder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemCount() {

        return fileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_image);
        }
    }

}
