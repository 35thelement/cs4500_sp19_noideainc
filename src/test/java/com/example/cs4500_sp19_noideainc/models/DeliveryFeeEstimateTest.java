
package com.example.cs4500_sp19_noideainc.models;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeliveryFeeEstimateTest {
	// default service and user
	User user = new User();
	Service service = new Service();
	
	// all current frequency and related deliverFee class (all of them are flat fees)
	private final DeliveryFee deliveryFee1 = new DeliveryFee(0, Frequency.Weekday, true);
	private final DeliveryFee deliveryFee2 = new DeliveryFee(75f, Frequency.Weekend, true);
	private final DeliveryFee deliveryFee3 = new DeliveryFee(150f, Frequency.Holiday, true);
	private final DeliveryFee deliveryFee4 = new DeliveryFee(225f, Frequency.Emergency, true);
	
	private List<DeliveryFee> listDeliveryFee1 = new ArrayList<DeliveryFee>();
	
	/*
	 * Initialize the list of DeliveryFee class
	 */
	void init() {
		this.listDeliveryFee1.add(this.deliveryFee1);
		this.listDeliveryFee1.add(this.deliveryFee2);
		this.listDeliveryFee1.add(this.deliveryFee3);
		this.listDeliveryFee1.add(this.deliveryFee4);
	}
	
	@Test
	// test if the calculation of different flat fees are correct
	public void testFlatFees() {
		init();
		Estimate estimate = new Estimate(0f, 750f, Frequency.Weekday, false, Frequency.Weekday, Frequency.Weekday, service, user);
		// test no frequency are added on the base price
		assertEquals(750f, estimate.getFees(this.listDeliveryFee1));
		
		// test base frequency with fat fee
		estimate.setBaseFrequency(Frequency.Weekend);
		assertEquals(825f, estimate.getFees(this.listDeliveryFee1));
		estimate.setBaseFrequency(Frequency.Holiday);
		assertEquals(900f, estimate.getFees(this.listDeliveryFee1));
		estimate.setBaseFrequency(Frequency.Emergency);
		assertEquals(975f, estimate.getFees(this.listDeliveryFee1));
		
		// test for another base estimate
		estimate.setBasePrice(500f);
		estimate.setBaseFrequency(Frequency.Weekend);
		assertEquals(575f, estimate.getFees(this.listDeliveryFee1));
		
		// test subscription frequency with fat fee
		estimate.setSubscription(true);
		estimate.setBaseFrequency(Frequency.Weekday); // make the base frequency remain 0
		estimate.setSubscriptionFrequency(Frequency.Holiday);
		assertEquals(350f, estimate.getFees(this.listDeliveryFee1));
		
		// test delivery frequency with fat fee
		estimate.setSubscription(false);
		estimate.setSubscriptionFrequency(Frequency.Weekday); // make the subscription frequency remain 0
		estimate.setDeliveryFrequency(Frequency.Emergency);
		assertEquals(725f, estimate.getFees(this.listDeliveryFee1));
		
		// test base frequency with fat fee and delivery frequency with fat fee and
		estimate.setBaseFrequency(Frequency.Weekend);
		estimate.setDeliveryFrequency(Frequency.Weekend);
		assertEquals(650f, estimate.getFees(this.listDeliveryFee1));
		
	}
}
