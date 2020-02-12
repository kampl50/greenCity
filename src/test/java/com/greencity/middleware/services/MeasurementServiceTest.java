package com.greencity.middleware.services;

import com.greencity.middleware.controllers.WebSocketPublisher;
import com.greencity.middleware.models.Measurement;
import com.greencity.middleware.repositories.MeasurementRepository;
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
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeasurementServiceTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private WebSocketPublisher webSocketPublisher;

    @InjectMocks
    private MeasurementService measurementService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        Mockito.reset(measurementRepository, webSocketPublisher);
    }

    @Test
    public void shouldReturnListWithThreeMeasurementsWhenThreeMeasurementsInRepository() {
        // given
        Measurement mockMeasurement1 = buildMeasurement("1", getLocalDateTime(12));
        Measurement mockMeasurement2 = buildMeasurement("2", getLocalDateTime(15));
        Measurement mockMeasurement3 = buildMeasurement("3", getLocalDateTime(18));
        List<Measurement> measurements = Arrays.asList(mockMeasurement1, mockMeasurement2, mockMeasurement3);

        when(measurementRepository.findAll()).thenReturn(measurements);

        // when
        List<Measurement> result = (List<Measurement>) measurementService.findAll();

        // then
        Assertions.assertThat(result).isEqualTo(measurements);
    }

    @Test
    public void shouldSaveMeasurementToRepositoryWhenPassedValidMeasurement() throws Exception {
        // given
        Measurement measurement = new Measurement();

        // when
        measurementService.saveMeasurement(measurement);

        // then
        Mockito.verify(measurementRepository, Mockito.times(1)).save(Mockito.any(Measurement.class));
    }

    @Test
    public void shouldGetMeasurementWithGivenId() {
        // given
        UUID uuid = UUID.randomUUID();
        Measurement mockMeasurement = buildMeasurement(uuid.toString(), LocalDateTime.now());
        when(measurementRepository.findById(uuid.toString())).thenReturn(Optional.of(mockMeasurement));

        // when
        Measurement measurements = measurementService.findById(uuid.toString());

        // then
        Assertions.assertThat(measurements).isEqualTo(mockMeasurement);
    }

    private LocalDateTime getLocalDateTime(int seconds) {
        return LocalDateTime.of(2019, 7, 24, 12, 12, seconds);
    }

    private Measurement buildMeasurement(String id, LocalDateTime dateTime) {
        return Measurement.builder()
                .id(id)
                .airTemperature(1)
                .macAddress("ma:ca:dd:re")
                .localDateTime(dateTime)
                .build();
    }

}
