package airPort.psk.models.flightThings;

import airPort.psk.models.others.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirPort {

    private long id;
    private Address address;

}
