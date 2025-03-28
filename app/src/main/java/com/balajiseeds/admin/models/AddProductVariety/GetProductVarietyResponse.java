package com.balajiseeds.admin.models.AddProductVariety;

import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductVarietyResponse {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<GetProductVarietyDetails> data;


    public GetProductVarietyResponse(String status, List<GetProductVarietyDetails> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GetProductVarietyDetails> getData() {
        return data;
    }

    public void setData(List<GetProductVarietyDetails> data) {
        this.data = data;
    }
}
