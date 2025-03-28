package com.balajiseeds.admin.models.AdminOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminOrdersDetails {


    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("orderNumber")
    @Expose
    private String orderNumber;


    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;


    @SerializedName("totalItems")
    @Expose
    private String totalItems;

    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;



    @SerializedName("partyName")
    @Expose
    private String partyName;
    @SerializedName("partyCode")
    @Expose
    private String partyCode;


    @SerializedName("totalPaidAmount")
    @Expose
    private String totalPaidAmount;
    @SerializedName("balanceAmount")
    @Expose
    private String balanceAmount;


    public AdminOrdersDetails(String orderId, String orderNumber, String orderDate, String totalAmount, String totalItems, String orderStatus, String partyName, String partyCode, String totalPaidAmount, String balanceAmount) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
        this.orderStatus = orderStatus;
        this.partyName = partyName;
        this.partyCode = partyCode;
        this.totalPaidAmount = totalPaidAmount;
        this.balanceAmount = balanceAmount;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(String totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
