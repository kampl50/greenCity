package com.greencity.middleware.repositories;

import com.greencity.middleware.controllers.WebSocketPublisher;
import com.greencity.middleware.models.OnlineDevice;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OnlineDeviceRepository {

    private WebSocketPublisher webSocketPublisher;
    private Set<OnlineDevice> devices;

    public OnlineDeviceRepository(@Lazy WebSocketPublisher webSocketPublisher) {
        this.webSocketPublisher = webSocketPublisher;
        this.devices = Collections.synchronizedSet(new HashSet<>());
    }

    public Set<OnlineDevice> getDevices() {
        return devices;
    }

    public void save(OnlineDevice device) {
        devices.add(device);
        webSocketPublisher.publishOnlineDeviceList();
    }

    public OnlineDevice getDeviceBy(String macAddress) {
        return devices.stream()
                .filter(device -> device.getMacAddress().equals(macAddress))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Device: " + macAddress + "  not found."));
    }

    public void delete(String macAddress) {
        Optional<OnlineDevice> deviceToRemove = devices.stream()
                .filter(device -> device.getMacAddress().equals(macAddress))
                .findFirst();
        if (deviceToRemove.isPresent())
            devices.remove(deviceToRemove.get());

        webSocketPublisher.publishOnlineDeviceList();
    }

    public void register(OnlineDevice device) {
        devices.stream()
                .filter(device::equals)
                .findFirst()
                .ifPresentOrElse(d -> d.update(device.getIp()), () -> save(device));
    }
}
