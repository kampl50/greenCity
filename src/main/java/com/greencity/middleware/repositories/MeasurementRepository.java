package com.greencity.middleware.repositories;

import com.greencity.middleware.models.Measurement;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeasurementRepository extends CassandraRepository<Measurement, String> {

    @AllowFiltering
    List<Measurement> findMeasurementsByLocalDateTimeGreaterThanAndLocalDateTimeLessThan(LocalDateTime startDate, LocalDateTime endDate);
}
