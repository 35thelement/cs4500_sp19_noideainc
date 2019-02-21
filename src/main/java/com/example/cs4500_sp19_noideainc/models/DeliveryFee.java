package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * This class represents the delivery fee added on the base estimate. The delivery fee
 * will depends on different types of frequency
 * 
 */
@Entity
@Table(name="deliveryFee")
public class DeliveryFee {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	// the constructor of DeliveryFee by given parameters
	public DeliveryFee(float fee, Frequency frequency, boolean flat) {
		this.fee = fee;
		this.frequency = frequency;
		this.flat = flat;
	}
	
	// all attributes of the delivery fee
	private float fee;
	private Frequency frequency; 
	private boolean flat;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public float getFee() {
		return fee;
	}
	
	public void setFee(float fee) {
		this.fee = fee;
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
	
	
}
