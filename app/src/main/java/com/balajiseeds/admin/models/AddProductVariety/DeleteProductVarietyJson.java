package com.balajiseeds.admin.models.AddProductVariety;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteProductVarietyJson {

    @SerializedName("varietyId")
    @Expose
    private String varietyId;

    public DeleteProductVarietyJson(String varietyId) {
        this.varietyId = varietyId;
    }


    public String getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(String varietyId) {
        this.varietyId = varietyId;
    }
}
