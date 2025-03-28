package com.balajiseeds.admin.models.GetOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelOrderJson {


    @SerializedName("orderId")
    @Expose
    String orderId;


    public CancelOrderJson(String orderId) {
        this.orderId = orderId;

    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


}
