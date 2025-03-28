package com.balajiseeds.admin.models.UpdateAmount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAmountJson {

    @SerializedName("orderId")
    @Expose
    private String orderId;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("referenceId")
    @Expose
    private String referenceId;
    @SerializedName("paymentDate")
    @Expose
    private String paymentDate;


    public UpdateAmountJson(String orderId, String amount, String paymentMode, String referenceId, String paymentDate) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.referenceId = referenceId;
        this.paymentDate = paymentDate;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
