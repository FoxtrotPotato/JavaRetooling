package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

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
    private Double balanceTotal;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public Balance(){}

    public Balance(String balanceType, Double balanceTotal, Transaction transaction, Farm farm) {
        this.balanceType = balanceType;
        this.balanceTotal = balanceTotal;
        this.transaction = transaction;
        this.farm = farm;
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

    public Double getBalanceTotal() {
        return balanceTotal;
    }

    public void setBalanceTotal(Double balanceTotal) {
        this.balanceTotal = balanceTotal;
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
                ", farm=" + farm +
                ", transaction=" + transaction +
                '}';
    }


}
