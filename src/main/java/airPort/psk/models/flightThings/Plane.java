package airPort.psk.models.flightThings;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plane {

    private String mark;
    private String model;
    private int numberOfSeats;
    private double range;
}
