package com.greencity.middleware.repositories;


import com.greencity.middleware.models.Device;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends CassandraRepository<Device, String> {
    @Override
    boolean existsById(String s);

    @Override
    List<Device> findAll();
}
