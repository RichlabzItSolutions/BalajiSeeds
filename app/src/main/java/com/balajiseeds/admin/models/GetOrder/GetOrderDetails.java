package com.balajiseeds.admin.models.GetOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderDetails {


    @SerializedName("orderId")
    @Expose
    String orderId;
    @SerializedName("orderNum")
    @Expose
    String orderNum;

    @SerializedName("totalItems")
    @Expose
    String totalItems;

    @SerializedName("orderDate")
    @Expose
    String orderDate;

    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("mobile")
    @Expose
    String mobile;
    @SerializedName("state_name")
    @Expose
    String state_name;

    @SerializedName("city_name")
    @Expose
    String city_name;


    public GetOrderDetails(String orderId, String orderNum, String totalItems, String orderDate, String status, String name, String mobile, String state_name, String city_name) {
        this.orderId = orderId;
        this.orderNum = orderNum;
        this.totalItems = totalItems;
        this.orderDate = orderDate;
        this.status = status;
        this.name = name;
        this.mobile = mobile;
        this.state_name = state_name;
        this.city_name = city_name;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
