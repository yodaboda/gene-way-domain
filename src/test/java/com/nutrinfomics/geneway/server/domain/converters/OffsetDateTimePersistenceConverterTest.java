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
package com.nutrinfomics.geneway.server.domain.converters;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.nutrinfomics.geneway.shared.testcategory.FastTest;

@Ignore
@Category(value = {FastTest.class})
public class OffsetDateTimePersistenceConverterTest {

  private final Clock clock = Clock.fixed(Instant.EPOCH, ZoneId.of("America/New_York"));

  private OffsetDateTimePersistenceConverter offsetDateTimePersistenceConverter =
      new OffsetDateTimePersistenceConverter();

  @Test
  public void convertToDatabaseColumn_AsExpected() {
    OffsetDateTime offsetDateTime = OffsetDateTime.now(clock);

    java.sql.Timestamp timestamp =
        offsetDateTimePersistenceConverter.convertToDatabaseColumn(offsetDateTime);

    String offsetDateTimeString = offsetDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    String timestampString = dateFormat.format(timestamp);
    assertEquals(offsetDateTimeString, timestampString);
  }

  @Test
  public void convertToEntityAttribute_AsExpected() {
    Timestamp timestamp = Timestamp.from(clock.instant());

    OffsetDateTime offsetDateTime =
        offsetDateTimePersistenceConverter.convertToEntityAttribute(timestamp);

    String offsetDateTimeString = offsetDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    String timestampString = dateFormat.format(timestamp);

    assertEquals(timestampString, offsetDateTimeString);
  }

  @Test
  public void convertRoundTrip() {
    OffsetDateTime offsetDateTime = OffsetDateTime.now();
    java.sql.Timestamp timestamp =
        offsetDateTimePersistenceConverter.convertToDatabaseColumn(offsetDateTime);
    OffsetDateTime convertedOffsetDateTime =
        offsetDateTimePersistenceConverter.convertToEntityAttribute(timestamp);

    assertTrue(offsetDateTime.isEqual(convertedOffsetDateTime));
  }
}

/*
 * †Dr Firas Swidan, PhD. frsswdn@gmail.com. firas.swidan@icloud.com.
 * https://www.linkedin.com/in/swidan
 * POBox  8125,  Nazareth  16480, Israel.
 * Public key: AAAAB3NzaC1yc2EAAAADAQABAAACAQD6Lt98LolwuA/aOcK0h91ECdeiyG3QKcUOT/CcMEPV64cpkv3jrLLGoag7YtzESZ3j7TLEd0WHZ/BZ9d+K2kRfzuuCdMMhrBwqP3YObbTbSIM6NjUNwbH403LLb3FuYApUt1EvC//w64UMm7h3fTo0vdyVuMuGnkRZuM6RRAXcODM4tni9ydd3ZQKN4inztkeH/sOoM77FStk8E2VYbljUQdY39zlRoZwUqNdKzwD3T2G00tmROlTZ6K5L8i68Zqt6s0XNS6XQvS3zXe0fI6UwuetnDrcVr1Yb8y2T8lfjMG9+9L2aKPoUOlOMMcyqM+oKVvRUOSdrzmtKOljnYC7TqzvsKrfXHvHlqHxxhPp1K7B/YWrHwCDbqp02dXdIaXkkHCIqKFNaY06HEWt4obDxppVhC8IabSb55LQVCCT7J4TFbwp6rID2+Y1L7NEvR3v3oaWSlQIZ+WSG04mwh9/7gRCt7XUoqmEXCCPoHqZXq5sWv193XA57pD5gKoX7Rf2i6UdbduNTMIhQMqcWIaPMBFwxUv/LRQCHnS+mlW2GnIHIHHGS/S46MurZ6BMvcb7fEz/NorVxvh3DbUaVTteMYcikH0y5sPmGECB1d99ENBBSEX6diI+PneFp2sOouQ6gOBWy6WAt3spGfLTOFMPo3bMV/UpktkQPpXkmfd1esQ==
 */
