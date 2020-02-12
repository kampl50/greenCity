package airPort.psk.services;


import airPort.psk.models.flightThings.Flight;
import airPort.psk.repositories.FlightRepository;
import airPort.psk.repositories.MostVisitedPlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightPlannerService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private MostVisitedPlacesRepository mostVisitedPlacesRepository;

    public void save(Flight flight) {
        flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public List<StringBuilder> getAllMostVisitedPlaces() {
        return mostVisitedPlacesRepository.getAll();
    }

}
