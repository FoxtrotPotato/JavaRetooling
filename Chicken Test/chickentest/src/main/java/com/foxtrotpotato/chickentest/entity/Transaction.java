package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_id")
    private int transactionId;

    @Column(name="transaction_date")
    private LocalDateTime transactionDate;

    @Column(name="transaction_total")
    private Float transactionTotal;

    @Column(name="transaction_observations")
    private String transactionObservations;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private List<TransactionDetail> transactionDetails;

    public Transaction(){}

    public Transaction(LocalDateTime transactionDate, Float transactionTotal, String transactionObservations) {
        this.transactionDate = transactionDate;
        this.transactionTotal = transactionTotal;
        this.transactionObservations = transactionObservations;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Float getTransactionTotal() {
        return transactionTotal;
    }

    public void setTransactionTotal(Float transactionTotal) {
        this.transactionTotal = transactionTotal;
    }

    public String getTransactionObservations() {
        return transactionObservations;
    }

    public void setTransactionObservations(String transactionObservations) {
        this.transactionObservations = transactionObservations;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public List<TransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public void addTransactionDetails(TransactionDetail theTransactionDetail) {
        if (transactionDetails == null) {
            transactionDetails = new ArrayList<>();
        }
        transactionDetails.add(theTransactionDetail);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", transactionDate='" + transactionDate + '\'' +
                ", transactionTotal=" + transactionTotal +
                ", transactionObservation='" + transactionObservations + '\'' +
                ", farm=" + farm +
                ", transactionDetails=" + transactionDetails +
                '}';
    }
}











