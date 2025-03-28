package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelCart {
    public static class addtoCart{
        @SerializedName("productId")
        @Expose
        private String productId;
        @SerializedName("quantity")
        @Expose
        private String quantity;

        public addtoCart(String productId, String quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }
    public static class deleteFromCart{
        @SerializedName("productId")
        @Expose
        private String productId;

        @SerializedName("partyId")
        @Expose
        private String partyId;



        public deleteFromCart(String productId, String partyId) {
            this.productId = productId;
            this.partyId = partyId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getPartyId() {
            return partyId;
        }

        public void setPartyId(String partyId) {
            this.partyId = partyId;
        }
    }

    public static class getCartResponse{
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("cart")
        @Expose
        private List<Cart> cartList;

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

        public List<Cart> getCartList() {
            return cartList;
        }

        public void setCartList(List<Cart> cartList) {
            this.cartList = cartList;
        }
    }
    public static class Cart{

        @SerializedName("cartid")
        @Expose
        private String cartid;
        @SerializedName("productId")
        @Expose
        private String productId;
        @SerializedName("qty")
        @Expose
        private String quantity;


        @SerializedName("productCrop")
        @Expose
        private String productName;

        @SerializedName("productVariety")
        @Expose
        private String productVariety;

        @SerializedName("image_url")
        @Expose
        private String image_url;

        @SerializedName("price")
        @Expose
        private String price;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }


        public String getCartid() {
            return cartid;
        }

        public void setCartid(String cartid) {
            this.cartid = cartid;
        }

        public String getProductVariety() {
            return productVariety;
        }

        public void setProductVariety(String productVariety) {
            this.productVariety = productVariety;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
