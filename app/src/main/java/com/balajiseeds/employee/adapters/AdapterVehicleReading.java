package com.balajiseeds.employee.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.databinding.EmpDialogAddVehicleReadingBinding;
import com.balajiseeds.employee.VehicleReadingActivity;
import com.balajiseeds.employee.models.ModelVehicleReading;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.WebServices;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;

import java.util.ArrayList;
import java.util.List;

public class AdapterVehicleReading extends RecyclerView.Adapter<AdapterVehicleReading.ViewHolder> {
    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_IMAGES};
    public EmpDialogAddVehicleReadingBinding dialog_reading_binding;
    Context context;
    List<ModelVehicleReading.Reading> readingList;
    WebServices webServices;
    String SelectedVTId = "0";
    ActivityResultLauncher<CropImageContractOptions> crop;


    public AdapterVehicleReading(Context context, List<ModelVehicleReading.Reading> readingList, ActivityResultLauncher<CropImageContractOptions> crop) {
        this.context = context;
        this.readingList = readingList;
        webServices = new WebServices(context);
        this.crop = crop;


    }

    @NonNull
    @Override
    public AdapterVehicleReading.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_item_vehicle_reading, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVehicleReading.ViewHolder holder, int position) {
        ModelVehicleReading.Reading reading = readingList.get(position);
        holder.tv_date.setText(reading.getDate());
        holder.tv_start_meter.setText(reading.getStartMeter());
        holder.tv_end_meter.setText(reading.getEndMeter());
        holder.tv_kms_driven.setText(reading.getKmsDriven());
        holder.tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endmeter = readingList.get(holder.getAdapterPosition()).getEndMeter();
                if (endmeter.isEmpty() || endmeter == null || Integer.valueOf(endmeter) == 0) {
                    AddEndReadingDialog(readingList.get(holder.getAdapterPosition()).getId(), readingList.get(holder.getAdapterPosition()).getVehicleType());
                } else {
                    Toast.makeText(context, "Already Update", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.tv_delete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                    webServices.DeleteReading(new ModelVehicleReading.deleteReadingRequest(readingList.get(holder.getAdapterPosition()).getId()), new WebServices.onResponse() {
                        @Override
                        public void response() {
                            readingList.remove(readingList.get(holder.getAdapterPosition()));
                            notifyDataSetChanged();
                        }
                    });
                }
            });

        });
    }

    @Override
    public int getItemCount() {
        return readingList.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        webServices.dismissDialog();
    }

    public void AddEndReadingDialog(String SelectedReadingId, String preVTId) {
        Dialog dialog = new Dialog(context);
        dialog_reading_binding = EmpDialogAddVehicleReadingBinding.inflate(((Activity) context).getLayoutInflater());
        dialog.setContentView(dialog_reading_binding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog_reading_binding.txtMeter.setText("End Meter");
        dialog_reading_binding.etReading.setHint("Enter End Meter Reading");
        dialog_reading_binding.txtVehicleReading.setText("Update End Meter");
        setVehicleType(dialog_reading_binding.spVehicleType, preVTId);
        checkPhotoNull();
        dialog_reading_binding.ivDelete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                    VehicleReadingActivity.fileToUpload = null;
                    dialog_reading_binding.ivReadingImg.getImageView().setImageDrawable(null);
                    checkPhotoNull();
                }
            });

        });
        dialog.setOnDismissListener(d -> {
            VehicleReadingActivity.fileToUpload = null;
        });
        dialog.setOnCancelListener(d -> {
            VehicleReadingActivity.fileToUpload = null;
        });
        dialog_reading_binding.txtVehicleReading.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog_reading_binding.ivReadingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchFilePicker();
//                        if(VehicleReadingActivity.fileToUpload != null){
//                            PopupMenu popupMenu=new PopupMenu(context,dialog_reading_binding.ivReadingImg);
//                            popupMenu.getMenuInflater().inflate(R.menu.option_menu_photo, popupMenu.getMenu());
//                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                                @Override
//                                public boolean onMenuItemClick(MenuItem item) {
//                                    if(item.getTitle().equals("View Photo")){
//                                        Intent intent=new Intent(context, ImageViewerActivity.class);
//                                        intent.putExtra("imageUrl",Constants.SelectedLeave.getImageUrl());
//                                        context.startActivity(intent);
//                                        return true;
//                                    } else if (item.getTitle().equals("Edit Photo"))
//                                    {
//                                        LaunchFilePicker();
//                                        return true;
//                                    }
//                                    return false;
//                                }
//                            });
//                            popupMenu.show();
//                        }else {
//                            LaunchFilePicker();
//                        }


            }
        });
        dialog_reading_binding.spVehicleType.setEnabled(false);
        dialog_reading_binding.tvSubmit.setOnClickListener(v -> {
            if (dialog_reading_binding.etReading.getText().toString().isEmpty()) {
                dialog_reading_binding.etReading.setError("Mandatory");
                dialog_reading_binding.etReading.requestFocus();
            } else if (SelectedVTId.equals("0")) {
                Toast.makeText(context, "Select Vehicle Type", Toast.LENGTH_SHORT).show();
            } else if (VehicleReadingActivity.fileToUpload == null) {
                Toast.makeText(context, "Upload Photo", Toast.LENGTH_SHORT).show();
            } else {
                webServices.AddReading(new ModelVehicleReading.addReadingRequest(SelectedReadingId, SelectedVTId, dialog_reading_binding.etReading.getText().toString()), VehicleReadingActivity.fileToUpload, new WebServices.onResponse() {
                    @Override
                    public void response() {
                        VehicleReadingActivity.fileToUpload = null;
                        dialog.dismiss();
                        ((VehicleReadingActivity) context).fetchVehicleReading();
                    }
                });
            }
        });
    }

    private void LaunchFilePicker() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(context, "You need to accept CAMERA permission to complete this task", Toast.LENGTH_SHORT).show();
            Constants.checkPermission(context, permissions);
        } else {
            CropImageOptions cropImageOptions=new CropImageOptions();
            cropImageOptions.imageSourceIncludeGallery=false;
            cropImageOptions.outputCompressFormat= Bitmap.CompressFormat.PNG;
            crop.launch(new CropImageContractOptions(null, cropImageOptions));
            
        }
    }

    private void setVehicleType(Spinner spVehicleType, String preVTId) {
        webServices.fetchVehicleType(new WebServices.onFetchVehicleType() {
            @Override
            public void fetchedVehicleType(List<ModelVehicleReading.VehicleType> vehicleTypeList) {
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select Vehicle Type", "0"));
                for (ModelVehicleReading.VehicleType s : vehicleTypeList) {
                    spinnerList.add(new ModelSpinner(s.getVehicleType(), s.getId()));
                    CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context, spinnerList);
                    spVehicleType.setAdapter(adapter);
                    for (int i = 0; i < spinnerList.size(); i++) {
                        if (spinnerList.get(i).getId().equals(preVTId)) {
                            spVehicleType.setSelection(i);
                            break;
                        }
                    }
                }
                spVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ModelSpinner selectedModel = (ModelSpinner) parent.getItemAtPosition(position);
                        SelectedVTId = selectedModel.getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


    }

    public void updatePhoto(Uri uri) {
        dialog_reading_binding.ivReadingImg.getImageView().setImageURI(uri);
    }

    public void checkPhotoNull() {
        if (VehicleReadingActivity.fileToUpload == null) {
            dialog_reading_binding.ivDelete.setVisibility(View.GONE);
            dialog_reading_binding.ivReadingImg.getUploadTextView().setVisibility(View.VISIBLE);
        } else {
            dialog_reading_binding.ivDelete.setVisibility(View.VISIBLE);
            dialog_reading_binding.ivReadingImg.getUploadTextView().setVisibility(View.GONE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_start_meter, tv_end_meter, tv_kms_driven, tv_update, tv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_start_meter = itemView.findViewById(R.id.tv_start_meter);
            tv_end_meter = itemView.findViewById(R.id.tv_end_meter);
            tv_kms_driven = itemView.findViewById(R.id.tv_kms_driven);
            tv_update = itemView.findViewById(R.id.tv_update);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
