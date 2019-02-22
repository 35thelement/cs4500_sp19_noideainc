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

    public float getDiscount(Frequency frequency, boolean flat, float flatAmount, float basePrice) {
        if (flatAmount < 0) {
            throw new RuntimeException("Flat amount cannot be negative.");
        }
        if (basePrice < 0) {
            throw new RuntimeException("Base price cannot be negative.");
        }
        if (flat) {
            return flatAmount;
        } else {
            switch (frequency) {
                case Onetime:
                    return discount = 0.0f;
                case Hourly:
                    return discount = basePrice * 0.01f;
                case Weekly:
                    return discount = basePrice * 0.05f;
                case Biweekly:
                    return discount = basePrice * 0.08f;
                case Monthly:
                    return discount = basePrice * 0.10f;
                case Yearly:
                    return discount = basePrice * 0.15f;
                case Weekday:
                    return discount = basePrice * 0.07f;
                case Weekend:
                    return discount = basePrice * 0.04f;
                case Emergency:
                    return discount = basePrice * (-0.05f);
                case Holiday:
                    return discount = basePrice * (-0.25f);
            }
        }
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