package com.balajiseeds.admin.models.PaidHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaidHistoryJson {

    @SerializedName("orderId")
    @Expose
    private String orderId;


    public PaidHistoryJson(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
