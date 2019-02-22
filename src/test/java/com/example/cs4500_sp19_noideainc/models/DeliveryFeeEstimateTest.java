
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
	
//	
//	@Test
//	// Test regular situations for the calculation of different flat fees
//	public void testFlatFees() {
//		// a list of deliveryFees
//		List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
//		listDeliveryFee.add(this.weekdayFlat);
//		listDeliveryFee.add(this.weekendFlat);
//		listDeliveryFee.add(this.holidayFlat);
//		listDeliveryFee.add(this.EmergencyFlat);
//		
//		Estimate estimate = new Estimate(0f, 750f, baseFrequency, false, subscriptionFrequency, 
//				Frequency.Weekday, service, user);
//		
//		// Test no frequency are added on the base price
//		assertEquals(0f, estimate.getFees(listDeliveryFee));
//		
//		// Test delivery frequency with different fat fee
//		estimate.setDeliveryFrequency(Frequency.Weekend);
//		assertEquals(75f, estimate.getFees(listDeliveryFee));
//		
//		estimate.setDeliveryFrequency(Frequency.Holiday);
//		assertEquals(150f, estimate.getFees(listDeliveryFee));
//		
//		estimate.setDeliveryFrequency(Frequency.Emergency);
//		assertEquals(225f, estimate.getFees(listDeliveryFee));
//		
//	}
//	
//	@Test
//	// Test special situations for the calculation of different flat fees
//	public void testFlatFees1() {
//		// a list of deliveryFees
//		List<DeliveryFee> listDeliveryFee = new ArrayList<DeliveryFee>();
//		DeliveryFee weekdayFlat1 = new DeliveryFee(15, Frequency.Weekday, true);
//		listDeliveryFee.add(weekdayFlat1);
//		Estimate estimate = new Estimate(0f, 500f, baseFrequency, false, subscriptionFrequency, 
//				Frequency.Weekday, service, user);
//		assertEquals(true, estimate.getFees(listDeliveryFee) > 0);
//	}
	
	
}




