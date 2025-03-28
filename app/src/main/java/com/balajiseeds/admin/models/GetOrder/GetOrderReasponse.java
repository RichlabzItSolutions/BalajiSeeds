package com.balajiseeds.admin.models.GetOrder;

import com.balajiseeds.employee.models.ModelOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderReasponse {

    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("orders")
    @Expose
    List<GetOrderDetails> orderList;

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

    public List<GetOrderDetails> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<GetOrderDetails> orderList) {
        this.orderList = orderList;
    }

}
