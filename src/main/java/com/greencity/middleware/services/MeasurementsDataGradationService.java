package com.greencity.middleware.services;

import com.greencity.middleware.models.Measurement;
import com.greencity.middleware.models.Range;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementsDataGradationService {

    public static final int MINUTES_IN_HOUR = 60;
    @Autowired
    private MeasurementService measurementService;

    private static <T> Predicate<T> distinctByKey(Measurement measurement) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(measurement.getLocalDateTime());
    }

    public List<Measurement> gradateMeasurementsByMinutes(Range range) {
        int interval = range.getIntervalInMinutes();

        return measurementService.findAllByDate(range.getStart(), range.getEnd())
                .parallelStream()
                .filter(match(interval))
                .map(measurement -> measurement.cutTimeTo(interval))
                .distinct()
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparing(Measurement::getLocalDateTime))
                .collect(Collectors.toList());
    }

    private Predicate<Measurement> match(int interval) {
        return measurement -> {
            measurement.getLocalDateTime().toEpochSecond(ZoneOffset.ofHours(2));
            if (interval < MINUTES_IN_HOUR) {
                return measurement.getLocalDateTime().getMinute() % interval == 0;
            } else {
                return measurement.getLocalDateTime().getHour() % (interval / 60) == 0;
            }
        };
    }
}