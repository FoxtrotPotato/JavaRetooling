package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_details")
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_details_id")
    private int transactionDetailsId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private float price;

    @Column(name = "subtotal")
    private float subtotal;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public TransactionDetail() {
    }

    public TransactionDetail(Product product, int quantity, float price, float subtotal, Transaction transaction) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        this.transaction = transaction;
    }

    public int getTransactionDetailsId() {
        return transactionDetailsId;
    }

    public void setTransactionDetailsId(int transactionDetailsId) {
        this.transactionDetailsId = transactionDetailsId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float transactionTotal) {
        this.subtotal = transactionTotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "TransactionDetail{" +
                "transactionDetailsId=" + transactionDetailsId +
                ", quantity='" + quantity + '\'' +
                ", transactionTotal=" + subtotal +
                ", product=" + product.getProductName() +
                ", transaction=" + transaction.getTransactionId() +
                '}';
    }

}
