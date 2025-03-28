package com.balajiseeds.employee.models.ProductsVarietiesWithProducts;

import com.balajiseeds.employee.models.ModelsProducts;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductsVarietiesWithProductsReasponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("partyId")
    @Expose
    private String partyId;

    @SerializedName("totalItems")
    @Expose
    private String totalItems;

    @SerializedName("data")

    @Expose
    private List<ProductVarietiesData> data;

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


    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public List<ProductVarietiesData> getData() {
        return data;
    }

    public void setData(List<ProductVarietiesData> data) {
        this.data = data;
    }


}
