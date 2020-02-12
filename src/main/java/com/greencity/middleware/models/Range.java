package com.greencity.middleware.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Range {

    private LocalDateTime start;
    private LocalDateTime end;
    private int intervalInMinutes;
}
