package com.example.cs4500_sp19_noideainc.Test;

import static org.junit.Assert.*;

import org.junit.Test;
import com.example.cs4500_sp19_noideainc.models.SubscriptionDiscount;
import com.example.cs4500_sp19_noideainc.models.Frequency;

public class TsetDiscount {

	SubscriptionDiscount TestServiceSub = new SubscriptionDiscount();

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
	public void testBiweek() {
		
		float actual = TestServiceSub.getDiscount(Frequency.Biweekly, false, 5, 10.0f);
		assertEquals(0.8f, actual, 0.01f);
	}
	

}
