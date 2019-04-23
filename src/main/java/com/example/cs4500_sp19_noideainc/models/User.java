package com.example.cs4500_sp19_noideainc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints=@UniqueConstraint(columnNames={"email"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private UserType userType = UserType.Client;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer rating;
    private String birthday = "";
    private String email;
    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviewsOfMe;
    @OneToMany(mappedBy = "reviewed")
    private List<Review> myReviewsOfOthers;
    @OneToMany(mappedBy = "user")
    private List<FrequentlyAskedAnswer> frequentlyAskedAnswers;

    /*Provider fields*/
    private String businessName;
    private Integer yearFounded;
    private Integer numOfEmployees;
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "resident_id")
    private List<Address> addresses = new ArrayList<Address>();
    private String businessEmail;
    @OneToMany(mappedBy = "establishment")
    private List<PaymentMethod> paymentMethods;
    private String facebook;
    private String instagram;
    private String twitter;
    private int hires;
    private boolean backgroundChecked;
    @OneToMany(mappedBy = "provider")
    private List<ServiceAnswer> serviceAnswers;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "PROVIDERS_SERVICES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID"))
    private List<Service> services;

    public User() {
    }

    public User(Integer id, UserType userType, String username, String password, String firstName, String lastName) {
        super();
        this.id = id;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(Integer id, UserType userType, String username, String password, String firstName,
                String lastName, String email, String businessName, Integer yearFounded, Integer numOfEmployees,
                String businessEmail, String facebook, String instagram, String twitter, int hires, boolean backgroundChecked,
                String birthday, List<Address> addresses) {
        super();
        this.id = id;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.businessName = businessName;
        this.yearFounded = yearFounded;
        this.numOfEmployees = numOfEmployees;
        this.businessEmail = businessEmail;
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
        this.hires = hires;
        this.backgroundChecked = backgroundChecked;
        this.birthday = birthday;
        this.addresses = addresses;
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
        if (!reviewsOfMe.isEmpty()) {
            int r = 0;
            for (int i = 0; i < reviewsOfMe.size(); i++) {
                r += reviewsOfMe.get(i).getRating();
            }
            this.rating = r / reviewsOfMe.size();
        }
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(Integer yearFounded) {
        this.yearFounded = yearFounded;
    }

    public Integer getNumOfEmployees() {
        return numOfEmployees;
    }

    public void setNumOfEmployees(Integer numOfEmployees) {
        this.numOfEmployees = numOfEmployees;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }
    
    public List<Address> getAddresses() {
    	return this.addresses;
    }
    
    public void setAddresses(List<Address> addresses) {
    	this.addresses = addresses;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getHires() { return this.hires; }

    public void setHires(int hires) { this.hires = hires; }

    public boolean getBackgroundChecked() { return this.backgroundChecked; }

    public void setBackgroundChecked(boolean backgroundChecked) { this.backgroundChecked = backgroundChecked; }
    
	public String getBirthday() {
		return this.birthday;
    }
      
	public void setBirthday(String birthday) {
    	this.birthday = birthday;
    }
}