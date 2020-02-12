package com.greencity.middleware.models;

import com.greencity.middleware.enums.DeviceStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@ToString
@Builder
@EqualsAndHashCode
public class DeviceDTO {

    private String macAddress;
    private DeviceStatus deviceStatus;

    public static DeviceDTO deviceToDTO(Device device) {
        return DeviceDTO.builder()
                .macAddress(device.getMacAddress())
                .deviceStatus(DeviceStatus.OFFLINE)
                .build();
    }

    public static DeviceDTO deviceToDTO(OnlineDevice device) {
        return DeviceDTO.builder()
                .macAddress(device.getMacAddress())
                .deviceStatus(DeviceStatus.ONLINE)
                .build();
    }
}
