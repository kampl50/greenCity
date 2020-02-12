package airPort.psk.controllers;


import airPort.psk.models.flightThings.Flight;
import airPort.psk.services.FlightPlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightPlannerController {

    @Autowired
    private FlightPlannerService flightPlannerService;

    @PostMapping("/createFlight")
    public void createFlight(@RequestBody Flight flight){
          flightPlannerService.save(flight);
    }

    @GetMapping("/flights")
    public List<Flight> allFlights(){
        return flightPlannerService.getAllFlights();
    }

    @GetMapping("/mostVisitedPlaces")
    public List<StringBuilder> allMostVisitedPlaces(){
        return flightPlannerService.getAllMostVisitedPlaces();
    }

    @GetMapping("/string")
    public String getString(){
        return "String";
    }

}
