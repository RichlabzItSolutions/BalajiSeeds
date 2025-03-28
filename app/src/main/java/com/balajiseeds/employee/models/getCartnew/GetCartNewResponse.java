package com.balajiseeds.employee.models.getCartnew;

import com.balajiseeds.employee.models.ProductsVarietiesWithProducts.ProductVarietiesData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCartNewResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cart")
    @Expose
    private List<GetCartDetails> cart;

    @SerializedName("totalItems")
    @Expose
    private String totalItems;


    public GetCartNewResponse(String status, String message, List<GetCartDetails> cart, String totalItems) {
        this.status = status;
        this.message = message;
        this.cart = cart;
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

    public List<GetCartDetails> getCart() {
        return cart;
    }

    public void setCart(List<GetCartDetails> cart) {
        this.cart = cart;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }
}
