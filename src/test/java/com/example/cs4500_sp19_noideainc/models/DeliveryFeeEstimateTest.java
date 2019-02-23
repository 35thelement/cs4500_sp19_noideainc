

package com.example.cs4500_sp19_noideainc.models;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeliveryFeeEstimateTest {
	// Default service and user
	User user = new User();
	Service service = new Service();
	
	// Default value of baseFrequency and subscriptionFrequency
	Frequency baseFrequency = Frequency.Daily;
	Frequency subscriptionFrequency = Frequency.Daily;
	
	// All basic frequency and related deliverFee class (all of them are flat fees)
	private final DeliveryFee weekdayFlat = new DeliveryFee(0, Frequency.Weekday, true);
	private final DeliveryFee weekendFlat = new DeliveryFee(75f, Frequency.Weekend, true);
	private final DeliveryFee holidayFlat = new DeliveryFee(150f, Frequency.Holiday, true);
	private final DeliveryFee EmergencyFlat = new DeliveryFee(225f, Frequency.Emergency, true);
	
	// Note: we assume the estimate value is the 0 at the beginning (when initialize)
	
	@Test
	// Test regular situations for the calculation of different flat fees
	public void testRegularFlatFees() throws Exception {
		// a list of deliveryFees regular
		List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
		listDeliveryFee.add(this.weekdayFlat);
		listDeliveryFee.add(this.weekendFlat);
		listDeliveryFee.add(this.holidayFlat);
		listDeliveryFee.add(this.EmergencyFlat);
		
		Estimate estimate = new Estimate(0f, 750f, baseFrequency, false, subscriptionFrequency, Frequency.Weekday, service, user);	
		// Test the frequency are added 0 on the base price
		assertEquals(0f, estimate.getFees(listDeliveryFee));
		
		// Test delivery frequency with different fat fee
		estimate.setDeliveryFrequency(Frequency.Weekend);
		assertEquals(75f, estimate.getFees(listDeliveryFee));
		
		estimate.setDeliveryFrequency(Frequency.Holiday);
		assertEquals(150f, estimate.getFees(listDeliveryFee));
		
		estimate.setDeliveryFrequency(Frequency.Emergency);
		assertEquals(225f, estimate.getFees(listDeliveryFee));
	}
	
	@Test
	// Test positive result of get fees
	public void testAllFlatFees() throws Exception {
		// a list of deliveryFees
		List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee weekdayFlat1 = new DeliveryFee(15f, Frequency.Weekday, true);
		listDeliveryFee.add(weekdayFlat1);
		Estimate estimate = new Estimate(0f, 500f, baseFrequency, false, subscriptionFrequency, Frequency.Weekday, service, user);
		assertEquals(true, estimate.getFees(listDeliveryFee) > 0);
		assertEquals(false, estimate.getFees(listDeliveryFee) < 0);
	}
	
	@Test
	// Test the decimal flat fee from 0 to 1
	public void testDecimalFlatFees() throws Exception {
		// a list of deliveryFees
		List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee weekdayFlat = new DeliveryFee(4.555f, Frequency.Weekday, true);
		DeliveryFee WeekendFlat = new DeliveryFee(15.7111f, Frequency.Weekend, true);
		DeliveryFee HolidayFlat = new DeliveryFee(1900.8f, Frequency.Holiday, true);
		listDeliveryFee.add(weekdayFlat);
		listDeliveryFee.add(WeekendFlat);
		listDeliveryFee.add(HolidayFlat);

		Estimate estimate = new Estimate(0f, 500f, baseFrequency, false, subscriptionFrequency, Frequency.Weekday, service, user);
		assertEquals(4.555f, estimate.getFees(listDeliveryFee));
		
		estimate.setDeliveryFrequency(Frequency.Weekend);
		assertEquals(15.7111f, estimate.getFees(listDeliveryFee));
		
		estimate.setDeliveryFrequency(Frequency.Holiday);
		assertEquals(1900.8f, estimate.getFees(listDeliveryFee));
	}
	
	@Test
	// Test the very big flat fee but does not bigger than the limited value
	// (five times of the base price)
	public void testBigFlatFees() throws Exception {
		// a list of deliveryFees
		List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee weekdayFlat = new DeliveryFee(2000f, Frequency.Weekend, true);
		DeliveryFee holidayFlat = new DeliveryFee(1999.8f, Frequency.Holiday, true);
		listDeliveryFee.add(weekdayFlat);
		listDeliveryFee.add(holidayFlat);
		Estimate estimate = new Estimate(0f, 400f, baseFrequency, false, subscriptionFrequency, Frequency.Weekday, service, user);
		
		estimate.setDeliveryFrequency(Frequency.Weekend);
		assertEquals(2000f, estimate.getFees(listDeliveryFee));
		
		estimate.setDeliveryFrequency(Frequency.Holiday);
		assertEquals(1999.8f, estimate.getFees(listDeliveryFee));
	}
	
	@Test
	// Test the very small flat fee from 0 to 1
	public void testTinyFlatFees() throws Exception {
		// a list of deliveryFees
		List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee weekdayFlat1 = new DeliveryFee(0.555f, Frequency.Weekday, true);
		DeliveryFee weekendFlat2 = new DeliveryFee(0.00001f, Frequency.Weekend, true);
		listDeliveryFee.add(weekdayFlat1);
		listDeliveryFee.add(weekendFlat2);

		Estimate estimate = new Estimate(0f, 500f, baseFrequency, false, subscriptionFrequency, Frequency.Weekday, service, user);
		assertEquals(0.555f, estimate.getFees(listDeliveryFee));
		
		estimate.setDeliveryFrequency(Frequency.Weekend);
		assertEquals(0.00001f, estimate.getFees(listDeliveryFee));
	}
	
	// Invalid negative flat fee situations	
	 @Test (expected = IllegalArgumentException.class)
	 public void negativeFlatFees() throws Exception {
		 List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
		 DeliveryFee weekdayFlat = new DeliveryFee(-30f, Frequency.Weekday, true);
		 listDeliveryFee.add(weekdayFlat);
	     Estimate estimate = new Estimate(0f, 500f, baseFrequency, false, subscriptionFrequency, Frequency.Weekday, service, user);
	     assertEquals(-30f, estimate.getFees(listDeliveryFee));
	 }
	 
	 // Invalid too high flat fee
	 @Test (expected = IllegalArgumentException.class)
	 public void tooHighFlatFees() throws Exception {
		 List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
		 DeliveryFee weekdayFlat = new DeliveryFee(2000.1f, Frequency.Weekday, true);
		 listDeliveryFee.add(weekdayFlat);
	     Estimate estimate = new Estimate(0f, 400f, baseFrequency, false, subscriptionFrequency, Frequency.Weekday, service, user);
	     assertEquals(2000.1f, estimate.getFees(listDeliveryFee));
	 }
	
	 
	 
	 // Test total estimated price as follows
	 
	 
	 /*   
	  * Because the Team 1 will test all discount parts, but now (before the due day 2/22 11:59pm) Team 1 has some implementation
	  * problems, we (Team 2) will set default discount value (assume the discount value must be correct) 
	  * and test our total estimate
	  * 
	  * we need to get estimate by this equation: 
	  * 	this.estimate = this.basePrice + this.getFees() - this.getDiscount()
	  */
	 
	 // Set default discount value 
	 float discount1 = 0f;
	 float discount2 = 100f;
	 
	 
	 // Test the calculate of the total estimate (do not cover too many detail tests because other unit tests will cover detail
	 // for every field in the estimate)
	 @Test
	 public void totalEstimate() throws Exception {
		 List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
		 DeliveryFee weekdayFlat = new DeliveryFee(0.1f, Frequency.Weekday, false);
		 DeliveryFee weekendFlat = new DeliveryFee(30f, Frequency.Weekend, true);
		 DeliveryFee holidayFlat = new DeliveryFee(0.3f, Frequency.Holiday, false);
		 DeliveryFee EmergencyFlat = new DeliveryFee(100f, Frequency.Emergency, true);
		 listDeliveryFee.add(weekdayFlat);
		 listDeliveryFee.add(weekendFlat);
		 listDeliveryFee.add(holidayFlat);
		 listDeliveryFee.add(EmergencyFlat);
		 
		 Estimate estimate1 = new Estimate(0f, 10f, baseFrequency, true, subscriptionFrequency, Frequency.Weekday, service, user);
		 Estimate estimate2 = new Estimate(0f, 200f, baseFrequency, false, subscriptionFrequency, Frequency.Weekend, service, user);
		 Estimate estimate3 = new Estimate(0f, 750f, baseFrequency, true, subscriptionFrequency, Frequency.Holiday, service, user);
		 Estimate estimate4 = new Estimate(0f, 800f, baseFrequency, false, subscriptionFrequency, Frequency.Emergency, service, user);
		 
		 // do need to consider the discount
		 float totalEstimate1 = estimate1.getBasePrice() + estimate1.getFees(listDeliveryFee) - discount1;
		 assertEquals(11f, totalEstimate1);
		 
		 // do not need to consider the discount (subscription is false)
		 float totalEstimate2 = estimate2.getBasePrice() + estimate2.getFees(listDeliveryFee);
		 assertEquals(230f, totalEstimate2);
		 
		 // do need to consider the discount
		 float totalEstimate3 = estimate3.getBasePrice() + estimate3.getFees(listDeliveryFee) - discount2;
		 assertEquals(875f, totalEstimate3);
		 
		 // do not need to consider the discount (subscription is false)
		 float totalEstimate4 = estimate4.getBasePrice() + estimate4.getFees(listDeliveryFee);
		 assertEquals(900f, totalEstimate4);
		 
	 }
	 
	 // Invalid base fee (negative)
	 @Test (expected = IllegalArgumentException.class)
	 public void invalidBaseFee() throws Exception {
		 List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
		 DeliveryFee weekdayFlat = new DeliveryFee(200f, Frequency.Weekday, true);
		 listDeliveryFee.add(weekdayFlat);
	     Estimate estimate = new Estimate(0f, -100f, baseFrequency, false, subscriptionFrequency, Frequency.Weekday, service, user);
	     assertEquals(100f, estimate.getFees(listDeliveryFee));
	 }
	
}

