package com.greencity.middleware.models;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder()
public class OnlineDevice {

    private Device device;
    private String ip;
    private LocalDateTime lastUpdateTime;

    public OnlineDevice(String macAddress, String ip) {
        this.device = new Device();
        this.device.setMacAddress(macAddress);
        this.setIp(ip);
        this.lastUpdateTime = LocalDateTime.now();
    }

    public void update(String ip) {
        this.setIp(ip);
        this.setLastUpdateTime(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OnlineDevice)) return false;
        OnlineDevice that = (OnlineDevice) o;
        return device.getMacAddress().equals(that.device.getMacAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(device.getMacAddress());
    }

    public String getMacAddress() {
        return device.getMacAddress();
    }
}
