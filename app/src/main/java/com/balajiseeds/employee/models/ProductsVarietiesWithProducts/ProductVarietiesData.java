package com.balajiseeds.employee.models.ProductsVarietiesWithProducts;

import com.balajiseeds.employee.models.ModelActivity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductVarietiesData {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("partyId")
    @Expose
    private String partyId;

    @SerializedName("productCrop")
    @Expose
    private String productCrop;
    @SerializedName("productCode")
    @Expose
    private String productCode;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("createdOn")
    @Expose
    private String createdOn;

    @SerializedName("productImage")
    @Expose
    private String productImage;

    @SerializedName("packagingOptions")
    @Expose
    private List<PackagingOptions> packagingOptions;



  /*  @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("distributorPrice")
    @Expose
    private String distributorPrice;
    @SerializedName("productVariety")
    @Expose
    private String productVariety;

    @SerializedName("packagingSize")
    @Expose
    private String packagingSize;

    @SerializedName("images")
    @Expose
    private List<ProductVarietiesPhoto> images;*/



    @SerializedName("delete")
    @Expose
    private List<String> delete;




    public ProductVarietiesData(String productCrop, String productCode, String description, String mrp, String distributorPrice, String packagingSize) {
        this.productCrop = productCrop;
        this.productCode = productCode;
        this.description = description;
     /*   this.mrp = mrp;
        this.distributorPrice = distributorPrice;
        this.packagingSize = packagingSize;*/
    }


    public ProductVarietiesData(String id, String productCrop, String productCode, String description, String mrp, String distributorPrice, String packagingSize) {
        this.id = id;
        this.productCrop = productCrop;
        this.productCode = productCode;
        this.description = description;
        /*this.mrp = mrp;
        this.distributorPrice = distributorPrice;
        this.packagingSize = packagingSize;*/
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
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

   /* public String getMrp() {
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

    public String getPackagingSize() {
        return packagingSize;
    }

    public void setPackagingSize(String packagingSize) {
        this.packagingSize = packagingSize;
    }*/

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getDelete() {
        return delete;
    }

    public void setDelete(List<String> delete) {
        this.delete = delete;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /*public List<ProductVarietiesPhoto> getImages() {
        return images;
    }

    public void setImages(List<ProductVarietiesPhoto> images) {
        this.images = images;
    }*/

    public List<PackagingOptions> getPackagingOptions() {
        return packagingOptions;
    }

    public void setPackagingOptions(List<PackagingOptions> packagingOptions) {
        this.packagingOptions = packagingOptions;
    }
}
