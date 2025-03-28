package com.balajiseeds.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProductJson {

    @SerializedName("productCode")
    @Expose
    private String productCode;
    @SerializedName("productCrop")
    @Expose
    private String productCrop;

    @SerializedName("varietyId")
    @Expose
    private String varietyId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("distributorPrice")
    @Expose
    private String distributorPrice;

    @SerializedName("mrp")
    @Expose
    private String mrp;

    @SerializedName("packagingSize")
    @Expose
    private String packagingSize;


    public AddProductJson(String productCode, String productCrop, String varietyId, String description, String distributorPrice, String mrp, String packagingSize) {
        this.productCode = productCode;
        this.productCrop = productCrop;
        this.varietyId = varietyId;
        this.description = description;
        this.distributorPrice = distributorPrice;
        this.mrp = mrp;
        this.packagingSize = packagingSize;
    }


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCrop() {
        return productCrop;
    }

    public void setProductCrop(String productCrop) {
        this.productCrop = productCrop;
    }

    public String getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(String varietyId) {
        this.varietyId = varietyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistributorPrice() {
        return distributorPrice;
    }

    public void setDistributorPrice(String distributorPrice) {
        this.distributorPrice = distributorPrice;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getPackagingSize() {
        return packagingSize;
    }

    public void setPackagingSize(String packagingSize) {
        this.packagingSize = packagingSize;
    }

}
