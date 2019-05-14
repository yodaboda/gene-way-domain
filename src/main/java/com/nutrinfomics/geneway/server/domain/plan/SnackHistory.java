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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.nutrinfomics.geneway.server.domain.EntityBase;
import com.nutrinfomics.geneway.server.domain.customer.Customer;
import com.nutrinfomics.geneway.shared.SnackStatus;

@Entity
@Table(
    indexes = {
      @Index(columnList = "customer, plannedSnack, dayString", unique = true),
      @Index(columnList = "customer, dayString", unique = false)
    })
public class SnackHistory extends EntityBase {

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customer")
  @NotNull
  private Customer customer;

  @OneToOne(fetch = FetchType.EAGER) // no cascade
  @JoinColumn(name = "plannedSnack")
  private Snack plannedSnack;

  @OneToOne(fetch = FetchType.EAGER) // no cascade
  @JoinColumn(name = "eatenSnack")
  private Snack eatenSnack;

  @Column(name = "dayString")
  private String dayString;

  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;

  private int timeZoneDiff;

  @Enumerated(EnumType.STRING)
  private SnackStatus status;

  public Snack getPlannedSnack() {
    return plannedSnack;
  }

  public void setPlannedSnack(Snack plannedSnack) {
    this.plannedSnack = plannedSnack;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Snack getEatenSnack() {
    return eatenSnack;
  }

  public void setEatenSnack(Snack eatenSnack) {
    this.eatenSnack = eatenSnack;
  }

  public String getDayString() {
    return dayString;
  }

  public void setDayString(String dayString) {
    this.dayString = dayString;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public int getTimeZoneDiff() {
    return timeZoneDiff;
  }

  public void setTimeZoneDiff(int timeZoneDiff) {
    this.timeZoneDiff = timeZoneDiff;
  }

  public SnackStatus getStatus() {
    return status;
  }

  public void setStatus(SnackStatus status) {
    this.status = status;
  }

  public static String getDateString(Date timestamp, int timeZoneOffset) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(timestamp);
    calendar.set(Calendar.ZONE_OFFSET, timeZoneOffset);

    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    System.out.println(hour);

    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy"); //
    formatter.setCalendar(calendar);

    String dateString = formatter.format(calendar.getTime());
    return dateString;
  }
}

/*
 * †Dr Firas Swidan, PhD. frsswdn@gmail.com. firas.swidan@icloud.com.
 * https://www.linkedin.com/in/swidan
 * POBox  8125,  Nazareth  16480, Israel.
 * Public key: AAAAB3NzaC1yc2EAAAADAQABAAACAQD6Lt98LolwuA/aOcK0h91ECdeiyG3QKcUOT/CcMEPV64cpkv3jrLLGoag7YtzESZ3j7TLEd0WHZ/BZ9d+K2kRfzuuCdMMhrBwqP3YObbTbSIM6NjUNwbH403LLb3FuYApUt1EvC//w64UMm7h3fTo0vdyVuMuGnkRZuM6RRAXcODM4tni9ydd3ZQKN4inztkeH/sOoM77FStk8E2VYbljUQdY39zlRoZwUqNdKzwD3T2G00tmROlTZ6K5L8i68Zqt6s0XNS6XQvS3zXe0fI6UwuetnDrcVr1Yb8y2T8lfjMG9+9L2aKPoUOlOMMcyqM+oKVvRUOSdrzmtKOljnYC7TqzvsKrfXHvHlqHxxhPp1K7B/YWrHwCDbqp02dXdIaXkkHCIqKFNaY06HEWt4obDxppVhC8IabSb55LQVCCT7J4TFbwp6rID2+Y1L7NEvR3v3oaWSlQIZ+WSG04mwh9/7gRCt7XUoqmEXCCPoHqZXq5sWv193XA57pD5gKoX7Rf2i6UdbduNTMIhQMqcWIaPMBFwxUv/LRQCHnS+mlW2GnIHIHHGS/S46MurZ6BMvcb7fEz/NorVxvh3DbUaVTteMYcikH0y5sPmGECB1d99ENBBSEX6diI+PneFp2sOouQ6gOBWy6WAt3spGfLTOFMPo3bMV/UpktkQPpXkmfd1esQ==
 */
