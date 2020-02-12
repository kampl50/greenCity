package com.greencity.middleware.controllers;

import com.greencity.middleware.models.Measurement;
import com.greencity.middleware.services.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static com.greencity.middleware.config.ConfigConstants.WEB_SOCKET_DEVICES_URL;
import static com.greencity.middleware.config.ConfigConstants.WEB_SOCKET_MEASUREMENT_URL;

@Component
@AllArgsConstructor
public class WebSocketPublisher {

    private SimpMessagingTemplate template;
    private DeviceService deviceService;

    public void publishMeasurement(Measurement measurement) {
        template.convertAndSend(WEB_SOCKET_MEASUREMENT_URL + measurement.getMacAddress(), measurement);
    }

    public void publishOnlineDeviceList() {
        template.convertAndSend(WEB_SOCKET_DEVICES_URL, deviceService.getDevicesAssignedByStatus());
    }
}
