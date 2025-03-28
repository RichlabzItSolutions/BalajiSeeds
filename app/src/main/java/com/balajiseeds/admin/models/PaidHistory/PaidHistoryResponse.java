package com.balajiseeds.admin.models.PaidHistory;

import com.balajiseeds.admin.models.AdminOrders.AdminOrdersDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaidHistoryResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<PaidHistoryDetails> data;


    public PaidHistoryResponse(String status, List<PaidHistoryDetails> data) {
        this.status = status;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PaidHistoryDetails> getData() {
        return data;
    }

    public void setData(List<PaidHistoryDetails> data) {
        this.data = data;
    }
}
