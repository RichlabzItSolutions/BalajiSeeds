package com.balajiseeds.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.employee.models.ModelExpenses;
import com.balajiseeds.employee.models.ModelLeaves;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterExpenses extends RecyclerView.Adapter<AdapterExpenses.ViewHolder> {
    Context context;
    List<ModelExpenses.Expenses> expensesList;
    //    onClick onClick;
    WebServices webServices;

    public AdapterExpenses(Context context, List<ModelExpenses.Expenses> expensesList) {
        this.context = context;
        this.expensesList = expensesList;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_expenses_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelExpenses.Expenses expenses = expensesList.get(position);
//        holder.tv_exp_head.setText(expenses.getExpenseId());
        holder.tv_amount.setText(expenses.getAmount());
        holder.tv_date.setText(expenses.getDate());
        holder.tv_desc.setText(expenses.getExpensesDescription());
        holder.tv_name.setText(expenses.getName());
        holder.tv_empid.setText(expenses.getEmpId());


        Glide.with(context)
                .load(expenses.getReceipt())
                .placeholder(R.drawable.loading)
                .into(holder.logoId);
        if (Constants.ExpenseHead != null) {
            for (ModelExpenses.ExpensesHead expensesHead : Constants.ExpenseHead) {
                if (expensesHead.getId().equals(expenses.getExpenseId())) {
                    holder.tv_exp_head.setText(expensesHead.getExpenseHead());
                    break;
                } else {
                    holder.tv_exp_head.setText("unknown type");
                }
            }
        }

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
//    public interface onClick{
//        public void onEditClisked(ModelExpenses.Expenses expenses);
//
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_exp_head, tv_amount, tv_date, tv_desc, tv_empid, tv_name;
        ImageView logoId;

        //        tv_edit,tv_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_exp_head = itemView.findViewById(R.id.tv_expenses_head);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_desc = itemView.findViewById(R.id.tv_desc);
//            tv_edit=itemView.findViewById(R.id.tv_edit);
//            tv_delete=itemView.findViewById(R.id.tv_delete);
            tv_empid = itemView.findViewById(R.id.tv_empid);
            tv_name = itemView.findViewById(R.id.tv_name);
            logoId = itemView.findViewById(R.id.logoId);

        }
    }
}
