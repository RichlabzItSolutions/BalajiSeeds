package com.balajiseeds.admin.models.AddProductVariety;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProductVarietyJson {

    @SerializedName("varietyName")
    @Expose
    private String varietyName;
    @SerializedName("status")
    @Expose
    private String status;

    public AddProductVarietyJson(String varietyName, String status) {
        this.varietyName = varietyName;
        this.status = status;
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
