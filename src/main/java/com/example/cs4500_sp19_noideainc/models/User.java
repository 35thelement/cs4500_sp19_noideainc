package com.example.cs4500_sp19_noideainc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "provider")
    private List<ServiceAnswer> serviceAnswers;
    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviewsOfMe;
    @OneToMany(mappedBy = "reviewed")
    private List<Review> myReviewsOfOthers;
    @OneToMany(mappedBy = "user")
    private List<FrequentlyAskedAnswer> frequentlyAskedAnswers;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "PROVIDERS_SERVICES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID"))
    private List<Service> services;

    public User() {
    }

    public User(Integer id, String username, String password, String firstName, String lastName) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<FrequentlyAskedAnswer> getFrequentlyAskedAnswers() {
        return frequentlyAskedAnswers;
    }

    public void setFrequentlyAskedAnswers(List<FrequentlyAskedAnswer> frequentlyAskedAnswers) {
        this.frequentlyAskedAnswers = frequentlyAskedAnswers;
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

    public List<Review> getReviewsOfMe() {
        return reviewsOfMe;
    }

    public void setReviewsOfMe(List<Review> reviewsOfMe) {
        this.reviewsOfMe = reviewsOfMe;
    }

    public List<Review> getMyReviewsOfOthers() {
        return myReviewsOfOthers;
    }

    public void setMyReviewsOfOthers(List<Review> myReviewsOfOthers) {
        this.myReviewsOfOthers = myReviewsOfOthers;
    }

  public List<ServiceAnswer> getServiceAnswers() {
    return serviceAnswers;
  }

  public void setServiceAnswers(List<ServiceAnswer> serviceAnswers) {
    this.serviceAnswers = serviceAnswers;
  }
}