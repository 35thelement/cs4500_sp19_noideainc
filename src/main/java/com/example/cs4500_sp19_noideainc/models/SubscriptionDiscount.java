package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "subscription_discount")
public class SubscriptionDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private float discount;
    private Frequency frequency;
    private boolean flat;
    private float flatAmount;
    private float basePrice;
    
 // the constructor for SubscriptionDiscount
    public SubscriptionDiscount(float discount, Frequency frequency, boolean flat) {
    	this.discount = discount;
    	this.frequency = frequency;
    	this.flat = flat;
    	
    }

    public float getDiscount() {
    	return discount;
        
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public boolean isFlat() {
        return flat;
    }

    public void setFlat(boolean flat) {
        this.flat = flat;
    }

    public float getFlatAmount() {
        return flatAmount;
    }

    public void setFlatAmount(int flatAmount) {
        this.flatAmount = flatAmount;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }
}