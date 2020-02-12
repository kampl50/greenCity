package airPort.psk.models.flightThings;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight {

    private LocalDateTime departureTime;
    private AirPort departurePlace;
    private AirPort arrivalPlace;
    private Crew crew;
    private Plane plane;
}
