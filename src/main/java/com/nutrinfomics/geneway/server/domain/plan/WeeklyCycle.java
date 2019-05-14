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

import java.util.List;
import java.util.Vector;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class WeeklyCycle extends ArbitraryCycle {
  private static final int CYCLE_LENGTH = 7;

  @Transient private List<WeeklyBehaving> weeklyBehavings = new Vector<>();

  private static WeeklyCycle instance;

  public static WeeklyCycle getInstance() {
    if (instance == null) {
      // try to retrieve the only weekly cycle instance
      //			EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
      //			TypedQuery<WeeklyCycle> query = entityManager.createQuery("SELECT s FROM WeeklyCycle",
      // WeeklyCycle.class);
      //			try{
      //				instance = query.getSingleResult();
      //			}
      //			catch(Exception e){
      //				instance = new WeeklyCycle();
      //			}
      instance = new WeeklyCycle();
    }

    return instance;
  }

  private WeeklyCycle() {
    super(CYCLE_LENGTH);
  }

  @Override
  public void advanceBySingleUnit() {
    super.advanceBySingleUnit();
    if (getRemainingLength() < 1) {
      setRemainingLength(CYCLE_LENGTH);
      ; // restart
      for (WeeklyBehaving weeklyBehaving : weeklyBehavings) weeklyBehaving.weeklyReset();
    }
    for (WeeklyBehaving weeklyBehaving : weeklyBehavings) weeklyBehaving.nextDay();
  }

  public void addWeeklyBehaving(WeeklyBehaving weeklyBehaving) {
    weeklyBehavings.add(weeklyBehaving);
  }
}


/*
 * †Dr Firas Swidan, PhD. frsswdn@gmail.com. firas.swidan@icloud.com.
 * https://www.linkedin.com/in/swidan
 * POBox  8125,  Nazareth  16480, Israel.
 * Public key: AAAAB3NzaC1yc2EAAAADAQABAAACAQD6Lt98LolwuA/aOcK0h91ECdeiyG3QKcUOT/CcMEPV64cpkv3jrLLGoag7YtzESZ3j7TLEd0WHZ/BZ9d+K2kRfzuuCdMMhrBwqP3YObbTbSIM6NjUNwbH403LLb3FuYApUt1EvC//w64UMm7h3fTo0vdyVuMuGnkRZuM6RRAXcODM4tni9ydd3ZQKN4inztkeH/sOoM77FStk8E2VYbljUQdY39zlRoZwUqNdKzwD3T2G00tmROlTZ6K5L8i68Zqt6s0XNS6XQvS3zXe0fI6UwuetnDrcVr1Yb8y2T8lfjMG9+9L2aKPoUOlOMMcyqM+oKVvRUOSdrzmtKOljnYC7TqzvsKrfXHvHlqHxxhPp1K7B/YWrHwCDbqp02dXdIaXkkHCIqKFNaY06HEWt4obDxppVhC8IabSb55LQVCCT7J4TFbwp6rID2+Y1L7NEvR3v3oaWSlQIZ+WSG04mwh9/7gRCt7XUoqmEXCCPoHqZXq5sWv193XA57pD5gKoX7Rf2i6UdbduNTMIhQMqcWIaPMBFwxUv/LRQCHnS+mlW2GnIHIHHGS/S46MurZ6BMvcb7fEz/NorVxvh3DbUaVTteMYcikH0y5sPmGECB1d99ENBBSEX6diI+PneFp2sOouQ6gOBWy6WAt3spGfLTOFMPo3bMV/UpktkQPpXkmfd1esQ==
 */