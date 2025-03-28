package com.balajiseeds.employee.models.MyOrders;

import com.balajiseeds.employee.models.getCartnew.GetCartDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmpOrdersResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<EmpOrdersDetails> data;


    public EmpOrdersResponse(String status, List<EmpOrdersDetails> data) {
        this.status = status;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EmpOrdersDetails> getData() {
        return data;
    }

    public void setData(List<EmpOrdersDetails> data) {
        this.data = data;
    }
}
