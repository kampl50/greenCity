package com.greencity.middleware.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.greencity.middleware.config.ConfigConstants;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class MeasurementDTO {
    private String macAddress;
    private double airTemperature;
    private double waterTemperature;
    private double airHumidity;
    private double airPollution;
    private double waterLevel;
    private double lightLevel;
    @DateTimeFormat(pattern = ConfigConstants.DATE_PATTERN)
    @JsonFormat(pattern = ConfigConstants.DATE_PATTERN)
    private LocalDateTime localDateTime;

    public Measurement convertToMeasurement() {
        return Measurement.builder()
                .airTemperature(this.airTemperature)
                .waterTemperature(this.waterTemperature)
                .airHumidity(this.airHumidity)
                .airPollution(this.airPollution)
                .waterLevel(this.waterLevel)
                .lightLevel(this.lightLevel)
                .macAddress(this.macAddress)
                .localDateTime(this.localDateTime)
                .build();
    }
}
