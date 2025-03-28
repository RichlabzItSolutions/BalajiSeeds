package com.balajiseeds.admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.employee.ActivityDetailActivity;
import com.balajiseeds.employee.AddActivityActivity;
import com.balajiseeds.employee.models.ModelActivity;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.util.List;

public class AdapterActivityList extends RecyclerView.Adapter<AdapterActivityList.ViewHolder> {

    Context context;
    List<ModelActivity.Data> activityList;


    public AdapterActivityList(Context context, List<ModelActivity.Data> activityList) {
        this.context = context;
        this.activityList = activityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_item_activity_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_edit.setVisibility(View.GONE);
        holder.tv_delete.setVisibility(View.GONE);
        ModelActivity.Data activity = activityList.get(position);
        holder.tv_date.setText(activity.getDate());
        holder.tv_title.setText(activity.getTitle());
        holder.tv_desc.setText(activity.getDescription());
        holder.tv_location.setText(activity.getLocation());
        holder.tr_name.setVisibility(View.VISIBLE);
        holder.tv_name.setText(activity.getEmpName());
        holder.tv_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SelectedActivity = activityList.get(holder.getAdapterPosition());
                context.startActivity(new Intent(context, ActivityDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_title, tv_location, tv_desc, tv_edit, tv_delete, tv_view_details,tv_name;
        TableRow tr_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_view_details = itemView.findViewById(R.id.tv_view_details);
            tv_name = itemView.findViewById(R.id.tv_name);
            tr_name = itemView.findViewById(R.id.tr_name);

        }
    }

}
