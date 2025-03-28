package com.balajiseeds.employee.models.ProductsVarietiesWithProducts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackagingOptions {

    @SerializedName("packagingSize")
    @Expose
    private String packagingSize;

    @SerializedName("mrp")
    @Expose
    private String mrp;

    @SerializedName("distributorPrice")
    @Expose
    private String distributorPrice;

    @SerializedName("stateId")
    @Expose
    private String stateId;


    public PackagingOptions(String packagingSize, String mrp, String distributorPrice, String stateId) {
        this.packagingSize = packagingSize;
        this.mrp = mrp;
        this.distributorPrice = distributorPrice;
        this.stateId = stateId;
    }


    public String getPackagingSize() {
        return packagingSize;
    }

    public void setPackagingSize(String packagingSize) {
        this.packagingSize = packagingSize;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getDistributorPrice() {
        return distributorPrice;
    }

    public void setDistributorPrice(String distributorPrice) {
        this.distributorPrice = distributorPrice;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }
}
