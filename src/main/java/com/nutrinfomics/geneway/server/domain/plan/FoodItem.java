package com.nutrinfomics.geneway.server.domain.plan;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.nutrinfomics.geneway.server.domain.EntityBase;
import com.nutrinfomics.geneway.shared.FoodItemType;
import com.nutrinfomics.geneway.shared.MeasurementUnit;

@Entity
public class FoodItem extends EntityBase implements Serializable {
  private float amount;

  @Enumerated(EnumType.STRING)
  private MeasurementUnit measurementUnit;

  @Enumerated(EnumType.STRING)
  private FoodItemType foodType;

  @Embedded private ArbitraryCycle cycle = new ArbitraryCycle();

  public FoodItem() {}

  public FoodItem(String foodType) {
    this(FoodItemType.valueOf(foodType.toUpperCase()));
  }

  public FoodItem(FoodItem foodItem) {
    this(
        foodItem.getAmount(),
        foodItem.getMeasurementUnit(),
        foodItem.getFoodType(),
        new ArbitraryCycle(foodItem.getCycle()));
  }

  public FoodItem(FoodItemType foodType) {
    this(0, MeasurementUnit.GRAM, foodType);
  }

  public FoodItem(float amount, MeasurementUnit measurementUnit, FoodItemType foodType) {
    this(amount, measurementUnit, foodType, new ArbitraryCycle(7));
  }

  public FoodItem(
      float amount, MeasurementUnit measurementUnit, FoodItemType foodType, ArbitraryCycle cycle) {
    setAmount(amount);
    setMeasurementUnit(measurementUnit);
    setFoodType(foodType);
    setCycle(cycle);
  }

  public FoodItem(String[] data) {
    setFoodType(FoodItemType.valueOf(data[0]));
    setMeasurementUnit(MeasurementUnit.valueOf(data[1]));
    setAmount(Float.parseFloat(data[2]));
    getCycle().setCycleLength(Integer.parseInt(data[3]));
  }

  public float getAmount() {
    return amount;
  }

  public float getWeeklyNormalizedAmount() {
    return amount * getCycle().getCycleLength() / 7.0F;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public MeasurementUnit getMeasurementUnit() {
    return measurementUnit;
  }

  public void setMeasurementUnit(MeasurementUnit measurementUnit) {
    this.measurementUnit = measurementUnit;
  }

  public FoodItemType getFoodType() {
    return foodType;
  }

  public void setFoodType(FoodItemType foodType) {
    this.foodType = foodType;
  }

  @Override
  public String toString() {
    return foodType + " " + measurementUnit + " " + amount + " " + cycle.getCycleLength();
  }

  public String[] toStrings() {
    return new String[] {
      foodType + "", measurementUnit + "", amount + "", cycle.getCycleLength() + ""
    };
  }

  public ArbitraryCycle getCycle() {
    return cycle;
  }

  public void setCycle(ArbitraryCycle cycle) {
    this.cycle = cycle;
  }
  
  public FoodItem normalize(float normalizingFactor) {
	  FoodItem normalizedFoodItem = new FoodItem(this.getAmount() * normalizingFactor, this.getMeasurementUnit(),
			  									this.getFoodType(), new ArbitraryCycle(this.getCycle()));
	  return normalizedFoodItem;
  }
}
