package com.balajiseeds.admin.models.AddProductVariety;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductVarietyDetails {

    @SerializedName("varietyId")
    @Expose
    private String varietyId;
    @SerializedName("varietyName")
    @Expose
    private String varietyName;


    public GetProductVarietyDetails(String varietyId, String varietyName) {
        this.varietyId = varietyId;
        this.varietyName = varietyName;
    }

    public String getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(String varietyId) {
        this.varietyId = varietyId;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }
}
