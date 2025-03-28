package com.balajiseeds.admin.models.PaidHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaidHistoryDetails {


    @SerializedName("paymentDate")
    @Expose
    private String paymentDate;
    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("referenceId")
    @Expose
    private String referenceId;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;


    public PaidHistoryDetails(String paymentDate, String amount, String referenceId, String paymentMode) {
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.referenceId = referenceId;
        this.paymentMode = paymentMode;
    }


    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}
