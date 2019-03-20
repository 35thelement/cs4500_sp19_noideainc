package com.example.cs4500_sp19_noideainc.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewTests {
    private User dante = new User(1, "dante", "password", "Dante", "Sparda");
    private User vergil = new User(2, "vergil", "password", "Vergil", "Sparda");

    private Review review = new Review();

    @Test
    public void testSettersAndGetters() {
        review.setId(1);
        review.setRating(3);
        review.setReview("Good work!");
        review.setReviewer(dante);
        review.setReviewed(vergil);
        review.setTitle("This is a review.");

        assertEquals(1, review.getId().intValue());
        assertEquals(3, review.getRating());
        assertEquals("Good work!", review.getReview());
        assertEquals("This is a review.", review.getTitle());
        assertSame(dante, review.getReviewer());
        assertSame(vergil, review.getReviewed());
    }

    @Test
    public void testConstructor() {
        review = new Review(1, "This is a review.", "Good work!", 3, dante, vergil);

        assertEquals(1, review.getId().intValue());
        assertEquals(3, review.getRating());
        assertEquals("Good work!", review.getReview());
        assertEquals("This is a review.", review.getTitle());
        assertSame(dante, review.getReviewer());
        assertSame(vergil, review.getReviewed());
    }
}