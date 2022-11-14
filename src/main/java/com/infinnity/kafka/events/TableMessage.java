package com.infinnity.kafka.events;

import java.time.ZonedDateTime;

public interface TableMessage {

    String getKey();

    long getVersion();

    boolean isDeleted();

    ZonedDateTime getTimestamp();

}
