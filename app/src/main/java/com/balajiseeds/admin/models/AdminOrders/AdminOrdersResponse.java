package com.balajiseeds.admin.models.AdminOrders;

import com.balajiseeds.employee.models.MyOrders.EmpOrdersDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminOrdersResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<AdminOrdersDetails> data;


    public AdminOrdersResponse(String status, String message,  List<AdminOrdersDetails> data) {
        this.status = status;
        this.message = message;
        this.data = data;
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

    public List<AdminOrdersDetails> getData() {
        return data;
    }

    public void setData(List<AdminOrdersDetails> data) {
        this.data = data;
    }
}
