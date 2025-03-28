package com.balajiseeds.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.models.AddExpensesHeads.DeleteExpensesHeadsJson;
import com.balajiseeds.admin.models.DeleteHolidays.DeleteHolidaysJson;
import com.balajiseeds.employee.adapters.AdapterExpenses;
import com.balajiseeds.employee.models.ModelExpenses;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;

import java.util.List;

public class AdapterExpensesHeads extends RecyclerView.Adapter<AdapterExpensesHeads.ViewHolder> {
    Context context;
    List<ModelExpenses.ExpensesHead> expensesList;

    WebServices webServices;

    onClick onClick;

    public AdapterExpensesHeads(Context context, List<ModelExpenses.ExpensesHead> expensesList, AdapterExpensesHeads.onClick onClick) {
        this.context = context;
        this.expensesList = expensesList;
        this.onClick = onClick;

        webServices = new WebServices(context);
        webServices.getExpenseHead(new WebServices.onGetExpenseHead() {
            @Override
            public void getExpenseHead(List<ModelExpenses.ExpensesHead> expenseHeadList) {
                Constants.ExpenseHead = expenseHeadList;
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public AdapterExpensesHeads.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_expenses_heads_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterExpensesHeads.ViewHolder holder, int position) {
        ModelExpenses.ExpensesHead expenses = expensesList.get(position);
       // holder.tv_amount.setText(expenses.getAmount());
       // holder.tv_date.setText(expenses.getDate());


        holder.tv_name.setText(expenses.getExpenseHead());
        holder.tv_desc.setText(expenses.getNarration());
       /* if (Constants.ExpenseHead != null) {
            for (ModelExpenses.ExpensesHead expensesHead : Constants.ExpenseHead) {
                if (expensesHead.getId().equals(expenses.getExpenseId())) {
                    holder.tv_name.setText(expensesHead.getExpenseHead());
                    break;
                } else {
                    holder.tv_name.setText("unknown type");
                }
            }
        }*/
        holder.tv_edit.setOnClickListener(v -> {
            onClick.onEditCliskedExpenseHeads(expenses);
        });
       /* holder.tv_delete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                    ModelExpenses.Expenses request = new ModelExpenses.Expenses(expensesList.get(holder.getAdapterPosition()).getId());
                    webServices.deleteExpenses(request, new WebServices.onResponse() {
                        @Override
                        public void response() {
                            expensesList.remove(expensesList.get(holder.getAdapterPosition()));
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    });
                }
            });

        });*/


        holder.tv_delete.setOnClickListener(v -> {
            Constants.ShowDeleteDialog(context, new Constants.OnYesClicked() {
                @Override
                public void YesClicked() {
                    DeleteExpensesHeadsJson request = new DeleteExpensesHeadsJson(expensesList.get(holder.getAdapterPosition()).getId());
                    webServices.deleteExpensesHeadsAdmin(request, new WebServices.onResponse() {
                        @Override
                        public void response() {

                            // removeAtPosition(position);8
                            expensesList.remove(expensesList.get(holder.getAdapterPosition()));
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


    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        webServices.dismissDialog();
    }

    public interface onClick {
        public void onEditCliskedExpenseHeads(ModelExpenses.ExpensesHead expenses);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_exp_head, tv_name, tv_date, tv_desc, tv_edit, tv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
