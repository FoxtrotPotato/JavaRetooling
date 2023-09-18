package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private int productId;

    @Column(name="product_name")
    private String productName;

    @Column(name="product_value")
    private float productValue;

    @Column(name="product_stock")
    private int productStock;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "farm_id")
    private Farm farm;

    public Product(){}

    public Product(String productName, float productValue, int productStock) {
        this.productName = productName;
        this.productValue = productValue;
        this.productStock = productStock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductValue() {
        return productValue;
    }

    public void setProductValue(float productValue) {
        this.productValue = productValue;
    }

    public float getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productValue=" + productValue +
                ", productStock=" + productStock +
                ", farm=" + farm +
                '}';
    }
}
