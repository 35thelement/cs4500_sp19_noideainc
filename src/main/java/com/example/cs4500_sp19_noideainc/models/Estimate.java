package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class represents an estimate that a service provider would give to a user incentive
 * to purchase
 */
@Entity
@Table(name="estimate")
public class Estimate {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    // A incentive estimate calculated for user to purchase service
    private float estimate;
    private float basePrice;
    private Frequency baseFrequency;
    private boolean subscription;
    private Frequency subscriptionFrequency;
    private Frequency deliveryFrequency;
    

    // The Service being purchased
    @ManyToOne
    @JsonIgnore
    private Service service;
    // The user purchasing the service
    @ManyToOne
    @JsonIgnore
    private User user;
	
	public float getEstimate() {
		return estimate;
	}
	public void setEstimate(float estimate) {
		this.estimate = estimate;
	}
	public float getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}
	public Frequency getBaseFrequency() {
		return baseFrequency;
	}
	public void setBaseFrequency(Frequency baseFrequency) {
		this.baseFrequency = baseFrequency;
	}
	public boolean isSubscription() {
		return subscription;
	}
	public void setSubscription(boolean subscription) {
		this.subscription = subscription;
	}
	public Frequency getSubscriptionFrequency() {
		return subscriptionFrequency;
	}
	public void setSubscriptionFrequency(Frequency subscriptionFrequency) {
		this.subscriptionFrequency = subscriptionFrequency;
	}
	public Frequency getDeliveryFrequency() {
		return deliveryFrequency;
	}
	public void setDeliveryFrequency(Frequency deliveryFrequency) {
		this.deliveryFrequency = deliveryFrequency;
	}
	
}
