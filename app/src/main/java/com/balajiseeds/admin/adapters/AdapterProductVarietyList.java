package com.balajiseeds.admin.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.AddHolidaysActivity;
import com.balajiseeds.admin.models.AddProductVariety.AddProductVarietyJson;
import com.balajiseeds.admin.models.AddProductVariety.DeleteProductVarietyJson;
import com.balajiseeds.admin.models.AddProductVariety.GetProductVarietyDetails;
import com.balajiseeds.admin.models.AddProductVariety.UpdateProductVarietyJson;
import com.balajiseeds.admin.models.DeleteHolidays.DeleteHolidaysJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.databinding.AdminDialogAddProductVarietyBinding;
import com.balajiseeds.utils.API;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.RetrofitClient;
import com.balajiseeds.utils.SharedPref;
import com.balajiseeds.utils.WebServices;

import java.util.List;

public class AdapterProductVarietyList extends RecyclerView.Adapter<AdapterProductVarietyList.ViewHolder> {
    Context context;
    List<GetProductVarietyDetails> GetProductVarietyList;
    //    onClick onClick;
    WebServices webServices;

    API api;
    String token, Status;

    AdminDialogAddProductVarietyBinding binding1;

    public AdapterProductVarietyList(Context context, List<GetProductVarietyDetails> GetProductVarietyList) {
        this.context = context;
        this.GetProductVarietyList = GetProductVarietyList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_variety_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetProductVarietyDetails GetProductVariety = GetProductVarietyList.get(position);

        webServices = new WebServices(context);

        api = RetrofitClient.getRetrofitInstance().create(API.class);

        SharedPref sharedPref = new SharedPref(context);
        if (sharedPref.getString(SharedPref.token) != null && !sharedPref.getString(SharedPref.token).isEmpty()) {
            Log.d("token", "" + sharedPref.getString(SharedPref.token));

            token = sharedPref.getString(SharedPref.token);

          //  fetchExpenses();

            // webServices = new WebServices(HolidaysActivity.this);

            //  api = RetrofitClient.getRetrofitInstance().create(API.class);

            //  getHolidaysList();


        } else {


        }

        holder.tv_varietyID.setText(GetProductVariety.getVarietyId());
        holder.tv_varietyName.setText(GetProductVariety.getVarietyName());


        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(context);

                //binding1 = AdminDialogAddProductVarietyBinding.inflate(getLayoutInflater());
                binding1 = AdminDialogAddProductVarietyBinding.inflate(LayoutInflater.from(context));
                dialog.setContentView(binding1.getRoot());
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();

                binding1.tvVarietyName.setText(GetProductVariety.getVarietyName());
                binding1.txtAddProductVariety.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                binding1.tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String VarietyName;
                        VarietyName = binding1.tvVarietyName.getText().toString();

                        if (VarietyName.isEmpty()) {
                            Toast.makeText(context, "Please Enter Variety Name", Toast.LENGTH_SHORT).show();
                        } else {
                            UpdateProductVarietyJson updateProductVarietyJson = new UpdateProductVarietyJson(GetProductVariety.getVarietyId(), VarietyName, "1");
                            webServices.updateProductVariety(updateProductVarietyJson, new WebServices.onResponse() {
                                @Override
                                public void response() {
                                    dialog.dismiss();
                                    //getProductVariety();
                                }
                            });

                        }

                    }
                });

            }
        });

        holder.tv_delete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                   DeleteProductVarietyJson request = new DeleteProductVarietyJson(GetProductVarietyList.get(holder.getAdapterPosition()).getVarietyId());
                    webServices.deleteProductVariety(request, new WebServices.onResponse() {
                        @Override
                        public void response() {

                           // removeAtPosition(position);8
                            GetProductVarietyList.remove(GetProductVarietyList.get(holder.getAdapterPosition()));
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    });

                }
            });

        });


    }


    @Override
    public int getItemCount() {
        return GetProductVarietyList.size() ;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
       //webServices.dismissDialog();
    }
//    public interface onClick{
//        public void onEditClisked(ModelExpenses.Expenses expenses);
//
//    }

  /*  public void removeAtPosition(int position) {
        GetProductVarietyList.remove(position);
        notifyItemRemoved(position);
    }
*/
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_varietyID, tv_varietyName,  tv_edit, tv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_varietyID = itemView.findViewById(R.id.tv_varietyID);
            tv_varietyName = itemView.findViewById(R.id.tv_varietyName);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
