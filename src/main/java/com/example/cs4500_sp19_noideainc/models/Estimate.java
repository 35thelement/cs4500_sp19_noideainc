package com.example.cs4500_sp19_noideainc.models;

import java.util.List;

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
    
    /*
     * Default constructor for the Estimate class
     */
	public Estimate() {
		
	}
	
    /*
     * Constructor for the Estimate class by given all elements
     */
	public Estimate(float estimate, float basePrice, Frequency baseFrequency, boolean subscription,
			Frequency subscriptionFrequency, Frequency deliveryFrequency, Service service, User user) {
		this.estimate = estimate;
		this.basePrice = basePrice;
		this.baseFrequency = baseFrequency;
		this.subscription = subscription;
		this.subscriptionFrequency = subscriptionFrequency;
		this.deliveryFrequency = deliveryFrequency;
		this.service = service;
		this.user = user;
	}

    
    /* Illustrate the algorithm consider the 
     * different frequency (related to different delivery fees)
     * 
     * @param listDeliveryFree: a list of DeliveryFee information, get related frequency information in this list
     */
    public float getFees(List<DeliveryFee> listDeliveryFree) throws Exception {
    	float calculation = 0f;
    	
    	// check if the listDeliveryFree is invalid
    	if (listDeliveryFree == null) {
    		throw new IllegalArgumentException("invalid listDeliveryFree");
    	}
    	
    	// check the base price
    	if (this.basePrice < 0) {
    		throw new IllegalArgumentException("invalid base fee");
    	}

    	// consider the the delivery frequency 
    	DeliveryFee getDeliveryFee = this.getFrequencyValue(listDeliveryFree, this.deliveryFrequency);
    	
    	if (getDeliveryFee != null) {
    		if (getDeliveryFee.isFlat()) {
        		calculation = getDeliveryFee.getFee();
        		if (calculation < 0) {
        			throw new IllegalArgumentException("invalid flat fee");
        		}
        		// we design that the delivery fee cannot be greater than the five times of the base fee
        		if (calculation > this.basePrice * 5) {
        			throw new IllegalArgumentException("too high flat fee");
        		}
        	} else {
        		if (getDeliveryFee.getFee() < 0) {
        			throw new IllegalArgumentException("invalid percentage fee");
        		}
        		// we design that the delivery fee cannot be greater than the five times of the base fee
        		if (getDeliveryFee.getFee() > 5) {
        			throw new IllegalArgumentException("too high percentage fee");
        		}
        		calculation = this.basePrice * getDeliveryFee.getFee();
        	}
    	} else {
    		throw new IllegalArgumentException("Invalid given list of deliveryFees, cannot find related frequecny in the list");
    	}
    	
    	return calculation;
    }
    
    // find related DeliveryFee base on the given Frequency (Enum) in the list of deliverFees class
    private DeliveryFee getFrequencyValue(List<DeliveryFee> listDeliveryFree, Frequency frequency) {
    	for (int i = 0; i < listDeliveryFree.size(); i++) {
    		DeliveryFee current = listDeliveryFree.get(i);
    		if (current.getFrequency().equals(frequency)) {
    			return current;
    		}
    	}
    	return null;
    }
    //get Discount amount
    public float getDiscount(List<SubscriptionDiscount> listSubscriptionDiscount) throws Exception {
    	float currentDiscount = 0f;
    	// check if the listSubscriptionDiscount is invalid
    	if (listSubscriptionDiscount == null) {
    		throw new IllegalArgumentException("invalid listSubscriptionDiscount");
    	}
    	// check the base price
    	if (this.basePrice < 0) {
    		throw new IllegalArgumentException("invalid base price");
    	}

    	// consider subscription frequency
    	SubscriptionDiscount getSubscriptionDiscount = this.getDiscountFrequencyValue(listSubscriptionDiscount, this.subscriptionFrequency);
    	if (getSubscriptionDiscount != null) {
    		if (getSubscriptionDiscount.isFlat()) {
        		currentDiscount = getSubscriptionDiscount.getDiscount();
        		if (currentDiscount < 0) {
        			throw new IllegalArgumentException("invalid flat discount");
        		}
        		// flat discount cannot be greater than base price
        		if (currentDiscount > this.basePrice) {
        			throw new IllegalArgumentException("too high flat discount");
        		}
        	} else {
        		if (getSubscriptionDiscount.getDiscount() < 0) {
        			throw new IllegalArgumentException("invalid discount");
        		}
        		// discount cannot be greater than 100
        		if (getSubscriptionDiscount.getDiscount() > 100) {
        			throw new IllegalArgumentException("too high discount");
        		}
        		currentDiscount = this.basePrice * getSubscriptionDiscount.getDiscount();
        	}
    	}
    	
    	return currentDiscount;
    }
        		
    // find related SubscriptionDiscount base on the given Frequency (Enum) in the list of SubscriptionDiscount class
    private SubscriptionDiscount getDiscountFrequencyValue(List<SubscriptionDiscount> ListSubscriptionDiscount, Frequency frequency) {
    	for (int i = 0; i < ListSubscriptionDiscount.size(); i++) {
    		SubscriptionDiscount current = ListSubscriptionDiscount.get(i);
    		if (current.getFrequency().equals(frequency)) {
    			return current;
    		}
    	}
    	return null;
    }
    
    
	public float getEstimate() {
		// this.estimate = this.basePrice + this.getFees() - this.getDiscount()
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