package com.balajiseeds.admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.AddEmployeeActivity;
import com.balajiseeds.admin.AddHolidaysActivity;
import com.balajiseeds.admin.HolidaysActivity;
import com.balajiseeds.admin.models.AddHoilidays.AddHolidaysResponse;
import com.balajiseeds.admin.models.DeleteHolidays.DeleteHolidaysJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.employee.adapters.AdapterExpenses;
import com.balajiseeds.employee.models.ModelExpenses;
import com.balajiseeds.utils.API;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.RetrofitClient;
import com.balajiseeds.utils.SharedPref;
import com.balajiseeds.utils.WebServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterHolidaysList extends RecyclerView.Adapter<AdapterHolidaysList.ViewHolder> {
    Context context;
    List<GetHolidaysDetails> holidaysList;
    //    onClick onClick;
    WebServices webServices;

    API api;
    String token, Status;

    String type = "";

    String id  = "";

    public AdapterHolidaysList(Context context, List<GetHolidaysDetails> holidaysList) {
        this.context = context;
        this.holidaysList = holidaysList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holidays_admin_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetHolidaysDetails holidays = holidaysList.get(position);

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


//        holder.tv_exp_head.setText(expenses.getExpenseId());
        holder.tv_from_date.setText(holidays.getFromDate());
        holder.tv_to_date.setText(holidays.getToDate());
        holder.tv_title.setText(holidays.getHolidayTitle());
        holder.tv_State.setText(holidays.getStateName());
        holder.tv_Description.setText(holidays.getDescription());
        holder.tv_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* type = "1";

                        Intent intent = new Intent(context, AddHolidaysActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("id", holidays.getId());
                        intent.putExtra("holidayTitle", holidays.getHolidayTitle());
                        intent.putExtra("fromDate", holidays.getFromDate());
                        intent.putExtra("toDate", holidays.getToDate());
                        intent.putExtra("description", holidays.getDescription());
                        intent.putExtra("states", holidays.getStateId());
                        intent.putExtra("status", holidays.getStatus());


                        context.startActivity(intent);*/


                Constants.SelectedHOLIDAY = holidaysList.get(holder.getAdapterPosition());
                context.startActivity(new Intent(context, AddHolidaysActivity.class));

              /*  Intent intent = new Intent(context, AddHolidaysActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("id", holidays.getId());
                intent.putExtra("holidayTitle", holidays.getHolidayTitle());
                intent.putExtra("fromDate", holidays.getFromDate());
                intent.putExtra("toDate", holidays.getToDate());
                intent.putExtra("description", holidays.getDescription());
                intent.putExtra("states", holidays.getStateId());
                intent.putExtra("status", holidays.getStatus());*/


                //context.startActivity(intent);

            }
        });

        holder.tv_Delete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                   DeleteHolidaysJson request = new DeleteHolidaysJson(holidaysList.get(holder.getAdapterPosition()).getId());
                    webServices.deleteHolidays(request, new WebServices.onResponse() {
                        @Override
                        public void response() {

                           // removeAtPosition(position);8
                            holidaysList.remove(holidaysList.get(holder.getAdapterPosition()));
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    });

/*
                    api.deleteHolidays(token, new DeleteHolidaysJson(holidays.getId())).enqueue(new Callback<AddHolidaysResponse>() {
                        @Override
                        public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                            // cwd.dismiss();
                            if(response.body()!=null){
                                AddHolidaysResponse getHolidaysResponse= response.body();

                                Log.d("kol", getHolidaysResponse.getMessage());

                                //  Status =  GetHolidaysResponse.getStatus();
                                //holodaysList = new ArrayList<>();
                                //   holodaysList = getHolidaysResponse.getData();



                            }
                            else {
                                // showToast("Something went wrong");
                            }
                        }

                        @Override
                        public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                            //cwd.dismiss();
                            // showToast("something went wrong");
                            t.printStackTrace();
                        }
                    });
*/
                }
            });

        });
       /* if (Constants.ExpenseHead != null) {
            for (ModelExpenses.ExpensesHead expensesHead : Constants.ExpenseHead) {
                if (expensesHead.getId().equals(expenses.getExpenseId())) {
                    holder.tv_exp_head.setText(expensesHead.getExpenseHead());
                    break;
                } else {
                    holder.tv_exp_head.setText("unknown type");
                }
            }
        }*/

    }

    @Override
    public int getItemCount() {
        return holidaysList.size();
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

    public void removeAtPosition(int position) {
        holidaysList.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_from_date, tv_to_date, tv_title, tv_State, tv_Description, tv_Edit, tv_Delete;

        //        tv_edit,tv_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_from_date = itemView.findViewById(R.id.tv_from_date);
            tv_to_date = itemView.findViewById(R.id.tv_to_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_State = itemView.findViewById(R.id.tv_State);
//            tv_edit=itemView.findViewById(R.id.tv_edit);
//            tv_delete=itemView.findViewById(R.id.tv_delete);
            tv_Description = itemView.findViewById(R.id.tv_Description);
            tv_Edit = itemView.findViewById(R.id.tv_Edit);
            tv_Delete = itemView.findViewById(R.id.tv_Delete);

        }
    }
}
