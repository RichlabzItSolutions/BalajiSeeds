package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelOrder {
    public static class addOrderRequest{
        //customerName,number,state,city,pinCode,address
        @SerializedName("customerName")
        @Expose
        String customerName;
        @SerializedName("number")
        @Expose
        String number;
        @SerializedName("state")
        @Expose
        String state;
        @SerializedName("city")
        @Expose
        String city;
        @SerializedName("pinCode")
        @Expose
        String pinCode;
        @SerializedName("address")
        @Expose
        String address;

        public addOrderRequest(String customerName, String number, String state, String city, String pinCode, String address) {
            this.customerName = customerName;
            this.number = number;
            this.state = state;
            this.city = city;
            this.pinCode = pinCode;
            this.address = address;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPinCode() {
            return pinCode;
        }

        public void setPinCode(String pinCode) {
            this.pinCode = pinCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
    public static class fetchOrderResponse{
        @SerializedName("status")
        @Expose
        String status;
        @SerializedName("message")
        @Expose
        String message;
        @SerializedName("orders")
        @Expose
        List<Order> orderList;

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

        public List<Order> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<Order> orderList) {
            this.orderList = orderList;
        }
    }

    public static class Order{
        //id,orderId,customerName,number,state,city,orderDate,orderStatus,noOfItems,products


        @SerializedName("orderNum")
        @Expose
        String orderNum;
        @SerializedName("totalAmount")
        @Expose
        String totalAmount;
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


        @SerializedName("id")
        @Expose
        String id;
        @SerializedName("orderId")
        @Expose
        String orderId;
        @SerializedName("orderNumber")
        @Expose
        String orderNumber;
        @SerializedName("totalItems")
        @Expose
        String totalItems;
        @SerializedName("orderDate")
        @Expose
        String orderDate;
        @SerializedName("status")
        @Expose
        String status;

        @SerializedName("products")
        @Expose
        List<ModelProducts.Product> products;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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


        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
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

        public List<ModelProducts.Product> getProducts() {
            return products;
        }

        public void setProducts(List<ModelProducts.Product> products) {
            this.products = products;
        }
    }
    public static class OrderByIdRequest{
        @SerializedName("orderId")
        @Expose
        String orderId;

        public OrderByIdRequest(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}
