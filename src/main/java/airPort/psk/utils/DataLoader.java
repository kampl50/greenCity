package airPort.psk.utils;


import airPort.psk.repositories.MostVisitedPlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    MostVisitedPlacesRepository mostVisitedPlacesRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData(){

        StringBuilder dublin = new StringBuilder("Dublin");
        StringBuilder london = new StringBuilder("London");
        StringBuilder hamburg = new StringBuilder("Hamburg");
        StringBuilder oslo = new StringBuilder("Oslo");
        StringBuilder berlin = new StringBuilder("Berlin");
        StringBuilder paris = new StringBuilder("Paris");
        mostVisitedPlacesRepository.add(dublin);
        mostVisitedPlacesRepository.add(london);
        mostVisitedPlacesRepository.add(hamburg);
        mostVisitedPlacesRepository.add(oslo);
        mostVisitedPlacesRepository.add(berlin);
        mostVisitedPlacesRepository.add(paris);

    }
}
