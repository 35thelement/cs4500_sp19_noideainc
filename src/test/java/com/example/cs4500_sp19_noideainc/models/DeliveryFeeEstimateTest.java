

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


	@Test
	// Tests regular (not too big) holidays delivery fees (both flat and percentage)
	public void testRegularlHolidaysFees() throws Exception {
		// add various holiday delivery fees to the list
		List<DeliveryFee> listHolidayDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee holidayPercentageFee1 = new DeliveryFee(0f, Frequency.Holiday, false);
		DeliveryFee holidayPercentageFee2 = new DeliveryFee(0.5f, Frequency.Holiday, false);
		DeliveryFee holidayFlatFee1 = new DeliveryFee(500f, Frequency.Holiday, true);
		listHolidayDeliveryFee.add(holidayPercentageFee1);
		listHolidayDeliveryFee.add(holidayPercentageFee2);
		listHolidayDeliveryFee.add(holidayFlatFee1);

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

	@Test
	// Tests big holiday percentage fees that are below the fee limit (five times the base price)
	public void testBigHolidaysPercentageFees() throws Exception {
		List<DeliveryFee> listHolidayDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee holidayPercentageBigFee = new DeliveryFee(3, Frequency.Holiday, false);
		DeliveryFee holidayPercentageBigFee1 = new DeliveryFee(5, Frequency.Holiday, false);
		listHolidayDeliveryFee.add(holidayPercentageBigFee);
		listHolidayDeliveryFee.add(holidayPercentageBigFee1);

		Estimate estimate = new Estimate(0f, 850f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Holiday, service, user);

		assertEquals(2550f, estimate.getFees(listHolidayDeliveryFee), 0.0001f);
		listHolidayDeliveryFee.remove(0);
		assertEquals(4250f, estimate.getFees(listHolidayDeliveryFee), 0.0001f);
		listHolidayDeliveryFee.remove(0);
	}

	@Test (expected = IllegalArgumentException.class)
	// Tests big holiday percentage fees that exceed the fee limit (bigger than five times the base price)
	public void testInvalidBigHolidaysPercentageFees() throws Exception {
		List<DeliveryFee> listHolidayDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee holidayPercentageInvalidBigFee = new DeliveryFee(5.01f, Frequency.Holiday, false);
		listHolidayDeliveryFee.add(holidayPercentageInvalidBigFee);

		Estimate estimate = new Estimate(0f, 900f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Holiday, service, user);

		assertEquals(4509f, estimate.getFees(listHolidayDeliveryFee), 0.0001f);
	}

	@Test (expected = IllegalArgumentException.class)
	// Tests throwing an exception if delivery frequency other than Holiday is inputed
	public void testRandomFrequencyForHolidaysFees() throws Exception {
		List<DeliveryFee> listHolidayDeliveryFee = new ArrayList<DeliveryFee>();
		// Emergency fee cannot be used to calculate an estimate for a holiday fee
		DeliveryFee emergencyFlatInvalidBigFee = new DeliveryFee(300f, Frequency.Emergency, false);
		listHolidayDeliveryFee.add(emergencyFlatInvalidBigFee);

		Estimate estimate = new Estimate(0f, 900f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Holiday, service, user);

		assertEquals(300f, estimate.getFees(listHolidayDeliveryFee), 0.0001f);
	}

	@Test (expected = NullPointerException.class)
	// Tests throwing an exception if delivery frequency is null
	public void testNullFrequencyForHolidaysFees() throws Exception {
		List<DeliveryFee> listHolidayDeliveryFee = new ArrayList<DeliveryFee>();
		// null frequency cannot be used to calculate an estimate for a holiday fee
		DeliveryFee nullPercentageInvalidBigFee = new DeliveryFee(0.65f, null, false);
		listHolidayDeliveryFee.add(nullPercentageInvalidBigFee);

		Estimate estimate = new Estimate(0f, 950f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Holiday, service, user);

		assertEquals(617.5f, estimate.getFees(listHolidayDeliveryFee), 0.0001f);
	}

	@Test (expected = IllegalArgumentException.class)
	// Tests throwing an exception for a negative delivery fee (for holiday frequency)
	public void testNegativeHolidaysPercentageFees() throws Exception {
		List<DeliveryFee> listHolidayDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee holidaysPercentageFee = new DeliveryFee(-0.25f, Frequency.Weekend, false);
		listHolidayDeliveryFee.add(holidaysPercentageFee);

		Estimate estimate = new Estimate(0f, 5000f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Weekend, service, user);

		assertEquals(-1250f, estimate.getFees(listHolidayDeliveryFee), 0.0001f);
	}

	@Test
	// Tests regular (not too big) weekends delivery fees (both flat and percentage)
	public void testRegularlWeekendsFees() throws Exception {
		List<DeliveryFee> listWeekendDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee weekendFlatFee1 = new DeliveryFee(400f, Frequency.Weekend, true);
		DeliveryFee weekendFlatFee2 = new DeliveryFee(300f, Frequency.Weekend, true);
		DeliveryFee weekendPercentageFee1 = new DeliveryFee(0.4f, Frequency.Weekend, false);
		listWeekendDeliveryFee.add(weekendFlatFee1);
		listWeekendDeliveryFee.add(weekendFlatFee2);
		listWeekendDeliveryFee.add(weekendPercentageFee1);

		// Base price here is 800. Since our team does not deal with discounts,
		// we will keep base frequency and subscription frequency as weekday for now.
		Estimate estimate = new Estimate(0, 800f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Weekend, service, user);

		// Weekend fees
		assertEquals(400f, estimate.getFees(listWeekendDeliveryFee), 0.0001f);
		listWeekendDeliveryFee.remove(0);
		assertEquals(300f, estimate.getFees(listWeekendDeliveryFee), 0.0001f);
		listWeekendDeliveryFee.remove(0);
		assertEquals(320f, estimate.getFees(listWeekendDeliveryFee), 0.0001f);
		listWeekendDeliveryFee.remove(0);
	}

	@Test
	// Tests big weekends percentage fees that are below the fee limit (five times the base price)
	public void testBigWeekendsPercentageFees() throws Exception {
		List<DeliveryFee> listWeekendDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee weekendPercentageBigFee = new DeliveryFee(2.5f, Frequency.Weekend, false);
		DeliveryFee weekendPercentageBigFee1 = new DeliveryFee(5, Frequency.Weekend, false);
		DeliveryFee weekendPercentageBigFee2 = new DeliveryFee(4.99f, Frequency.Weekend, false);
		listWeekendDeliveryFee.add(weekendPercentageBigFee);
		listWeekendDeliveryFee.add(weekendPercentageBigFee1);
		listWeekendDeliveryFee.add(weekendPercentageBigFee2);

		Estimate estimate = new Estimate(0f, 550f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Weekend, service, user);

		assertEquals(1375f, estimate.getFees(listWeekendDeliveryFee), 0.0001f);
		listWeekendDeliveryFee.remove(0);
		assertEquals(2750f, estimate.getFees(listWeekendDeliveryFee), 0.0001f);
		listWeekendDeliveryFee.remove(0);
		assertEquals(2744.4998f, estimate.getFees(listWeekendDeliveryFee), 0.0001f);
		listWeekendDeliveryFee.remove(0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	// Tests big weekends percentage fees that exceed the fee limit (bigger than five times the base price)
	public void testInvalidBigWeekendsPercentageFees() throws Exception {
		List<DeliveryFee> listWeekendDeliveryFee = new ArrayList<DeliveryFee>();
		
		DeliveryFee weekendPercentageInvalidBigFee = new DeliveryFee(5.01f, Frequency.Weekend, false);
		listWeekendDeliveryFee.add(weekendPercentageInvalidBigFee);
		
		Estimate estimate = new Estimate(0f, 900f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Weekend, service, user);

		assertEquals(4509f, estimate.getFees(listWeekendDeliveryFee), 0.0001f);
		listWeekendDeliveryFee.remove(0);
	}

	@Test (expected = IllegalArgumentException.class)
	// Tests throwing an exception if delivery frequency other than Weekend is inputed
	public void testRandomFrequencyForWeekendsFees() throws Exception {
		List<DeliveryFee> listWeekendDeliveryFee = new ArrayList<DeliveryFee>();
		// Emergency fee cannot be used to calculate an estimate for a weekend fee
		DeliveryFee dailyPercentageInvalidBigFee = new DeliveryFee(0.55f, Frequency.Daily, false);
		listWeekendDeliveryFee.add(dailyPercentageInvalidBigFee);

		Estimate estimate = new Estimate(0f, 900f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Weekend, service, user);

		assertEquals(300f, estimate.getFees(listWeekendDeliveryFee), 0.0001f);
	}

	@Test (expected = NullPointerException.class)
	// Tests throwing an exception if delivery frequency is null (for Weekend frequency)
	public void testNullFrequencyForWeekendsFees() throws Exception {
		List<DeliveryFee> listWeekendDeliveryFee = new ArrayList<DeliveryFee>();
		// null frequency cannot be used to calculate an estimate for a weekend fee
		DeliveryFee nullPercentageInvalidBigFee = new DeliveryFee(0.4f, null, false);
		listWeekendDeliveryFee.add(nullPercentageInvalidBigFee);

		Estimate estimate = new Estimate(0f, 950f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Weekend, service, user);

		assertEquals(380f, estimate.getFees(listWeekendDeliveryFee), 0.0001f);
	}

	@Test (expected = IllegalArgumentException.class)
	// Tests throwing an exception for a negative delivery fee (for weekend frequency)
	public void testNegativeWeekendsPercentageFees() throws Exception {
		List<DeliveryFee> listWeekendDeliveryFee = new ArrayList<DeliveryFee>();
		DeliveryFee weekendsPercentageFee = new DeliveryFee(-0.9f, Frequency.Weekend, false);
		listWeekendDeliveryFee.add(weekendsPercentageFee);

		Estimate estimate = new Estimate(0f, 115000f, baseFrequency, false, 
				subscriptionFrequency, Frequency.Weekend, service, user);

		assertEquals(-103500f, estimate.getFees(listWeekendDeliveryFee), 0.0001f);
	}
}
