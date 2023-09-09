package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

@Entity
@Table(name="transaction_details")
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_details_id")
    private int transactionDetailsId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="quantity")
    private String quantity;

    @Column(name="subtotal")
    private Float transactionTotal;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public TransactionDetail(){}

    public TransactionDetail(String quantity, Float transactionTotal) {
        this.quantity = quantity;
        this.transactionTotal = transactionTotal;
    }

    public int getTransactionDetailsId() {
        return transactionDetailsId;
    }

    public void setTransactionDetailsId(int transactionDetailsId) {
        this.transactionDetailsId = transactionDetailsId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Float getTransactionTotal() {
        return transactionTotal;
    }

    public void setTransactionTotal(Float transactionTotal) {
        this.transactionTotal = transactionTotal;
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
                ", transactionTotal=" + transactionTotal +
                ", product=" + product +
                ", transaction=" + transaction +
                '}';
    }
}
