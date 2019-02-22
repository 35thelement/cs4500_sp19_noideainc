
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
	
	
	@Test
	// test if the calculation of different flat fees are correct
	public void testFlatFees() {
		this.listDeliveryFee1.add(this.deliveryFee1);
		this.listDeliveryFee1.add(this.deliveryFee2);
		this.listDeliveryFee1.add(this.deliveryFee3);
		this.listDeliveryFee1.add(this.deliveryFee4);
		Estimate estimate = new Estimate(0f, 750f, Frequency.Weekday, false, Frequency.Weekday, Frequency.Weekday, service, user);
		// test no frequency are added on the base price
		assertEquals(0f, estimate.getFees(this.listDeliveryFee1));
		
		// test delivery frequency with fat fee
		estimate.setDeliveryFrequency(Frequency.Weekend);
		assertEquals(75f, estimate.getFees(this.listDeliveryFee1));
		estimate.setDeliveryFrequency(Frequency.Holiday);
		assertEquals(150f, estimate.getFees(this.listDeliveryFee1));
		estimate.setDeliveryFrequency(Frequency.Emergency);
		assertEquals(225f, estimate.getFees(this.listDeliveryFee1));
		
	}
}
