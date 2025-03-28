package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelProducts {


    public static class Product {



        @SerializedName("productId")
        @Expose
        private String productId;

        @SerializedName("cartId")
        @Expose
        private String cartId;



        @SerializedName("unitPrice")
        @Expose
        private String unitPrice;



        @SerializedName("image_url")
        @Expose
        private String image_url;


        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("skuCode")
        @Expose
        private String skuCode;
        @SerializedName("productTitle")
        @Expose
        private String productTitle;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("mrp")
        @Expose
        private String mrp;
        @SerializedName("productVariety")
        @Expose
        private String productVariety;
        @SerializedName("sellingPrice")
        @Expose
        private String sellingPrice;
        @SerializedName("packagingSize")
        @Expose
        private String packagingSize;
        @SerializedName("images")
        @Expose
        private List<ModelActivity.Photo> images;
        @SerializedName("delete")
        @Expose
        private List<String> delete;
        @SerializedName("qty")
        @Expose
        private int qty;



        public Product(String skuCode, String productTitle, String description, String mrp, String sellingPrice, String packagingSize) {
            this.skuCode = skuCode;
            this.productTitle = productTitle;
            this.description = description;
            this.mrp = mrp;
            this.sellingPrice = sellingPrice;
            this.packagingSize = packagingSize;
        }

        public Product(String id, String skuCode, String productTitle, String description, String mrp, String sellingPrice, String packagingSize) {
            this.id = id;
            this.skuCode = skuCode;
            this.productTitle = productTitle;
            this.description = description;
            this.mrp = mrp;
            this.sellingPrice = sellingPrice;
            this.packagingSize = packagingSize;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public List<String> getDelete() {
            return delete;
        }

        public void setDelete(List<String> delete) {
            this.delete = delete;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getProductVariety() {
            return productVariety;
        }

        public void setProductVariety(String productVariety) {
            this.productVariety = productVariety;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(String sellingPrice) {
            this.sellingPrice = sellingPrice;
        }

        public String getPackagingSize() {
            return packagingSize;
        }

        public void setPackagingSize(String packagingSize) {
            this.packagingSize = packagingSize;
        }

        public List<ModelActivity.Photo> getImages() {
            return images;
        }


        public void setImages(List<ModelActivity.Photo> images) {
            this.images = images;
        }


        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }

    public static class Image {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("productId")
        @Expose
        private String productId;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

    }

    public static class deleteProduct {
        @SerializedName("id")
        @Expose
        private String id;

        public deleteProduct(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class GetProductResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<Product> data;

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

        public List<Product> getData() {
            return data;
        }

        public void setData(List<Product> data) {
            this.data = data;
        }

    }
    public static class GetProductRequest {
        @SerializedName("searchStr")
        @Expose
        private String searchStr;

        public GetProductRequest(String searchStr) {
            this.searchStr = searchStr;
        }

        public String getSearchStr() {
            return searchStr;
        }

        public void setSearchStr(String searchStr) {
            this.searchStr = searchStr;
        }
    }
    public static class getProductByIdRequest{
        @SerializedName("productId")
        @Expose
        private String productId;

        public getProductByIdRequest(String productId) {
            this.productId = productId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }
    }
    public static class getProductByIdResponse{
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private Product data;

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

        public Product getData() {
            return data;
        }

        public void setData(Product data) {
            this.data = data;
        }
    }
}
