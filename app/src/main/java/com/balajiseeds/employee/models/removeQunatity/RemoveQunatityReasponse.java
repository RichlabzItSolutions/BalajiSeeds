package com.balajiseeds.employee.models.removeQunatity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveQunatityReasponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("cartId")
    @Expose
    private String cartId;


    public RemoveQunatityReasponse(String status, String message, String cartId) {
        this.status = status;
        this.message = message;
        this.cartId = cartId;
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
}
