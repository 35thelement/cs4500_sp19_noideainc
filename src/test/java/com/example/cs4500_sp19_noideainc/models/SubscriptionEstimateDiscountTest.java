package com.example.cs4500_sp19_noideainc.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class SubscriptionEstimateDiscountTest {
/**
    private SubscriptionDiscount TestServiceSub = new SubscriptionDiscount();

    @Test(expected = RuntimeException.class)
    public void testNegativeFlatAmount() {
        float actual = TestServiceSub.getDiscount(Frequency.Onetime, true, -10, 19.8f);
    }

    @Test(expected = RuntimeException.class)
    public void testNegativeBasePrice() {
        float actual = TestServiceSub.getDiscount(Frequency.Onetime, true, 10, -19.8f);
    }

    @Test
    public void testFlatDiscount() {
        float actual = TestServiceSub.getDiscount(Frequency.Onetime, true, 5, 10.0f);
        assertEquals(5.0f, actual, 0.0f);
    }

    @Test
    public void testOnetime() {
        float actual = TestServiceSub.getDiscount(Frequency.Onetime, false, 0, 10.0f);
        assertEquals(0.0f, actual, 0.0f);
    }

    @Test
    public void testHourly() {
        float actual = TestServiceSub.getDiscount(Frequency.Hourly, false, 5, 10.0f);
        assertEquals(0.1f, actual, 0.01f);
    }

    @Test
    public void testWeekly() {
        float actual = TestServiceSub.getDiscount(Frequency.Weekly, false, 5, 10.0f);
        assertEquals(0.5, actual, 0.01f);
    }

    @Test
    public void testBiweekly() {
        float actual = TestServiceSub.getDiscount(Frequency.Biweekly, false, 5, 10.0f);
        assertEquals(0.8f, actual, 0.01f);
    }

    @Test
    public void testMonthly() {
        float actual = TestServiceSub.getDiscount(Frequency.Monthly, false, 5, 10.0f);
        assertEquals(1.0f, actual, 0.01f);
    }

    @Test
    public void testYearly() {
        float actual = TestServiceSub.getDiscount(Frequency.Yearly, false, 5, 10.0f);
        assertEquals(1.5f, actual, 0.01f);
    }

    @Test
    public void testWeekday() {
        float actual = TestServiceSub.getDiscount(Frequency.Weekday, false, 5, 10.0f);
        assertEquals(0.7f, actual, 0.01f);
    }

    @Test
    public void testWeekend() {
        float actual = TestServiceSub.getDiscount(Frequency.Weekend, false, 5, 10.0f);
        assertEquals(0.4f, actual, 0.01f);
    }

    @Test
    public void testEmergency() {
        float actual = TestServiceSub.getDiscount(Frequency.Emergency, false, 5, 10.0f);
        assertEquals(-0.5f, actual, 0.01f);
    }

    @Test
    public void testHoliday() {
        float actual = TestServiceSub.getDiscount(Frequency.Holiday, false, 5, 10.0f);
        assertEquals(-2.5f, actual, 0.01f);
    }
    */
}
