package com.balajiseeds.employee.adapters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.employee.models.ModelActivity;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.CustomImageViewDashed;
import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdapterAddImages extends RecyclerView.Adapter<AdapterAddImages.ViewHolder> {
    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_IMAGES};
    List<Object> fileList;
    Context context;
    ActivityResultLauncher<CropImageContractOptions> crop;
    List<String> deleteList;


    public AdapterAddImages(List<Object> fileList, Context context, ActivityResultLauncher<CropImageContractOptions> crop) {
        this.fileList = fileList;
        this.context = context;
        this.crop = crop;
        deleteList = new ArrayList<>();
    }

    public static Uri getFileUri(Context context, File file) {
        return FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
    }

    @NonNull
    @Override
    public AdapterAddImages.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_item_activity_img, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddImages.ViewHolder holder, int position) {

        if (position < (fileList.size())) {
            Log.i("datatype", "====" + String.valueOf(fileList.get(position).getClass()));
            if (String.valueOf(fileList.get(position).getClass()).equals("class android.net.Uri$HierarchicalUri")) {

                Glide.with(context).load(fileList.get(position)).placeholder(R.drawable.loading).into(holder.iv_img.getImageView());
                holder.iv_img.setTag("1");
                holder.iv_img.getUploadTextView().setVisibility(View.GONE);
                holder.iv_delete.setVisibility(View.VISIBLE);
            } else {
                ModelActivity.Photo photo = (ModelActivity.Photo) fileList.get(position);
                Log.i("photodata", "===" + photo.getImageUrl());
                Glide.with(context).load(photo.getImageUrl()).placeholder(R.drawable.loading).into(holder.iv_img.getImageView());
                holder.iv_img.setTag("1");
                holder.iv_img.getUploadTextView().setVisibility(View.GONE);
                holder.iv_delete.setVisibility(View.VISIBLE);
            }
        }
        holder.iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.iv_img.getTag() == null || !holder.iv_img.getTag().toString().equals("1")) {
                    int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(context, "You need to accept CAMERA permission to complete this task", Toast.LENGTH_SHORT).show();
                        Constants.checkPermission(context, permissions);
                    } else {
                        crop.launch(new CropImageContractOptions(null, new CropImageOptions()));
                    }
                }
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                    @Override
                    public void YesClicked() {
                        if (String.valueOf(fileList.get(holder.getAdapterPosition()).getClass()).equals("class android.net.Uri$HierarchicalUri")) {
                            //from disk
                            fileList.remove(fileList.get(holder.getAdapterPosition()));
                            notifyItemRemoved(holder.getAdapterPosition());
                        } else {
//                        from web
                            ModelActivity.Photo photo = (ModelActivity.Photo) fileList.get(holder.getAdapterPosition());
                            Log.i("photodatadelete", photo.getImageUrl() + "===" + photo.getId());
                            deleteList.add(photo.getId());
                            fileList.remove(fileList.get(holder.getAdapterPosition()));
                            Log.i("fillistsize", "===" + fileList.size());
                            notifyItemRemoved(holder.getAdapterPosition());

                        }
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {

        return fileList.size() + 1;
    }

    public void addTolist(Uri file) {
        int i = fileList.size();
        fileList.add(file);
        notifyItemInserted(i);
    }

    public List<File> getuploadedFiles() {
        List<File> FILELIST = new ArrayList<>();
        for (Object o : fileList) {
            if (String.valueOf(o.getClass()).equals("class android.net.Uri$HierarchicalUri")) {
                File f = Constants.uriToFile((Uri) o, context);
                FILELIST.add(f);
            }
        }
        return FILELIST;
    }

    public List<String> getDeleteList() {
        return deleteList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomImageViewDashed iv_img;
        ImageView iv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.img);
            iv_delete = itemView.findViewById(R.id.iv_delete);

        }
    }

}
