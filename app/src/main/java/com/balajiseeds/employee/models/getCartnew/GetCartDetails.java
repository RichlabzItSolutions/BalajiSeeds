package com.balajiseeds.employee.models.getCartnew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCartDetails {


    @SerializedName("partyId")
    @Expose
    private String partyId;
    @SerializedName("cartId")
    @Expose
    private String cartId;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productVariety")
    @Expose
    private String productVariety;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("unit_price")
    @Expose
    private String unit_price;
    @SerializedName("total_amount")
    @Expose
    private String total_amount;

    @SerializedName("packagingSize")
    @Expose
    private String packagingSize;

    @SerializedName("packagingName")
    @Expose
    private String packagingName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("productImage")
    @Expose
    private String productImage;




    public GetCartDetails(String partyId, String cartId, String productId, String productName, String productVariety, String qty, String unit_price, String total_amount, String packagingSize, String packagingName, String status, String created_at, String productImage) {
        this.partyId = partyId;
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.productVariety = productVariety;
        this.qty = qty;
        this.unit_price = unit_price;
        this.total_amount = total_amount;
        this.packagingSize = packagingSize;
        this.packagingName = packagingName;
        this.status = status;
        this.created_at = created_at;
        this.productImage = productImage ;
    }


    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVariety() {
        return productVariety;
    }

    public void setProductVariety(String productVariety) {
        this.productVariety = productVariety;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }




    public String getPackagingSize() {
        return packagingSize;
    }

    public void setPackagingSize(String packagingSize) {
        this.packagingSize = packagingSize;
    }

    public String getPackagingName() {
        return packagingName;
    }

    public void setPackagingName(String packagingName) {
        this.packagingName = packagingName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
