package com.balajiseeds.admin.models.UpdateAmount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAmountDetails {


    @SerializedName("orderId")
    @Expose
    private String orderId;

    @SerializedName("paymentId")
    @Expose
    private String paymentId;

    @SerializedName("paidAmount")
    @Expose
    private String paidAmount;
    @SerializedName("balanceAmount")
    @Expose
    private String balanceAmount;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;

    @SerializedName("referenceId")
    @Expose
    private String referenceId;
    @SerializedName("paymentDate")
    @Expose
    private String paymentDate;

    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;


    public UpdateAmountDetails(String orderId, String paymentId, String paidAmount, String balanceAmount, String paymentMode, String referenceId, String paymentDate, String totalAmount) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.paidAmount = paidAmount;
        this.balanceAmount = balanceAmount;
        this.paymentMode = paymentMode;
        this.referenceId = referenceId;
        this.paymentDate = paymentDate;
        this.totalAmount = totalAmount;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
