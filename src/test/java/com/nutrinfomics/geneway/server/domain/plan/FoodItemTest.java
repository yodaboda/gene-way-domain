package com.nutrinfomics.geneway.server.domain.plan;

import static org.junit.Assert.*;

import org.junit.Test;

import com.nutrinfomics.geneway.shared.FoodItemType;
import com.nutrinfomics.geneway.shared.MeasurementUnit;

public class FoodItemTest {

	@Test
	public void testNormalize() {
		FoodItem foodItem = new FoodItem(1, MeasurementUnit.GRAM, FoodItemType.FIG);

		FoodItem normalizedFoodItem = foodItem.normalize(.66F);

		assertEquals(.66F, normalizedFoodItem.getAmount(), .0006F);
		assertEquals(MeasurementUnit.GRAM, normalizedFoodItem.getMeasurementUnit());
		assertEquals(foodItem.getFoodType(), normalizedFoodItem.getFoodType());
		assertEquals(foodItem.getCycle().getCycleLength(), normalizedFoodItem.getCycle().getCycleLength());
	}

}
