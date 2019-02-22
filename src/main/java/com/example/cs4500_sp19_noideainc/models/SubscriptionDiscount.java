package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="subscription_discount")
public class SubscriptionDiscount {
	@Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Integer id;
	  private float discount;
	  private Frequency frequency;
	  private boolean flat;
	  private float flatAmount;
	  private float basePrice;
	  
	public float getDiscount(Frequency frequency, boolean flat, float flatAmount, float basePrice) {
		if (flat == true) {
			return flatAmount;
		} else if (frequency == Frequency.Onetime) {
			return discount = 0.0f;
		} else if (frequency == Frequency.Hourly) {
			return discount = basePrice * 0.01f;
		} else if (frequency == Frequency.Weekly) {
			return discount = basePrice * 0.05f;
		} else if (frequency == Frequency.Biweekly) {
			return discount = basePrice * 0.08f;
		} else if (frequency == Frequency.Monthly) {
			return discount = basePrice * 0.010f;
		} else if (frequency == Frequency.Yearly) {
			return discount = basePrice * 0.15f;
		} else if (frequency == Frequency.Weekday) {
			return discount = basePrice * 0.07f;
		} else if (frequency == Frequency.Weekend) {
			return discount = basePrice * 0.04f;
		} else if (frequency == Frequency.Emergency) {
			return discount = basePrice * (-0.05f);
		} else if (frequency == Frequency.Holiday) {
			return discount = basePrice * (-0.25f);
		}
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public boolean isFlat() {
		return flat;
	}
	public void setFlat(boolean flat) {
		this.flat = flat;
	}

	public float getFlatAmount() {
		return flatAmount;
	}

	public void setFlatAmount(int flatAmount) {
		this.flatAmount = flatAmount;
	}
	public float getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}
}
