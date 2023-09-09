package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "chickens")
public class Chicken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chicken_id")
    private int chickenId;

    @Column(name = "chicken_birthday")
    private LocalDate chickenBirthDay;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @Transient
    private int ageInDays;

    public Chicken() {
    }

    public Chicken(LocalDate chickenBirthDay) {
        this.chickenBirthDay = chickenBirthDay;
    }

    public int getChickenId() {
        return chickenId;
    }

    public void setChickenId(int chickenId) {
        this.chickenId = chickenId;
    }

    public LocalDate getChickenBirthDay() {
        return chickenBirthDay;
    }

    public void setChickenBirthDay(LocalDate chickenBirthDay) {
        this.chickenBirthDay = chickenBirthDay;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public int getAgeInDays() {
        return ageInDays;
    }

    public void setAgeInDays(int ageInDays) {
        this.ageInDays = ageInDays;
    }

    @Override
    public String toString() {
        return "Chicken{" +
                "chickenId=" + chickenId +
                ", chickenBirthDay=" + chickenBirthDay +
                ", product=" + product +
                ", farm=" + farm +
                '}';
    }
}
