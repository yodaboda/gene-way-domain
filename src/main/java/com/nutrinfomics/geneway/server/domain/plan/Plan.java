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
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.nutrinfomics.geneway.server.domain.EntityBase;
import com.nutrinfomics.geneway.server.domain.specification.AcceptAllSpecification;
import com.nutrinfomics.geneway.server.domain.specification.SnackOrderSpecification;
import com.nutrinfomics.geneway.shared.ActivitiesType;
import com.nutrinfomics.geneway.shared.SupplementType;

@Entity
public class Plan extends EntityBase implements Serializable {
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private SnackMenu snackMenu;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private MarkedSnackMenu todaysSnackMenu;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private SnackOrderSpecification snackOrderSpecification;

  @ElementCollection(targetClass = ActivitiesType.class)
  @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
  @CollectionTable(name = "plan_activities")
  @Column(name = "activities") // Column name in plan_activities
  private List<ActivitiesType> activities;

  @ElementCollection(targetClass = SupplementType.class)
  @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
  @CollectionTable(name = "plan_supplements")
  @Column(name = "supplements") // Column name in plan_supplements
  private List<SupplementType> supplements;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private PlanPreferences planPreferences;

  public Plan() {}

  public SnackOrderSpecification getSnackOrderSpecification() {
    return snackOrderSpecification;
  }

  public void setSnackOrderSpecification(SnackOrderSpecification snackOrderSpecification) {
    this.snackOrderSpecification = snackOrderSpecification;
  }

  public SnackMenu getSnackMenu() {
    return snackMenu;
  }

  public void setSnackMenu(SnackMenu snackMenu) {
    this.snackMenu = snackMenu;
  }

  public List<ActivitiesType> getActivities() {
    return activities;
  }

  public void setActivities(List<ActivitiesType> activities) {
    this.activities = activities;
  }

  public List<SupplementType> getSupplements() {
    return supplements;
  }

  public void setSupplements(List<SupplementType> supplements) {
    this.supplements = supplements;
  }

  public Plan(
      SnackMenu snackMenu,
      Vector<ActivitiesType> activities,
      Vector<SupplementType> supplements,
      PlanPreferences planPreferences) {
    this.snackMenu = snackMenu;
    this.activities = activities;
    this.planPreferences = planPreferences;
    snackOrderSpecification = new SnackOrderSpecification(snackMenu.size());
    for (int i = 0; i < snackMenu.size(); ++i) {
      snackOrderSpecification.getFoodOrderSpecification().add(new AcceptAllSpecification());
    }
  }

  public PlanPreferences getPlanPreferences() {
    return planPreferences;
  }

  public void setPlanPreferences(PlanPreferences planPreferences) {
    this.planPreferences = planPreferences;
  }

  public MarkedSnackMenu getTodaysSnackMenu() {
    return todaysSnackMenu;
  }

  public void setTodaysSnackMenu(MarkedSnackMenu todaysSnackMenu) {
    this.todaysSnackMenu = todaysSnackMenu;
  }
}

/*
 * †Dr Firas Swidan, PhD. frsswdn@gmail.com. firas.swidan@icloud.com.
 * https://www.linkedin.com/in/swidan
 * POBox  8125,  Nazareth  16480, Israel.
 * Public key: AAAAB3NzaC1yc2EAAAADAQABAAACAQD6Lt98LolwuA/aOcK0h91ECdeiyG3QKcUOT/CcMEPV64cpkv3jrLLGoag7YtzESZ3j7TLEd0WHZ/BZ9d+K2kRfzuuCdMMhrBwqP3YObbTbSIM6NjUNwbH403LLb3FuYApUt1EvC//w64UMm7h3fTo0vdyVuMuGnkRZuM6RRAXcODM4tni9ydd3ZQKN4inztkeH/sOoM77FStk8E2VYbljUQdY39zlRoZwUqNdKzwD3T2G00tmROlTZ6K5L8i68Zqt6s0XNS6XQvS3zXe0fI6UwuetnDrcVr1Yb8y2T8lfjMG9+9L2aKPoUOlOMMcyqM+oKVvRUOSdrzmtKOljnYC7TqzvsKrfXHvHlqHxxhPp1K7B/YWrHwCDbqp02dXdIaXkkHCIqKFNaY06HEWt4obDxppVhC8IabSb55LQVCCT7J4TFbwp6rID2+Y1L7NEvR3v3oaWSlQIZ+WSG04mwh9/7gRCt7XUoqmEXCCPoHqZXq5sWv193XA57pD5gKoX7Rf2i6UdbduNTMIhQMqcWIaPMBFwxUv/LRQCHnS+mlW2GnIHIHHGS/S46MurZ6BMvcb7fEz/NorVxvh3DbUaVTteMYcikH0y5sPmGECB1d99ENBBSEX6diI+PneFp2sOouQ6gOBWy6WAt3spGfLTOFMPo3bMV/UpktkQPpXkmfd1esQ==
 */
