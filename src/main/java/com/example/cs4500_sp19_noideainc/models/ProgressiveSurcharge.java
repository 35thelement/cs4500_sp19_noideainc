
package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * This class represent a progressive surcharge based on 
 * the different distance traveled, e.g., 1% for every 
 * additional mile traveled beyond some threshold, 10% 
 * for 10-20 additional miles, 20% for 20-40 additional miles
 */
@Entity
@Table(name="progressiveSurcharge")
public class ProgressiveSurcharge {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	// all attributes of the progressive surcharge
	private float subcharge; // surcharge is the percentage
	private float distance;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public float getSubcharge() {
		return subcharge;
	}
	
	public void setSubcharge(float subcharge) {
		this.subcharge = subcharge;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
}