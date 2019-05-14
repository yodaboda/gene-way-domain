/*
 * Copyright 2019 Firas Swidan†
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

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
    FoodItem normalizedFoodItem =
        new FoodItem(
            this.getAmount() * normalizingFactor,
            this.getMeasurementUnit(),
            this.getFoodType(),
            new ArbitraryCycle(this.getCycle()));
    return normalizedFoodItem;
  }
}

/*
 * †Dr Firas Swidan, PhD. frsswdn@gmail.com. firas.swidan@icloud.com.
 * https://www.linkedin.com/in/swidan
 * POBox  8125,  Nazareth  16480, Israel.
 * Public key: AAAAB3NzaC1yc2EAAAADAQABAAACAQD6Lt98LolwuA/aOcK0h91ECdeiyG3QKcUOT/CcMEPV64cpkv3jrLLGoag7YtzESZ3j7TLEd0WHZ/BZ9d+K2kRfzuuCdMMhrBwqP3YObbTbSIM6NjUNwbH403LLb3FuYApUt1EvC//w64UMm7h3fTo0vdyVuMuGnkRZuM6RRAXcODM4tni9ydd3ZQKN4inztkeH/sOoM77FStk8E2VYbljUQdY39zlRoZwUqNdKzwD3T2G00tmROlTZ6K5L8i68Zqt6s0XNS6XQvS3zXe0fI6UwuetnDrcVr1Yb8y2T8lfjMG9+9L2aKPoUOlOMMcyqM+oKVvRUOSdrzmtKOljnYC7TqzvsKrfXHvHlqHxxhPp1K7B/YWrHwCDbqp02dXdIaXkkHCIqKFNaY06HEWt4obDxppVhC8IabSb55LQVCCT7J4TFbwp6rID2+Y1L7NEvR3v3oaWSlQIZ+WSG04mwh9/7gRCt7XUoqmEXCCPoHqZXq5sWv193XA57pD5gKoX7Rf2i6UdbduNTMIhQMqcWIaPMBFwxUv/LRQCHnS+mlW2GnIHIHHGS/S46MurZ6BMvcb7fEz/NorVxvh3DbUaVTteMYcikH0y5sPmGECB1d99ENBBSEX6diI+PneFp2sOouQ6gOBWy6WAt3spGfLTOFMPo3bMV/UpktkQPpXkmfd1esQ==
 */
