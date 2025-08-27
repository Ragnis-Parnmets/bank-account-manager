package com.example.bank_account_manager.infrastructure.time;

import org.mapstruct.Named;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class TimeConfig {

    private TimeConfig() {}

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MMMM yyyy 'at' HH:mm:ss");
    private static final ZoneId TIME_ZONE = ZoneId.of("Europe/Tallinn");

    @Named("instantToDateTime")
    public static String instantToDateTime(Instant instant) {
        return instant == null ? null : instant.atZone(TIME_ZONE).format(DATE_TIME_FORMATTER);
    }
}
