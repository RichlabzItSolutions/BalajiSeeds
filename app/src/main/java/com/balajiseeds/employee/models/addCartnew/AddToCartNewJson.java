package com.balajiseeds.employee.models.addCartnew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCartNewJson {

    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("partyId")
    @Expose
    private String partyId;

    @SerializedName("packagingSize")
    @Expose
    private String packagingSize;



    public AddToCartNewJson(String productId, String quantity, String partyId, String packagingSize) {
        this.productId = productId;
        this.quantity = quantity;
        this.partyId = partyId;
        this.packagingSize = packagingSize;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }


    public String getPackagingSize() {
        return packagingSize;
    }

    public void setPackagingSize(String packagingSize) {
        this.packagingSize = packagingSize;
    }
}
