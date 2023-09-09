package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;
import org.hibernate.engine.transaction.internal.TransactionImpl;

@Entity
@Table(name="balances")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="balance_id")
    private int balanceId;

    @Column(name="balance_type")
    private String balanceType;

    @Column(name="balance_total")
    private Float balanceTotal;

    @Column(name="balance_observations")
    private String balanceObservation;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public Balance(){}

    public Balance(String balanceType, Float balanceTotal, String balanceObservation) {
        this.balanceType = balanceType;
        this.balanceTotal = balanceTotal;
        this.balanceObservation = balanceObservation;
    }

    public int getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public Float getBalanceTotal() {
        return balanceTotal;
    }

    public void setBalanceTotal(Float balanceTotal) {
        this.balanceTotal = balanceTotal;
    }

    public String getBalanceObservation() {
        return balanceObservation;
    }

    public void setBalanceObservation(String balanceObservation) {
        this.balanceObservation = balanceObservation;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "balanceId=" + balanceId +
                ", balanceType='" + balanceType + '\'' +
                ", balanceTotal=" + balanceTotal +
                ", balanceObservation='" + balanceObservation + '\'' +
                ", farm=" + farm +
                ", transaction=" + transaction +
                '}';
    }


}
