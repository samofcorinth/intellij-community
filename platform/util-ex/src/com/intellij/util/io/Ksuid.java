// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.util.io;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * KSUID is for K-Sortable Unique Identifier.
 * Globally unique IDs similar to RFC 4122 UUIDs, but contain a time component, so they can be "roughly" sorted by time of creation.
 * The remainder of the KSUID is randomly generated bytes.
 *
 * See https://github.com/segmentio/ksuid#what-is-a-ksuid
 */
public final class Ksuid {
  private static final int EPOCH = 1400000000;
  private static final int TIMESTAMP_LENGTH = 4;
  private static final int PAYLOAD_LENGTH = 16;
  public static final int MAX_ENCODED_LENGTH = 27;

  public static @NotNull String generate() {
    ByteBuffer byteBuffer = ByteBuffer.allocate(TIMESTAMP_LENGTH + PAYLOAD_LENGTH);

    long utc = ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli() / 1000;
    int timestamp = (int)(utc - EPOCH);
    byteBuffer.putInt(timestamp);

    byte[] bytes = new byte[PAYLOAD_LENGTH];
    DigestUtil.getRandom().nextBytes(bytes);
    byteBuffer.put(bytes);

    String uid = new String(Base62.encode(byteBuffer.array()), StandardCharsets.UTF_8);
    return uid.length() > MAX_ENCODED_LENGTH ? uid.substring(0, MAX_ENCODED_LENGTH) : uid;
  }
}