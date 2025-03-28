package com.balajiseeds.admin.models.GetExpensesHeadsList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetExpenseseHeadsDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("expenseHead")
    @Expose
    private String expenseHead;
    @SerializedName("status")
    @Expose
    private String status;


    public GetExpenseseHeadsDetails(String id, String expenseHead, String status) {
        this.id = id;
        this.expenseHead = expenseHead;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpenseHead() {
        return expenseHead;
    }

    public void setExpenseHead(String expenseHead) {
        this.expenseHead = expenseHead;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
