package com.greencity.middleware.services;


import com.greencity.middleware.enums.DeviceStatus;
import com.greencity.middleware.models.Device;
import com.greencity.middleware.models.DeviceDTO;
import com.greencity.middleware.repositories.DeviceRepository;
import com.greencity.middleware.repositories.OnlineDeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeviceService {

    private DeviceRepository deviceRepository;
    private OnlineDeviceRepository onlineDeviceRepository;

    public void save(Device device) {
        if (!deviceRepository.existsById(device.getMacAddress()))
            deviceRepository.save(device);
    }

    public List<DeviceDTO> getDevicesAssignedByStatus() {
        List<DeviceDTO> allDtoDevices = deviceRepository.findAll().stream().map(DeviceDTO::deviceToDTO).collect(Collectors.toList());
        List<DeviceDTO> onlineDtoDevices = onlineDeviceRepository.getDevices().stream().map(DeviceDTO::deviceToDTO).collect(Collectors.toList());

        if (onlineDtoDevices.isEmpty() && allDtoDevices.isEmpty()) {
            throw new NoSuchElementException("No devices available");
        }

        if (allDtoDevices.isEmpty()) {
            return onlineDtoDevices;
        }

        for (DeviceDTO device : allDtoDevices) {
            for (DeviceDTO onlineDevice : onlineDtoDevices) {
                if (device.getMacAddress().equals(onlineDevice.getMacAddress())) {
                    device.setDeviceStatus(DeviceStatus.ONLINE);
                    break;
                }
            }
        }
        return allDtoDevices;
    }
}
