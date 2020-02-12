package com.greencity.middleware.services;

import com.google.gson.JsonObject;
import com.greencity.middleware.config.ConfigConstants;
import com.greencity.middleware.models.OnlineDevice;
import com.greencity.middleware.repositories.OnlineDeviceRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OnlineDeviceService {

    private OnlineDeviceRepository deviceRepository;

    public static void saveWaterLevel(OnlineDevice device, Integer waterLevel) {

        final Logger logger = LoggerFactory.getLogger(OnlineDeviceService.class);

        JsonObject json = new JsonObject();
        json.addProperty("waterLevel", waterLevel);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + device.getIp() + "/water-tank"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            logger.info("the water level has not changed");
        }
    }

    @Scheduled(fixedRate = ConfigConstants.DEVICE_CHECKING_INTERVAL)
    public void checkingAvailableDevices() {

        deviceRepository.getDevices().stream()
                .filter(this::isTimeReachedToDelete)
                .map(device -> device.getMacAddress())
                .forEach(macAddress -> deviceRepository.delete(macAddress));
    }

    private boolean isTimeReachedToDelete(OnlineDevice device) {
        return device.getLastUpdateTime()
                .isBefore(LocalDateTime.now()
                        .minusMinutes(ConfigConstants.TIME_TO_REMOVE_DEVICE));
    }
}
