package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    @Query(value="SELECT review FROM Review review")
    public List<Review> findAllReviews();
    @Query(value="SELECT review FROM Review review WHERE review.id=:id")
    public Review findReviewById(@Param("id") Integer id);
}