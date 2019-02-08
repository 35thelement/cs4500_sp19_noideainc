package com.example.cs4500_sp19_noideainc.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.MappedSuperclass;


@Entity
@Table(name = "users")
//@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    @OneToOne(mappedBy = "resident")
    private Address address;
    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviewsOfMe;
    @OneToMany(mappedBy = "reviewed")
    private List<Review> myReviewsOfOthers;

    public User() {
    }

    public User(Integer i, String username, String firstName, String lastName) {
        this.id = i;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Review> getReviewsOfMe() {
        return this.reviewsOfMe;
    }

    public void setReviewsOfMe(List<Review> reviewsOfMe) {
        this.reviewsOfMe = reviewsOfMe;
    }

    public List<Review> getMyReviewsOfOthers() {
        return this.myReviewsOfOthers;
    }

    public void setMyReviewsOfOthers(List<Review> myReviewsOfOthers) {
        this.myReviewsOfOthers = myReviewsOfOthers;
    }
}