package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String street;
    private String city;
    private String state;
    private String zip;
    // 0 for home address, 1 for business address
    private Integer addressType;
    //  @ManyToOne
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resident_id", updatable = true)
    @JsonIgnore
    private User resident;

    public Address() {
    	this.street = "";
    	this.city = "";
    	this.state = "";
    	this.zip = "";
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getStreet() {
        return this.street;
    }
    public void setStreet(String street) {
    	System.out.println("IN SET STREET:");
    	System.out.println(street);
        this.street = street;
        System.out.println("after setting street");
        System.out.println(this.street);
        		
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZip() {
        return this.zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public User getResident() {
        return this.resident;
    }
    public void setResident(User resident) {
        this.resident = resident;
    }
    public Integer getAddressType() {
    	return this.addressType;
    }
    public void setAddressType(Integer addressType) {
    	this.addressType = addressType;
    }
}