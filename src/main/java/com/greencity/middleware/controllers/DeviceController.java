package com.greencity.middleware.controllers;

import com.greencity.middleware.models.DeviceDTO;
import com.greencity.middleware.models.OnlineDevice;
import com.greencity.middleware.repositories.OnlineDeviceRepository;
import com.greencity.middleware.services.DeviceService;
import com.greencity.middleware.services.OnlineDeviceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/devices")
public class DeviceController {

    private OnlineDeviceRepository onlineDeviceRepository;
    private DeviceService deviceService;

    @PostMapping
    public void setWaterLevel(@RequestParam(name = "waterLevel") int waterLevel) {
        onlineDeviceRepository.getDevices().stream()
                .forEach(device -> OnlineDeviceService.saveWaterLevel(device, waterLevel));
    }

    @GetMapping("/online")
    public Set<OnlineDevice> getOnlineDevices() {
        return onlineDeviceRepository.getDevices();
    }

    @GetMapping
    public List<DeviceDTO> getAllAssignedDevices() {
        return deviceService.getDevicesAssignedByStatus();
    }

    @GetMapping(params = {"macAddress"})
    public OnlineDevice getDeviceByMac(@RequestParam("macAddress") String macAddress) {
        return onlineDeviceRepository.getDeviceBy(macAddress);
    }
}
