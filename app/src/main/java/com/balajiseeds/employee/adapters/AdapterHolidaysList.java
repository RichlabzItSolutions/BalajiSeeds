package com.balajiseeds.employee.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.models.DeleteHolidays.DeleteHolidaysJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.utils.API;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.RetrofitClient;
import com.balajiseeds.utils.SharedPref;
import com.balajiseeds.utils.WebServices;

import java.util.List;

public class AdapterHolidaysList extends RecyclerView.Adapter<AdapterHolidaysList.ViewHolder> {
    Context context;
    List<GetHolidaysDetails> holidaysList;
    //    onClick onClick;
    WebServices webServices;

    API api;
    String token, Status ;

    String id  = "";

    public AdapterHolidaysList(Context context, List<GetHolidaysDetails> holidaysList) {
        this.context = context;
        this.holidaysList = holidaysList;
       // webServices = new WebServices(context);
        /*webServices.getExpenseHead(new WebServices.onGetExpenseHead() {
            @Override
            public void getExpenseHead(List<ModelExpenses.ExpensesHead> expenseHeadList) {
                Constants.ExpenseHead = expenseHeadList;
                notifyDataSetChanged();
            }
        });*/
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
        holder.tv_Edit.setVisibility(View.GONE);
        holder.tv_Delete.setVisibility(View.GONE);


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
