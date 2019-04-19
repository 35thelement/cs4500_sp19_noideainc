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
    private String street = "";
    private String city = "";
    private String state = "";
    private String zip = "";
    // 0 for home address, 1 for business address. Defaults to home.
    private Integer addressType = 0;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resident_id")
    @JsonIgnore
    private User resident;

    public Address() {
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
        this.street = street;
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