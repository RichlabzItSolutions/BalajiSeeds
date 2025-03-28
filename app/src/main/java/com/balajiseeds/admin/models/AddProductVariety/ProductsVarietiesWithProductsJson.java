package com.balajiseeds.admin.models.AddProductVariety;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductsVarietiesWithProductsJson {

    @SerializedName("varietyId")
    @Expose
    private String varietyId;

    @SerializedName("partyId")
    @Expose
    private String partyId;


    public ProductsVarietiesWithProductsJson(String varietyId, String partyId) {
        this.varietyId = varietyId;
        this.partyId = partyId;
    }


    public String getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(String varietyId) {
        this.varietyId = varietyId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
