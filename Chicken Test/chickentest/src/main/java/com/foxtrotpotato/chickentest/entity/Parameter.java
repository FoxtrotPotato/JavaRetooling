package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

@Entity
@Table(name="parameters")
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="parameter_id")
    private int parameterId;

    @Column(name="parameter_name")
    private String parameterName;

    @Column(name="parameter_value")
    private int parameterValue;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "farm_id")
    private Farm farm;

    public Parameter(){}

    public Parameter(String parameterName, int parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public int getParameterId() {
        return parameterId;
    }

    public void setParameterId(int parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public int getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(int parameterValue) {
        this.parameterValue = parameterValue;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "parameterId=" + parameterId +
                ", parameterName='" + parameterName + '\'' +
                ", parameterValue=" + parameterValue +
                ", farm=" + farm +
                '}';
    }



}
