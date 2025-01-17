/*
 * This file is part of Waarp Project (named also Waarp or GG).
 *
 *  Copyright (c) 2019, Waarp SAS, and individual contributors by the @author
 *  tags. See the COPYRIGHT.txt in the distribution for a full listing of
 * individual contributors.
 *
 *  All Waarp Project is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Waarp is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with
 * Waarp . If not, see <http://www.gnu.org/licenses/>.
 */
package org.waarp.common.guid;

import org.waarp.common.utility.Hexa;
import org.waarp.common.utility.StringUtils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UUID Generator (also Global UUID Generator) but limited to 1 Integer (32
 * bits) <br>
 * <br>
 * Inspired from com.groupon locality-uuid which used combination of internal
 * counter value - process id -
 * and Timestamp. see https://github.com/groupon/locality-uuid.java <br>
 * <br>
 * But force sequence and take care of errors and improves some performance
 * issues.<br>
 * <br>
 * Limit is about 4000000/s UUID
 */
public final class IntegerUuid {
  /**
   * Counter part
   */
  private static final AtomicInteger COUNTER =
      new AtomicInteger(StringUtils.RANDOM.nextInt());
  /**
   * Byte size of UUID
   */
  private static final int UUIDSIZE = 4;

  /**
   * real UUID
   */
  private final byte[] uuid = { 0, 0, 0, 0 };

  static final synchronized int getCounter() {
    if (COUNTER.compareAndSet(Integer.MAX_VALUE, Integer.MIN_VALUE)) {
      return Integer.MAX_VALUE;
    } else {
      return COUNTER.getAndIncrement();
    }
  }

  /**
   * Constructor that generates a new UUID using the current process id, MAC
   * address, and timestamp
   */
  public IntegerUuid() {
    // atomically
    final int count = getCounter();
    uuid[0] = (byte) (count >> 24);
    uuid[1] = (byte) (count >> 16);
    uuid[2] = (byte) (count >> 8);
    uuid[3] = (byte) count;
  }

  /**
   * Constructor that takes a byte array as this UUID's content
   *
   * @param bytes UUID content
   */
  public IntegerUuid(final byte[] bytes) {
    if (bytes.length != UUIDSIZE) {
      throw new RuntimeException(
          "Attempted to parse malformed UUID: " + Arrays.toString(bytes));
    }
    System.arraycopy(bytes, 0, uuid, 0, UUIDSIZE);
  }

  public IntegerUuid(final int value) {
    uuid[0] = (byte) (value >> 24);
    uuid[1] = (byte) (value >> 16);
    uuid[2] = (byte) (value >> 8);
    uuid[3] = (byte) value;
  }

  public IntegerUuid(final String idsource) {
    final String id = idsource.trim();

    if (id.length() != UUIDSIZE * 2) {
      throw new IllegalArgumentException(
          "Attempted to parse malformed UUID: " + id);
    }
    System.arraycopy(Hexa.fromHex(id), 0, uuid, 0, UUIDSIZE);
  }

  @Override
  public String toString() {
    return Hexa.toHex(uuid);
  }

  /**
   * copy the uuid of this UUID, so that it can't be changed, and return it
   *
   * @return raw byte array of UUID
   */
  public final byte[] getBytes() {
    return Arrays.copyOf(uuid, UUIDSIZE);
  }

  /**
   * extract timestamp from raw UUID bytes and return as int
   *
   * @return millisecond UTC timestamp from generation of the UUID
   */
  public final long getTimestamp() {
    long time;
    time = ((long) uuid[0] & 0xFF) << 24;
    time |= ((long) uuid[2] & 0xFF) << 16;
    time |= ((long) uuid[2] & 0xFF) << 8;
    time |= (long) uuid[3] & 0xFF;
    return time;
  }

  @Override
  public final boolean equals(final Object o) {
    if (!(o instanceof IntegerUuid)) {
      return false;
    }
    return this == o || Arrays.equals(uuid, ((IntegerUuid) o).uuid);
  }

  @Override
  public final int hashCode() {
    return Arrays.hashCode(uuid);
  }

  /**
   * @return the equivalent UUID as int
   */
  public final int getInt() {
    int value = ((int) uuid[0] & 0xFF) << 24;
    value |= ((long) uuid[1] & 0xFF) << 16;
    value |= ((long) uuid[2] & 0xFF) << 8;
    value |= (long) uuid[3] & 0xFF;
    return value;
  }
}
