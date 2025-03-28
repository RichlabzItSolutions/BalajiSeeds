package com.balajiseeds.employee.models.placeOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrderReasponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("orderNumber")
    @Expose
    private String orderNumber;

    @SerializedName("totalItems")
    @Expose
    private String totalItems;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;






    public PlaceOrderReasponse(String status, String message, String orderId, String orderNumber, String totalItems, String totalAmount) {
        this.status = status;
        this.message = message;
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.totalItems = totalItems;
        this.totalAmount = totalAmount;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
