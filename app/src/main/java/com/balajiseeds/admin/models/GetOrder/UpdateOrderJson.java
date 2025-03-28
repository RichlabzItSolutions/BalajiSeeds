package com.balajiseeds.admin.models.GetOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateOrderJson {


    @SerializedName("orderId")
    @Expose
    String orderId;
    @SerializedName("orderStatus")
    @Expose
    String orderStatus;


    public UpdateOrderJson(String orderId, String orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;

    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


}
