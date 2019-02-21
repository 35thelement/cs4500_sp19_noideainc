
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
    public float getFees(List<DeliveryFee> listDeliveryFree) {
    	float finalEstimate = this.basePrice;
    	
    	// consider base frequency
    	DeliveryFee getBaseFrequencyFee = this.getFrequencyValue(listDeliveryFree, this.baseFrequency);
    	if (getBaseFrequencyFee.isFlat()) {
    		finalEstimate = finalEstimate + getBaseFrequencyFee.getFee();
    	} else {
    		finalEstimate = finalEstimate + this.basePrice * getBaseFrequencyFee.getFee();
    	}
    	
    	// consider the the discount frequency 
    	if (this.subscription) {
        	DeliveryFee getSubscriptionFrequencyFee = this.getFrequencyValue(listDeliveryFree, this.subscriptionFrequency);
        	if (getSubscriptionFrequencyFee.isFlat()) {
        		finalEstimate = finalEstimate - getSubscriptionFrequencyFee.getFee();
        	} else {
        		finalEstimate = finalEstimate - this.basePrice * getSubscriptionFrequencyFee.getFee();
        	}
    	}
    	
    	// consider the the delivery frequency 
    	DeliveryFee getDeliveryFee = this.getFrequencyValue(listDeliveryFree, this.deliveryFrequency);
    	if (getDeliveryFee.isFlat()) {
    		finalEstimate = finalEstimate + getDeliveryFee.getFee();
    	} else {
    		finalEstimate = finalEstimate + this.basePrice * getDeliveryFee.getFee();
    	}
    	
    	return finalEstimate;
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