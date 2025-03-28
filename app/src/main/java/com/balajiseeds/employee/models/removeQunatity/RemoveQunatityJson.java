package com.balajiseeds.employee.models.removeQunatity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveQunatityJson {
    @SerializedName("cartId")
    @Expose
    private String cartId;


    public RemoveQunatityJson(String cartId) {
        this.cartId = cartId;
    }


    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
}
