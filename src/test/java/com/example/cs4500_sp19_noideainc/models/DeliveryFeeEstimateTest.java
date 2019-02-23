
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

	// Delivery fee objects for testing holidays fees
	// This includes both flat fees and percentage fees.
	private final DeliveryFee holidayPercentageFee1 = new DeliveryFee(0f, Frequency.Holiday, false);
	private final DeliveryFee holidayPercentageFee2 = new DeliveryFee(0.5f, Frequency.Holiday, false);
	private final DeliveryFee holidayFlatFee1 = new DeliveryFee(500f, Frequency.Holiday, true);

	private final DeliveryFee weekendFlatFee1 = new DeliveryFee(400f, Frequency.Weekend, true);
	private final DeliveryFee weekendFlatFee2 = new DeliveryFee(300f, Frequency.Weekend, true);
	private final DeliveryFee weekendPercentageFee1 = new DeliveryFee(0.4f, Frequency.Weekend, false);


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
		DeliveryFee weekdayFlat1 = new DeliveryFee(4.555f, Frequency.Weekday, true);
		DeliveryFee weekdayFlat2 = new DeliveryFee(15.7111f, Frequency.Weekend, true);
		DeliveryFee weekdayFlat3 = new DeliveryFee(1900.8f, Frequency.Holiday, true);
		listDeliveryFee.add(weekdayFlat1);
		listDeliveryFee.add(weekdayFlat2);
		listDeliveryFee.add(weekdayFlat3);
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
		DeliveryFee weekdayFlat2 = new DeliveryFee(2000f, Frequency.Weekend, true);
		DeliveryFee weekdayFlat3 = new DeliveryFee(1999.8f, Frequency.Holiday, true);
		listDeliveryFee.add(weekdayFlat2);
		listDeliveryFee.add(weekdayFlat3);
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
		DeliveryFee weekdayFlat2 = new DeliveryFee(0.00001f, Frequency.Weekend, true);
		listDeliveryFee.add(weekdayFlat1);
		listDeliveryFee.add(weekdayFlat2);
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

	@Test
	// Tests regular holidays delivery fees (not too big)
	public void testRegularlHolidaysFees() throws Exception {
		// add various holiday delivery fees to the list
		List<DeliveryFee> listHolidayDeliveryFee = new ArrayList<DeliveryFee>();
		listHolidayDeliveryFee.add(this.holidayPercentageFee1);
		listHolidayDeliveryFee.add(this.holidayPercentageFee2);
		listHolidayDeliveryFee.add(this.holidayFlatFee1);

		// Base price here is 800. Since our team does not deal with discounts,
		// we will keep base frequency and subscription frequency as weekday for now.
		Estimate estimate = new Estimate(0, 800f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Holiday, service, user);

		assertEquals(0, estimate.getFees(listHolidayDeliveryFee), 0.0001f);
		listHolidayDeliveryFee.remove(0);
		assertEquals(400, estimate.getFees(listHolidayDeliveryFee), 0.0001f);
		listHolidayDeliveryFee.remove(0);
		assertEquals(500, estimate.getFees(listHolidayDeliveryFee), 0.0001f);
		listHolidayDeliveryFee.remove(0);
	}
	
}





