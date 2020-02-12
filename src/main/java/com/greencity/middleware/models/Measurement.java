package com.greencity.middleware.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Table
public class Measurement {
    @Id
    @PrimaryKey
    private String id;
    private String macAddress;
    private double airTemperature;
    private double waterTemperature;
    private double airHumidity;
    private double airPollution;
    private double waterLevel;
    private double lightLevel;
    private LocalDateTime localDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measurement)) return false;
        Measurement that = (Measurement) o;
        return getMacAddress().equals(that.getMacAddress()) &&
                getLocalDateTime().equals(that.getLocalDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMacAddress(), getLocalDateTime());
    }

    public Measurement cutTimeTo(int minutes) {
        if (minutes < 60) {
            return setLocalDateTimeSecondsToZero();
        } else {
            return setLocalDateTimeMinutesAndSecondsToZero();
        }
    }


    private Measurement setLocalDateTimeSecondsToZero() {
        return Measurement.builder()
                .id(this.id)
                .macAddress(this.macAddress)
                .airTemperature(this.airTemperature)
                .waterTemperature(this.waterTemperature)
                .airHumidity(this.airHumidity)
                .airPollution(this.airPollution)
                .waterLevel(this.waterLevel)
                .lightLevel(this.lightLevel)
                .macAddress(this.macAddress)
                .localDateTime(dropSeconds())
                .build();
    }

    private LocalDateTime dropSeconds() {
        return LocalDateTime.of(this.getLocalDateTime().getYear()
                , this.getLocalDateTime().getMonth()
                , this.getLocalDateTime().getDayOfMonth()
                , this.getLocalDateTime().getHour()
                , this.getLocalDateTime().getMinute()
                , 0);
    }

    private Measurement setLocalDateTimeMinutesAndSecondsToZero() {
        return Measurement.builder()
                .id(this.id)
                .airTemperature(this.airTemperature)
                .waterTemperature(this.waterTemperature)
                .airHumidity(this.airHumidity)
                .airPollution(this.airPollution)
                .waterLevel(this.waterLevel)
                .lightLevel(this.lightLevel)
                .macAddress(this.macAddress)
                .localDateTime(dropMinutesAndSeconds())
                .build();
    }

    private LocalDateTime dropMinutesAndSeconds() {
        return LocalDateTime.of(this.getLocalDateTime().getYear()
                , this.getLocalDateTime().getMonth()
                , this.getLocalDateTime().getDayOfMonth()
                , this.getLocalDateTime().getHour()
                , 0
                , 0);
    }
}
