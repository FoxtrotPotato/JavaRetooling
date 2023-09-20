package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="farms")
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="farm_id")
    private int farmId;

    @Column(name="farm_name")
    private String farmName;

    @OneToMany(mappedBy = "farm")
    private List<User> users;

    public Farm(){}

    public Farm(String farmName) {
        this.farmName = farmName;
    }

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Farm{" +
                "farmId=" + farmId +
                ", farmName='" + farmName + '\'' +
                '}';
    }

}
