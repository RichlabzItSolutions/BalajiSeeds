package com.balajiseeds.employee.models.addCartnew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCartNewResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("cartId")
    @Expose
    private String cartId;
    @SerializedName("totalItems")
    @Expose
    private String totalItems;


    public AddCartNewResponse(String status, String message, String cartId, String totalItems) {
        this.status = status;
        this.message = message;
        this.cartId = cartId;
        this.totalItems = totalItems;
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

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }
}
