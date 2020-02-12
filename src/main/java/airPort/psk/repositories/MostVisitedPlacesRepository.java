package airPort.psk.repositories;


import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class MostVisitedPlacesRepository implements OwnRepository<StringBuilder> {

    List<StringBuilder> mostVisitedCities = new LinkedList<>();

    @Override
    public void add(StringBuilder city) {
        mostVisitedCities.add(city);
    }

    @Override
    public void update(StringBuilder city) {
        mostVisitedCities.stream().
                map(g -> {
                    g.delete(0, g.length());
                    g = city;
                    return g;
                });
    }

    @Override
    public void remove(StringBuilder city) {
        mostVisitedCities.remove(city);
    }


    @Override
    public StringBuilder getById(int i) {
        return mostVisitedCities.get(i);
    }

    @Override
    public List<StringBuilder> getAll() {
        return mostVisitedCities;
    }
}
