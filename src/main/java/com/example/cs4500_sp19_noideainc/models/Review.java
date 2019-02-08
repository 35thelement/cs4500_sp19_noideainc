package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String review;
    private Integer rating;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="reviewer_id", referencedColumnName="id")
    @JsonIgnore
    private User reviewer;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="reviewed_id", referencedColumnName="id")
    @JsonIgnore
    private User reviewed;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getReview() {
        return this.review;
    }
    public void setReview(String review) {
        this.review = review;
    }
    public int getRating() {
        return this.rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public User getReviewer() {
        return this.reviewer;
    }
    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }
    public User getReviewed() {
        return this.reviewed;
    }
    public void setReviewed(User reviewed) {
        this.reviewed = reviewed;
    }
}