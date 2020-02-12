package com.greencity.middleware.services;

import com.greencity.middleware.models.Range;
import com.greencity.middleware.models.Measurement;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeasurementsDataGradationServiceTest {

    private List<Measurement> measurements;
    private LocalDateTime startDate = LocalDateTime.of(2019, 7, 28, 12, 0, 0);
    private LocalDateTime endDate = LocalDateTime.of(2019, 7, 28, 12, 59, 0);

    @Mock
    private MeasurementService measurementService;

    @InjectMocks
    private MeasurementsDataGradationService measurementsDataGradationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        final Measurement m1 = buildMeasurementWithCertainSeconds("1", 0);
        final Measurement m2 = buildMeasurementWithCertainSeconds("2", 12);
        final Measurement m3 = buildMeasurementWithCertainSeconds("3", 23);
        final Measurement m4 = buildMeasurementWithCertainSeconds("4", 34);
        final Measurement m5 = buildMeasurementWithCertainSeconds("5", 59);

        measurements = Arrays.asList(m1, m2, m3, m4, m5);
    }

    @After
    public void tearDown() {
        Mockito.reset(measurementService);
    }

    @Test
    public void shouldReturnElementsWithIndexesZeroOneThreeFromAllFiveMeasures() {
        // given
        Range range = new Range(startDate, endDate, 2);
        measurements = measurements.stream()
                .map(m -> m.cutTimeTo(range.getIntervalInMinutes()))
                .collect(Collectors.toList());
        when(measurementService.findAllByDate(startDate, endDate)).thenReturn(measurements);

        // when
        List<Measurement> result = measurementsDataGradationService.gradateMeasurementsByMinutes(range);

        // then
        Assertions.assertThat(result).isEqualTo(List.of(measurements.get(0), measurements.get(1), measurements.get(3)));
    }

    @Test
    public void shouldReturnEmptyList() {
        // given
        Range range = new Range(startDate, endDate, 58);
        measurements = measurements.stream().map(m -> m.cutTimeTo(range.getIntervalInMinutes())).collect(Collectors.toList());
        when(measurementService.findAllByDate(startDate, endDate)).thenReturn(measurements);

        // when
        List<Measurement> result = measurementsDataGradationService.gradateMeasurementsByMinutes(range);

        // then
        Assertions.assertThat(result).isEqualTo(List.of());
    }

    private Measurement buildMeasurementWithCertainSeconds(String id, int minutes) {
        return Measurement.builder()
                .id(id)
                .macAddress("ma:ca:dd:re")
                .airTemperature(1)
                .waterTemperature(2)
                .airHumidity(3)
                .airPollution(4)
                .waterLevel(5)
                .lightLevel(6)
                .localDateTime(LocalDateTime.of(2019, 7, 28, 12, minutes, 43))
                .build();
    }
}
