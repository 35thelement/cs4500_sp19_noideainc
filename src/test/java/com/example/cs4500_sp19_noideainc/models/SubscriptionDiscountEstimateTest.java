package com.example.cs4500_sp19_noideainc.models;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionDiscountEstimateTest {

	// Default service and user
	User user = new User();
	Service service = new Service();

	// Default value of baseFrequency and deliveryFrequency
	Frequency baseFrequency = Frequency.Daily;
	Frequency deliveryFrequency = Frequency.Weekday;

	// All basic frequency and related SubscriptionDiscount class (all of them are flat fees)
	private final SubscriptionDiscount dailyFlat = new SubscriptionDiscount(0f, Frequency.Daily, true);
	private final SubscriptionDiscount weeklyFlat = new SubscriptionDiscount(75f, Frequency.Weekly, true);
	private final SubscriptionDiscount monthlyFlat = new SubscriptionDiscount(150f, Frequency.Monthly, true);
	private final SubscriptionDiscount yearlyFlat = new SubscriptionDiscount(225f, Frequency.Yearly, true);

	// Note: we assume the estimate value is the 0 at the beginning (when initialize)
	
	@Test
	// Test regular situations for the calculation of different flat fees
	public void testRegularFlatFees() throws Exception {
		// a list of SubscriptionDiscount regular
		List<SubscriptionDiscount> listSubscriptionDiscount = new ArrayList<SubscriptionDiscount>();
		listSubscriptionDiscount.add(this.dailyFlat);
		listSubscriptionDiscount.add(this.weeklyFlat);
		listSubscriptionDiscount.add(this.monthlyFlat);
		listSubscriptionDiscount.add(this.yearlyFlat);

		Estimate estimate = new Estimate(0f, 750f, baseFrequency, false, Frequency.Daily, deliveryFrequency, service, user);	
		// Test the frequency are added 0 on the base price
		assertEquals(0f, estimate.getDiscount(listSubscriptionDiscount));
		// Test delivery frequency with different fat fee
		estimate.setSubscriptionFrequency(Frequency.Weekly);
		assertEquals(75f, estimate.getDiscount(listSubscriptionDiscount));

		estimate.setSubscriptionFrequency(Frequency.Monthly);
		assertEquals(150f, estimate.getDiscount(listSubscriptionDiscount));

		estimate.setSubscriptionFrequency(Frequency.Yearly);
		assertEquals(225f, estimate.getDiscount(listSubscriptionDiscount));
	}
	@Test
	// Test positive result of get fees
	public void testPositiveFlatFees() throws Exception {
		// a list of deliveryFees
		List<SubscriptionDiscount> listSubscriptionDiscount = new ArrayList<SubscriptionDiscount>();
		SubscriptionDiscount dailyFlat1 = new SubscriptionDiscount(15f, Frequency.Daily, true);
		listSubscriptionDiscount.add(dailyFlat1);
		Estimate estimate = new Estimate(0f, 500f, baseFrequency, false, Frequency.Daily, deliveryFrequency, service, user);
		assertEquals(true, estimate.getDiscount(listSubscriptionDiscount) > 0);
		assertEquals(false, estimate.getDiscount(listSubscriptionDiscount) < 0);
	}
	@Test
	// Test the decimal flat fee from 0 to 1
	public void testDecimalFlatFees() throws Exception {
		// a list of SubscriptionDiscount
		List<SubscriptionDiscount> listSubscriptionDiscount = new ArrayList<SubscriptionDiscount>();
		SubscriptionDiscount DailyFlat = new SubscriptionDiscount(4.555f, Frequency.Daily, true);
		SubscriptionDiscount WeeklyFlat = new SubscriptionDiscount(15.7111f, Frequency.Weekly, true);
		SubscriptionDiscount MonthlyFlat = new SubscriptionDiscount(100.8f, Frequency.Monthly, true);
		SubscriptionDiscount YearlyFlat = new SubscriptionDiscount(200.12f, Frequency.Yearly, true);
		listSubscriptionDiscount.add(DailyFlat);
		listSubscriptionDiscount.add(WeeklyFlat);
		listSubscriptionDiscount.add(MonthlyFlat);
		listSubscriptionDiscount.add(YearlyFlat);

		Estimate estimate = new Estimate(0f, 1000f, baseFrequency, false, Frequency.Daily, deliveryFrequency, service, user);
		assertEquals(4.555f, estimate.getDiscount(listSubscriptionDiscount));

		estimate.setSubscriptionFrequency(Frequency.Weekly);
		assertEquals(15.7111f, estimate.getDiscount(listSubscriptionDiscount));

		estimate.setSubscriptionFrequency(Frequency.Monthly);
		assertEquals(100.8f, estimate.getDiscount(listSubscriptionDiscount));
		
		estimate.setSubscriptionFrequency(Frequency.Yearly);
		assertEquals(200.12f, estimate.getDiscount(listSubscriptionDiscount));
	}
	@Test
	// Test the very big flat fee but does not bigger than the base price
	public void testBigFlatFees() throws Exception {
		// a list of SubscriptionDiscount
		List<SubscriptionDiscount> listSubscriptionDiscount = new ArrayList<SubscriptionDiscount>();
		SubscriptionDiscount WeeklyFlat = new SubscriptionDiscount(999f, Frequency.Weekly, true);
		SubscriptionDiscount MonthlyFlat = new SubscriptionDiscount(999.9f, Frequency.Monthly, true);
		SubscriptionDiscount YearlyFlat = new SubscriptionDiscount(999.999999f, Frequency.Yearly, true);
		listSubscriptionDiscount.add(WeeklyFlat);
		listSubscriptionDiscount.add(MonthlyFlat);
		listSubscriptionDiscount.add(YearlyFlat);
		Estimate estimate = new Estimate(0f, 1000f, baseFrequency, false, Frequency.Daily, deliveryFrequency, service, user);

		estimate.setSubscriptionFrequency(Frequency.Weekly);
		assertEquals(999f, estimate.getDiscount(listSubscriptionDiscount));
		
		estimate.setSubscriptionFrequency(Frequency.Monthly);
		assertEquals(999.9f, estimate.getDiscount(listSubscriptionDiscount));

		estimate.setSubscriptionFrequency(Frequency.Yearly);
		assertEquals(999.999999f, estimate.getDiscount(listSubscriptionDiscount));
	}
	
	// Invalid negative flat fee situations	
		@Test (expected = IllegalArgumentException.class)
		public void negativeFlatFees() throws Exception {
			List<SubscriptionDiscount> listSubscriptionDiscount = new ArrayList<SubscriptionDiscount>();
			SubscriptionDiscount DailyFlat = new SubscriptionDiscount(-30f, Frequency.Daily, true);
			listSubscriptionDiscount.add(DailyFlat);
			Estimate estimate = new Estimate(0f, 500f, baseFrequency, false, Frequency.Daily, deliveryFrequency, service, user);
			assertEquals(-30f, estimate.getDiscount(listSubscriptionDiscount));
		}

		// Invalid flat fee is too high 
		@Test (expected = IllegalArgumentException.class)
		public void tooHighFlatFees() throws Exception {
			List<SubscriptionDiscount> listSubscriptionDiscount = new ArrayList<SubscriptionDiscount>();
			SubscriptionDiscount DailyFlat = new SubscriptionDiscount(1001f, Frequency.Daily, true);
			listSubscriptionDiscount.add(DailyFlat);
			Estimate estimate = new Estimate(0f, 1000f, baseFrequency, false, Frequency.Daily, deliveryFrequency, service, user);
			assertEquals(1001f, estimate.getDiscount(listSubscriptionDiscount));
		}
		
		// Test total estimated price 
		// Set default deliveryFee  
		float deliveryFee1 = 10f;
		float deliveryFee2 = 100f;
		
		// Test the calculate of the total estimate (do not cover too many detail tests because other unit tests will cover detail
		// for every field in the estimate)
		@Test
		public void totalEstimate() throws Exception {
			List<SubscriptionDiscount> listSubscriptionDiscount = new ArrayList<SubscriptionDiscount>();
			SubscriptionDiscount dailyFlat = new SubscriptionDiscount(0f, Frequency.Daily, false);
			SubscriptionDiscount weeklyFlat = new SubscriptionDiscount(30f, Frequency.Weekly, true);
			SubscriptionDiscount monthlyFlat = new SubscriptionDiscount(50f, Frequency.Monthly, true);
			SubscriptionDiscount yearlyFlat = new SubscriptionDiscount(100f, Frequency.Yearly, true);
			listSubscriptionDiscount.add(dailyFlat);
			listSubscriptionDiscount.add(weeklyFlat);
			listSubscriptionDiscount.add(monthlyFlat);
			listSubscriptionDiscount.add(yearlyFlat);

			Estimate estimate1 = new Estimate(0f, 10f, baseFrequency, true, Frequency.Daily, deliveryFrequency, service, user);
			Estimate estimate2 = new Estimate(0f, 200f, baseFrequency, false, Frequency.Weekly, deliveryFrequency, service, user);
			Estimate estimate3 = new Estimate(0f, 750f, baseFrequency, true, Frequency.Monthly, deliveryFrequency, service, user);
			Estimate estimate4 = new Estimate(0f, 800f, baseFrequency, false, Frequency.Yearly, deliveryFrequency, service, user);

			// Estimate
			float totalEstimate1 = estimate1.getBasePrice() - estimate1.getDiscount(listSubscriptionDiscount) + deliveryFee1;
			assertEquals(20f, totalEstimate1);

			// Estimate
			float totalEstimate2 = estimate2.getBasePrice() - estimate2.getDiscount(listSubscriptionDiscount) + deliveryFee1;
			assertEquals(180f, totalEstimate2);

			// Estimate
			float totalEstimate3 = estimate3.getBasePrice() - estimate3.getDiscount(listSubscriptionDiscount) + deliveryFee2;
			assertEquals(800f, totalEstimate3);

			// Estimate
			float totalEstimate4 = estimate4.getBasePrice() - estimate4.getDiscount(listSubscriptionDiscount) + deliveryFee2;
			assertEquals(800f, totalEstimate4);

		}
		
		// Invalid base price (negative)
		@Test (expected = IllegalArgumentException.class)
		public void invalidBaseFee() throws Exception {
			List<SubscriptionDiscount> listSubscriptionDiscount = new ArrayList<SubscriptionDiscount>();
			SubscriptionDiscount dailyFlat = new SubscriptionDiscount(200f, Frequency.Daily, true);
			listSubscriptionDiscount.add(dailyFlat);
			Estimate estimate = new Estimate(0f, -100f, baseFrequency, false, Frequency.Daily, deliveryFrequency, service, user);
			assertEquals(100f, estimate.getDiscount(listSubscriptionDiscount));
		}
		
		@Test
		// Tests regular Yearly discount (both flat and percentage)
		public void testRegularlYealy() throws Exception {
			// add various Yearly subscription discount to separate lists
			List<SubscriptionDiscount> listSubscriptionDiscount = new ArrayList<SubscriptionDiscount>();
			List<SubscriptionDiscount> listYearlyDiscount1 = new ArrayList<SubscriptionDiscount>();
			List<SubscriptionDiscount> listYearlyDiscount2 = new ArrayList<SubscriptionDiscount>();

			SubscriptionDiscount YearlyPercentageDiscount1 = new SubscriptionDiscount(0f, Frequency.Yearly, false);
			SubscriptionDiscount YearlyPercentageDiscount2 = new SubscriptionDiscount(0.5f, Frequency.Yearly, false);
			SubscriptionDiscount YearlyFlatDIscount = new SubscriptionDiscount(500f, Frequency.Yearly, true);

			listSubscriptionDiscount.add(YearlyPercentageDiscount1);
			listYearlyDiscount1.add(YearlyPercentageDiscount2);
			listYearlyDiscount2.add(YearlyFlatDIscount);

			Estimate estimate = new Estimate(0, 800f, baseFrequency, false, Frequency.Yearly, deliveryFrequency, service, user);

			assertEquals(0, estimate.getDiscount(listSubscriptionDiscount), 0.0001f);
			assertEquals(400, estimate.getDiscount(listYearlyDiscount1), 0.0001f);
			assertEquals(500, estimate.getDiscount(listYearlyDiscount2), 0.0001f);
		}
		
		@Test (expected = IllegalArgumentException.class)
		// Tests Discount that exceed the fee limit (more than 100%)
		public void testInvalidDiscount() throws Exception {
			List<SubscriptionDiscount> listSubscriptionDiscount = new ArrayList<SubscriptionDiscount>();
			SubscriptionDiscount BigDiscount = new SubscriptionDiscount(101f, Frequency.Yearly, false);
			listSubscriptionDiscount.add(BigDiscount);

			Estimate estimate = new Estimate(0f, 900f, baseFrequency, false, Frequency.Yearly, deliveryFrequency, service, user);

			assertEquals(500f, estimate.getDiscount(listSubscriptionDiscount), 0.0001f);
		}

}
