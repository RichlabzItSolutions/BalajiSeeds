package com.balajiseeds.employee.models.ProductsVarietiesWithProducts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductVarietiesPhoto {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;


    public ProductVarietiesPhoto(String id, String productId, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.imageUrl = imageUrl;
    }


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
