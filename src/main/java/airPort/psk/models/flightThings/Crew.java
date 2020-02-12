package airPort.psk.models.flightThings;

import airPort.psk.models.people.AirLinePilot;
import airPort.psk.models.people.Stewardess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Crew {

    private List<AirLinePilot> airLinePilots;
    private List<Stewardess> stewardesses;
}
