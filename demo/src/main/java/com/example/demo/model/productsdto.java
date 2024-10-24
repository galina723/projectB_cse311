package com.example.demo.model;
import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

public class productsdto {

    private int ProductId;
    private String ProductName;
    private String ProductCategory;
    private String ProductDescription;
    private MultipartFile ProductMainImage;
    private MultipartFile ProductOtherImages;
    private double ProductPrice;
    private int ProductQuantity;
    private Date CreateTime;
    public int getProductId() {
        return ProductId;
    }
    public void setProductId(int productId) {
        ProductId = productId;
    }
    public String getProductName() {
        return ProductName;
    }
    public void setProductName(String productName) {
        ProductName = productName;
    }
    public String getProductCategory() {
        return ProductCategory;
    }
    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }
    public String getProductDescription() {
        return ProductDescription;
    }
    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }
    public MultipartFile getProductMainImage() {
        return ProductMainImage;
    }
    public void setProductMainImage(MultipartFile productMainImage) {
        ProductMainImage = productMainImage;
    }
    public MultipartFile getProductOtherImages() {
        return ProductOtherImages;
    }
    public void setProductOtherImages(MultipartFile productOtherImages) {
        ProductOtherImages = productOtherImages;
    }
    public double getProductPrice() {
        return ProductPrice;
    }
    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }
    public int getProductQuantity() {
        return ProductQuantity;
    }
    public void setProductQuantity(int productQuantity) {
        ProductQuantity = productQuantity;
    }
    public Date getCreateTime() {
        return CreateTime;
    }
    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    
}
