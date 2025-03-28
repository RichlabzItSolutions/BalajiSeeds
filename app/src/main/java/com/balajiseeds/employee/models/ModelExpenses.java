package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelExpenses {
    public static class Expenses {
        //id,expenseId,amount,date,expensesDescription
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("expenseId")
        @Expose
        public String expenseId;
        @SerializedName("amount")
        @Expose
        public String amount;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("expenseDescription")
        @Expose
        public String expensesDescription;
        @SerializedName("empId")
        @Expose
        public String empId;
        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("receipt")
        @Expose
        public String receipt;


        public Expenses(String id) {
            this.id = id;
        }

        public Expenses(String expenseId, String amount, String date, String expensesDescription) {
            this.expenseId = expenseId;
            this.amount = amount;
            this.date = date;
            this.expensesDescription = expensesDescription;
        }

        public Expenses(String id, String expenseId, String amount, String date, String expensesDescription) {
            this.id = id;
            this.expenseId = expenseId;
            this.amount = amount;
            this.date = date;
            this.expensesDescription = expensesDescription;
        }

        public String getReceipt() {
            return receipt;
        }

        public void setReceipt(String receipt) {
            this.receipt = receipt;
        }

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getExpenseId() {
            return expenseId;
        }

        public void setExpenseId(String expenseId) {
            this.expenseId = expenseId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getExpensesDescription() {
            return expensesDescription;
        }

        public void setExpensesDescription(String expensesDescription) {
            this.expensesDescription = expensesDescription;
        }
    }

    public static class ExpensesHead {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("expenseHead")
        @Expose
        public String expenseHead;

        @SerializedName("narration")
        @Expose
        private String narration;

        @SerializedName("status")
        @Expose
        public String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getExpenseHead() {
            return expenseHead;
        }

        public void setExpenseHead(String expenseHead) {
            this.expenseHead = expenseHead;
        }


        public String getNarration() {
            return narration;
        }

        public void setNarration(String narration) {
            this.narration = narration;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ExpensesHeadsResponse {
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("expensesHeads")
        @Expose
        public List<ExpensesHead> data;

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

        public List<ExpensesHead> getData() {
            return data;
        }

        public void setData(List<ExpensesHead> data) {
            this.data = data;
        }
    }

    public static class getExpensesRequest {
        @SerializedName("fromDate")
        @Expose
        private String fromDate;
        @SerializedName("toDate")
        @Expose
        private String toDate;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("searchStr")
        @Expose
        private String searchStr;

        public getExpensesRequest(String fromDate, String toDate) {
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        public getExpensesRequest(String fromDate, String toDate, String state, String city, String searchStr) {
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.state = state;
            this.city = city;
            this.searchStr = searchStr;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSearchStr() {
            return searchStr;
        }

        public void setSearchStr(String searchStr) {
            this.searchStr = searchStr;
        }

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }
    }

    public static class getExpensesResponse {
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("expenses")
        @Expose
        public List<Expenses> data;

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

        public List<Expenses> getData() {
            return data;
        }

        public void setData(List<Expenses> data) {
            this.data = data;
        }
    }

}
