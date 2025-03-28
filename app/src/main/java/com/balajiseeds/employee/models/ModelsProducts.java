package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelsProducts {


    public static class Product {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("productCrop")
        @Expose
        private String productCrop;
        @SerializedName("productCode")
        @Expose
        private String productCode;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("mrp")
        @Expose
        private String mrp;
        @SerializedName("distributorPrice")
        @Expose
        private String distributorPrice;
        @SerializedName("productVariety")
        @Expose
        private String productVariety;

        @SerializedName("createdOn")
        @Expose
        private String createdOn;
        @SerializedName("packagingSize")
        @Expose
        private String packagingSize;

        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("delete")
        @Expose
        private List<String> delete;

        @SerializedName("images")
        @Expose
        private List<ModelActivity.Photo> images;




        public Product(String productCrop, String productCode, String description, String mrp, String distributorPrice, String packagingSize) {
            this.productCrop = productCrop;
            this.productCode = productCode;
            this.description = description;
            this.mrp = mrp;
            this.distributorPrice = distributorPrice;
            this.packagingSize = packagingSize;
        }

       /* public Product(String productCrop, String productCode, String productVariety, String description, String mrp, String distributorPrice, String packagingSize) {
            this.productCrop = productCrop;
            this.productCode = productCode;
            this.productVariety = productVariety;
            this.description = description;
            this.mrp = mrp;
            this.distributorPrice = distributorPrice;
            this.packagingSize = packagingSize;
        }*/

        public Product(String id, String productCrop, String productCode, String description, String mrp, String distributorPrice, String packagingSize) {
            this.id = id;
            this.productCrop = productCrop;
            this.productCode = productCode;
            this.description = description;
            this.mrp = mrp;
            this.distributorPrice = distributorPrice;
            this.packagingSize = packagingSize;
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

        public String getProductCrop() {
            return productCrop;
        }

        public void setProductCrop(String productCrop) {
            this.productCrop = productCrop;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
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

        public String getDistributorPrice() {
            return distributorPrice;
        }

        public void setDistributorPrice(String distributorPrice) {
            this.distributorPrice = distributorPrice;
        }

        public String getProductVariety() {
            return productVariety;
        }

        public void setProductVariety(String productVariety) {
            this.productVariety = productVariety;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getPackagingSize() {
            return packagingSize;
        }

        public void setPackagingSize(String packagingSize) {
            this.packagingSize = packagingSize;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<ModelActivity.Photo> getImages() {
            return images;
        }

        public void setImages(List<ModelActivity.Photo> images) {
            this.images = images;
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

    public static class AddProduct {

        @SerializedName("productCode")
        @Expose
        private String productCode;
        @SerializedName("productCrop")
        @Expose
        private String productCrop;
        @SerializedName("productVariety")
        @Expose
        private String productVariety;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("distributorPrice")
        @Expose
        private String distributorPrice;
        @SerializedName("mrp")
        @Expose
        private String mrp;
        @SerializedName("packagingSize")
        @Expose
        private String packagingSize;

        @SerializedName("delete")
        @Expose
        private List<String> delete;


        public AddProduct(String productCode, String productCrop, String productVariety, String description, String distributorPrice, String mrp, String packagingSize) {
            this.productCode = productCode;
            this.productCrop = productCrop;
            this.productVariety = productVariety;
            this.description = description;
            this.distributorPrice = distributorPrice;
            this.mrp = mrp;
            this.packagingSize = packagingSize;
        }


        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductCrop() {
            return productCrop;
        }

        public void setProductCrop(String productCrop) {
            this.productCrop = productCrop;
        }

        public String getProductVariety() {
            return productVariety;
        }

        public void setProductVariety(String productVariety) {
            this.productVariety = productVariety;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDistributorPrice() {
            return distributorPrice;
        }

        public void setDistributorPrice(String distributorPrice) {
            this.distributorPrice = distributorPrice;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getPackagingSize() {
            return packagingSize;
        }

        public void setPackagingSize(String packagingSize) {
            this.packagingSize = packagingSize;
        }

        public List<String> getDelete() {
            return delete;
        }

        public void setDelete(List<String> delete) {
            this.delete = delete;
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
