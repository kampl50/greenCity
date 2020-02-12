package com.greencity.middleware.services;

import com.greencity.middleware.config.ConfigConstants;
import com.greencity.middleware.controllers.WebSocketPublisher;
import com.greencity.middleware.models.Measurement;
import com.greencity.middleware.repositories.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MeasurementService {

    private WebSocketPublisher webSocketPublisher;
    private MeasurementRepository repository;

    public void lightOn() {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + ConfigConstants.DEVICE_IP_ADDRESS + "/lights-on"))
                .GET()
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void lightOff() {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + ConfigConstants.DEVICE_IP_ADDRESS + "/lights-off"))
                .GET()
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public MeasurementService(WebSocketPublisher webSocketPublisher, MeasurementRepository repository) {
        this.webSocketPublisher = webSocketPublisher;
        this.repository = repository;
    }

    public List<Measurement> findAllByDate(LocalDateTime min, LocalDateTime max) {
        return repository.findMeasurementsByLocalDateTimeGreaterThanAndLocalDateTimeLessThan(min, max);
    }

    public Measurement findById(String id) {
        return repository.findById(id).get();
    }

    public void saveMeasurement(Measurement measurement) {
        measurement.setId(UUID.randomUUID().toString());
        repository.save(measurement);
        webSocketPublisher.publishMeasurement(measurement);
    }

    public List<Measurement> findAll() {
        return repository.findAll();
    }

}
