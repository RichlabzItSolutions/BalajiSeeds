package com.balajiseeds.admin.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.ImageViewerDialogFragment;
import com.balajiseeds.R;
import com.balajiseeds.employee.models.ModelVehicleReading;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.util.ArrayList;
import java.util.List;

public class AdapterVehicleReading extends RecyclerView.Adapter<AdapterVehicleReading.ViewHolder> {
    Context context;
    List<ModelVehicleReading.Reading> readingList;
    WebServices webServices;
    FragmentManager fm;


    public AdapterVehicleReading(Context context, List<ModelVehicleReading.Reading> readingList,FragmentManager fm) {
        this.context = context;
        this.readingList = readingList;
        this.fm=fm;
        webServices = new WebServices(context);
//        webServices.fetchVehicleType(new WebServices.onFetchVehicleType() {
//            @Override
//            public void fetchedVehicleType(List<ModelVehicleReading.VehicleType> vehicleTypeList) {
//                Constants.VehicleTypeList=vehicleTypeList;
//            }
//        });
    }

    @NonNull
    @Override
    public AdapterVehicleReading.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_vehicle_reading, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVehicleReading.ViewHolder holder, int position) {
        ModelVehicleReading.Reading reading = readingList.get(position);
        holder.tv_date.setText(reading.getDate());
        holder.tv_name.setText(reading.getName());
        holder.tv_empid.setText(reading.getEmpId());
        holder.tv_start_meter.setText(reading.getStartMeter());
        holder.tv_end_meter.setText(reading.getEndMeter());
        holder.tv_kms_driven.setText(reading.getKmsDriven());
//        if(Constants.VehicleTypeList!=null) {
//            for (ModelVehicleReading.VehicleType vehicleType : Constants.VehicleTypeList) {
//                if (vehicleType.getId().equals(reading.getVehicleType())) {
//                    holder.tv_vehicle_type.setText(vehicleType.getVehicleType());
//                    break;
//                } else {
//                    holder.tv_vehicle_type.setText("unknown type");
//                }
//            }
//        }
        holder.tv_vehicle_type.setText(reading.getVehicleType());
        holder.tv_view_start.setOnClickListener(v -> {
            if (!readingList.get(holder.getAdapterPosition()).getStartMeterImageUrl().isEmpty()) {
                ArrayList<String> arrayList=new ArrayList<String>();
                arrayList.add(readingList.get(holder.getAdapterPosition()).getStartMeterImageUrl());
                Constants.showImageViewerDialog(fm,arrayList,0);
//                context.startActivity(new Intent(context, ImageViewerDialogFragment.class).putExtra("imageUrl", readingList.get(holder.getAdapterPosition()).getStartMeterImageUrl()));
            } else {
                Toast.makeText(context, "Not Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tv_view_end.setOnClickListener(v -> {
            if (!readingList.get(holder.getAdapterPosition()).getEndMeterImageUrl().isEmpty()) {
                ArrayList<String> arrayList=new ArrayList<String>();
                arrayList.add(readingList.get(holder.getAdapterPosition()).getEndMeterImageUrl());
                Constants.showImageViewerDialog(fm,arrayList,0);

//                context.startActivity(new Intent(context, ImageViewerDialogFragment.class).putExtra("imageUrl", readingList.get(holder.getAdapterPosition()).getEndMeterImageUrl()));
            } else {
                Toast.makeText(context, "Not Uploaded", Toast.LENGTH_SHORT).show();
            }
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_name, tv_empid, tv_start_meter, tv_end_meter, tv_kms_driven, tv_view_start, tv_view_end, tv_vehicle_type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_empid = itemView.findViewById(R.id.tv_empid);
            tv_start_meter = itemView.findViewById(R.id.tv_start_meter);
            tv_end_meter = itemView.findViewById(R.id.tv_end_meter);
            tv_kms_driven = itemView.findViewById(R.id.tv_kms_driven);
            tv_view_start = itemView.findViewById(R.id.tv_view_start_meter);
            tv_view_end = itemView.findViewById(R.id.tv_view_end_meter);
            tv_vehicle_type = itemView.findViewById(R.id.tv_vehicle_type);
        }
    }
}
