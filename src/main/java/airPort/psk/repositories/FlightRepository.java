package airPort.psk.repositories;


import airPort.psk.models.flightThings.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {
    @Override
    List<Flight> findAll();
}
