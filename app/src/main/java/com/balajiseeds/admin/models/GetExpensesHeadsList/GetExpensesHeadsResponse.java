package com.balajiseeds.admin.models.GetExpensesHeadsList;

import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetExpensesHeadsResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("expensesHeads")
    @Expose
    private List<GetExpenseseHeadsDetails> expensesHeads;

    public GetExpensesHeadsResponse(String status, String message, List<GetExpenseseHeadsDetails> expensesHeads) {
        super();
        this.status = status;
        this.message = message;
        this.expensesHeads = expensesHeads;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<GetExpenseseHeadsDetails> getExpensesHeads() {
        return expensesHeads;
    }

    public void setData(List<GetExpenseseHeadsDetails> expensesHeads) {
        this.expensesHeads = expensesHeads;
    }
}
