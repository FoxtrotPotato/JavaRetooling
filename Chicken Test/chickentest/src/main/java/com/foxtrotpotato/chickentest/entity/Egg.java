package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="eggs")
public class Egg {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="egg_id")
    private int eggId;

    @Temporal(TemporalType.DATE)
    @Column(name="egg_birthday")
    private LocalDate eggBirthDay;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @Transient
    private int ageInDays;

    public Egg(){}

    public Egg(LocalDate eggBirthDay, Farm farm, Product product) {
        this.eggBirthDay = eggBirthDay;
        this.farm = farm;
        this.product = product;
    }

    public int getEggId() {
        return eggId;
    }

    public void setEggId(int eggId) {
        this.eggId = eggId;
    }

    public LocalDate getEggBirthDay() {
        return eggBirthDay;
    }

    public void setEggBirthDay(LocalDate eggBirthDay) {
        this.eggBirthDay = eggBirthDay;
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
        return "Egg{" +
                "eggId=" + eggId +
                ", eggBirthDay=" + eggBirthDay +
                ", product=" + product +
                ", farm=" + farm +
                '}';
    }
}
