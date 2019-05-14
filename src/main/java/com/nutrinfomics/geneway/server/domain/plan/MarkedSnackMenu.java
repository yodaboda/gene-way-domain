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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.nutrinfomics.geneway.server.domain.EntityBase;
import com.nutrinfomics.geneway.server.domain.specification.AbstractFoodSpecification;
import com.nutrinfomics.geneway.server.domain.specification.AcceptAllSpecification;
import com.nutrinfomics.geneway.server.domain.specification.SnackOrderSpecification;

@Entity
public class MarkedSnackMenu extends EntityBase {

  // this is a set, because when using a list there were many duplicates
  @OneToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.ALL})
  private Set<MarkedSnack> markedSnacks;

  private String date;

  @OneToOne(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.ALL})
  private MarkedSnack currentSnack;

  public MarkedSnackMenu() {}

  public MarkedSnackMenu(String date, List<Snack> snacks) {
    this.setDate(date);
    setSnacks(snacks);
  }

  public MarkedSnack calcCurrentSnack(SnackOrderSpecification snackOrderSpecification) {
    if (currentSnack != null && !currentSnack.isMarked()) return currentSnack;

    if (getMarkedSnacks().isEmpty()) {
      setCurrentSnack(null);
      return null;
    }

    List<AbstractFoodSpecification> foodOrderSpecification =
        snackOrderSpecification.getFoodOrderSpecification();
    int i = foodOrderSpecification.size() - getMarkedSnacks().size();
    assert i >= 0;
    AbstractFoodSpecification currentFoodSpecification = foodOrderSpecification.get(i);

    MarkedSnack foundSnack = null;
    for (MarkedSnack markedSnack : getMarkedSnacks()) {
      if (markedSnack.isMarked()) continue;
      if (isSnackSpecificationFitting(currentFoodSpecification, markedSnack)) {
        boolean found = true;
        for (int j = i + 1; j < foodOrderSpecification.size(); ++j) {
          AbstractFoodSpecification futureFoodSpecification = foodOrderSpecification.get(j);
          if (futureFoodSpecification instanceof AcceptAllSpecification) continue;
          else if (isSnackSpecificationFitting(futureFoodSpecification, markedSnack)) {
            found = false;
            break;
          }
        }
        if (found) {
          foundSnack = markedSnack;
          break;
        }
      }
    }
    if (foundSnack != null) getMarkedSnacks().remove(foundSnack);
    setCurrentSnack(foundSnack);
    return foundSnack;
  }

  private boolean isSnackSpecificationFitting(
      AbstractFoodSpecification abstractFoodSpecification, MarkedSnack markedSnack) {
    Snack snack = markedSnack.getSnack();
    if (snack instanceof GeneralVaryingSnack) {
      for (Snack s : ((GeneralVaryingSnack) snack).getSnacks()) {
        if (isSnackSubsSpecificationFitting(abstractFoodSpecification, s)) return true;
      }
    } else {
      if (isSnackSubsSpecificationFitting(abstractFoodSpecification, snack)) return true;
    }
    return false;
  }

  private boolean isSnackSubsSpecificationFitting(
      AbstractFoodSpecification abstractFoodSpecification, Snack snack) {
    for (FoodItem foodItem : snack.getFoodItems()) {
      if (abstractFoodSpecification.qualifies(foodItem.getFoodType())) {
        return true;
      }
    }
    return false;
  }

  public MarkedSnack getCurrentSnack() {
    return currentSnack;
  }

  public void setCurrentSnack(MarkedSnack currentSnack) {
    this.currentSnack = currentSnack;
  }

  public Set<MarkedSnack> getMarkedSnacks() {
    return markedSnacks;
  }

  public void setMarkedSnacks(Set<MarkedSnack> markedSnacks) {
    this.markedSnacks = markedSnacks;
  }

  public void setSnacks(List<Snack> snacks) {
    this.markedSnacks = new HashSet<MarkedSnack>(snacks.size());
    for (Snack snack : snacks) {

      this.markedSnacks.add(new MarkedSnack(snack));
    }
  }

  @Override
  public boolean equals(Object otherMarkedSnackMenu) {
    if (otherMarkedSnackMenu == null || this.getClass() != otherMarkedSnackMenu.getClass()) {
      return false;
    }
    MarkedSnackMenu markedSnackMenu = (MarkedSnackMenu) otherMarkedSnackMenu;
    return getDate().equals(markedSnackMenu.getDate())
        && getMarkedSnacks().equals(markedSnackMenu.getMarkedSnacks());
  }

  @Override
  public int hashCode() {
    return getDate().hashCode() + getMarkedSnacks().hashCode();
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}

/*
 * †Dr Firas Swidan, PhD. frsswdn@gmail.com. firas.swidan@icloud.com.
 * https://www.linkedin.com/in/swidan
 * POBox  8125,  Nazareth  16480, Israel.
 * Public key: AAAAB3NzaC1yc2EAAAADAQABAAACAQD6Lt98LolwuA/aOcK0h91ECdeiyG3QKcUOT/CcMEPV64cpkv3jrLLGoag7YtzESZ3j7TLEd0WHZ/BZ9d+K2kRfzuuCdMMhrBwqP3YObbTbSIM6NjUNwbH403LLb3FuYApUt1EvC//w64UMm7h3fTo0vdyVuMuGnkRZuM6RRAXcODM4tni9ydd3ZQKN4inztkeH/sOoM77FStk8E2VYbljUQdY39zlRoZwUqNdKzwD3T2G00tmROlTZ6K5L8i68Zqt6s0XNS6XQvS3zXe0fI6UwuetnDrcVr1Yb8y2T8lfjMG9+9L2aKPoUOlOMMcyqM+oKVvRUOSdrzmtKOljnYC7TqzvsKrfXHvHlqHxxhPp1K7B/YWrHwCDbqp02dXdIaXkkHCIqKFNaY06HEWt4obDxppVhC8IabSb55LQVCCT7J4TFbwp6rID2+Y1L7NEvR3v3oaWSlQIZ+WSG04mwh9/7gRCt7XUoqmEXCCPoHqZXq5sWv193XA57pD5gKoX7Rf2i6UdbduNTMIhQMqcWIaPMBFwxUv/LRQCHnS+mlW2GnIHIHHGS/S46MurZ6BMvcb7fEz/NorVxvh3DbUaVTteMYcikH0y5sPmGECB1d99ENBBSEX6diI+PneFp2sOouQ6gOBWy6WAt3spGfLTOFMPo3bMV/UpktkQPpXkmfd1esQ==
 */
