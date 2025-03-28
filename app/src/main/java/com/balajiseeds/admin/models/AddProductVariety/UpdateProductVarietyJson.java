package com.balajiseeds.admin.models.AddProductVariety;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProductVarietyJson {

    @SerializedName("varietyId")
    @Expose
    private String varietyId;
    @SerializedName("varietyName")
    @Expose
    private String varietyName;
    @SerializedName("status")
    @Expose
    private String status;


    public UpdateProductVarietyJson(String varietyId, String varietyName, String status) {
        this.varietyId = varietyId;
        this.varietyName = varietyName;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
