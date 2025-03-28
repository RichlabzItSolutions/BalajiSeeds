package com.balajiseeds.employee.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.models.ModelEmployee;
import com.balajiseeds.employee.ActivityDetailActivity;
import com.balajiseeds.employee.AddActivityActivity;
import com.balajiseeds.employee.models.ModelActivity;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.util.List;

public class AdapterActivityList extends RecyclerView.Adapter<AdapterActivityList.ViewHolder> {

    Context context;
    List<ModelActivity.Data> activityList;
    WebServices webServices;

    public AdapterActivityList(Context context, List<ModelActivity.Data> activityList) {
        this.context = context;
        this.activityList = activityList;
        webServices = new WebServices(context);
    }

    @NonNull
    @Override
    public AdapterActivityList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_item_activity_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterActivityList.ViewHolder holder, int position) {
        ModelActivity.Data activity = activityList.get(position);
        holder.tv_date.setText(activity.getDate());
        holder.tv_title.setText(activity.getTitle());
        holder.tv_desc.setText(activity.getDescription());
        holder.tv_location.setText(activity.getLocation());
        holder.tv_edit.setOnClickListener(V -> {
            Constants.SelectedActivity = activityList.get(holder.getAdapterPosition());
            context.startActivity(new Intent(context, AddActivityActivity.class));
        });
        holder.tv_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SelectedActivity = activityList.get(holder.getAdapterPosition());
                context.startActivity(new Intent(context, ActivityDetailActivity.class));
            }
        });
        holder.tv_delete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                    webServices.deleteActivityEmp(new ModelActivity.DeleteRequest(activityList.get(holder.getAdapterPosition()).getId()), new WebServices.onResponse() {
                        @Override
                        public void response() {
                            activityList.remove(activityList.get(holder.getAdapterPosition()));
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    });
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        webServices.dismissDialog();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_title, tv_location, tv_desc, tv_edit, tv_delete, tv_view_details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_view_details = itemView.findViewById(R.id.tv_view_details);
        }
    }
}
