package com.greencity.middleware.services;

import com.greencity.middleware.enums.DeviceStatus;
import com.greencity.middleware.models.Device;
import com.greencity.middleware.models.DeviceDTO;
import com.greencity.middleware.models.OnlineDevice;
import com.greencity.middleware.repositories.DeviceRepository;
import com.greencity.middleware.repositories.OnlineDeviceRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceTest {

    private static final String MAC_ADDRESS_1 = "ma:ca:dd:10";
    private static final String MAC_ADDRESS_2 = "ma:ca:dd:20";
    private static final String MAC_ADDRESS_3 = "ma:ca:dd:30";

    private static final String IP_ADDRESS_1 = "192.168.0.190";
    private static final String IP_ADDRESS_2 = "192.168.0.193";

    private static final int INDEX_NUMBER_0 = 0;
    private static final int INDEX_NUMBER_1 = 1;
    private static final int INDEX_NUMBER_2 = 2;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private OnlineDeviceRepository onlineDeviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @After
    public void tearDown() {
        Mockito.reset(deviceRepository, onlineDeviceRepository);
    }

    @Test
    public void shouldReturnDeviceListWithOnlineStatusAndSameMacAddress() {
        // given
        List<Device> devices = buildDeviceList();
        Set<OnlineDevice> onlineDevices = buildOnlineDeviceSet();
        when(deviceRepository.findAll()).thenReturn(devices);
        when(onlineDeviceRepository.getDevices()).thenReturn(onlineDevices);

        // when
        List<DeviceDTO> allDevicesDTO = deviceService.getDevicesAssignedByStatus();

        // then
        Assertions.assertThat(allDevicesDTO.get(INDEX_NUMBER_0).getMacAddress()).isEqualTo(MAC_ADDRESS_1);
        Assertions.assertThat(allDevicesDTO.get(INDEX_NUMBER_1).getMacAddress()).isEqualTo(MAC_ADDRESS_2);

        Assertions.assertThat(allDevicesDTO.get(INDEX_NUMBER_0).getDeviceStatus()).isEqualTo(DeviceStatus.ONLINE);
        Assertions.assertThat(allDevicesDTO.get(INDEX_NUMBER_1).getDeviceStatus()).isEqualTo(DeviceStatus.ONLINE);
        Assertions.assertThat(allDevicesDTO.get(INDEX_NUMBER_2).getDeviceStatus()).isEqualTo(DeviceStatus.OFFLINE);
    }

    @Test
    public void shouldReturnOnlyOfflineDevicesWhenOnlineDevicesSetIsEmpty() {
        // given
        List<Device> devices = buildDeviceList();
        when(deviceRepository.findAll()).thenReturn(devices);
        when(onlineDeviceRepository.getDevices()).thenReturn(Collections.emptySet());

        // when
        List<DeviceDTO> allDevicesDTO = deviceService.getDevicesAssignedByStatus();

        // then
        Assertions.assertThat(allDevicesDTO).extracting(DeviceDTO::getDeviceStatus).containsOnly(DeviceStatus.OFFLINE);
    }

    @Test
    public void shouldThrowNoSuchElementExceptionWhenNoDeviceAvailable() {
        // given
        List<Device> devices = Collections.emptyList();
        Set<OnlineDevice> onlineDevices = Collections.emptySet();
        when(deviceRepository.findAll()).thenReturn(devices);
        when(onlineDeviceRepository.getDevices()).thenReturn(onlineDevices);

        // when
        Throwable exception = Assertions.catchThrowable(() -> deviceService.getDevicesAssignedByStatus());

        // then
        Assertions.assertThat(exception).isExactlyInstanceOf(NoSuchElementException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("No devices available");
    }

    @Test
    public void shouldReturnOnlyOnlineDevicesWhenOfflineDevicesListIsEmpty() {
        // given
        List<Device> devices = Collections.emptyList();;
        Set<OnlineDevice> onlineDevices = buildOnlineDeviceSet();
        when(deviceRepository.findAll()).thenReturn(devices);
        when(onlineDeviceRepository.getDevices()).thenReturn(onlineDevices);

        // when
        List<DeviceDTO> allDevicesDTO = deviceService.getDevicesAssignedByStatus();

        // then
        Assertions.assertThat(allDevicesDTO.size()).isEqualTo(Integer.valueOf(2));
        Assertions.assertThat(allDevicesDTO.get(INDEX_NUMBER_0).getDeviceStatus()).isEqualTo(DeviceStatus.ONLINE);
        Assertions.assertThat(allDevicesDTO.get(INDEX_NUMBER_1).getDeviceStatus()).isEqualTo(DeviceStatus.ONLINE);
    }


    private List<Device> buildDeviceList() {
        Device d1 = Device.builder().macAddress(MAC_ADDRESS_1).build();
        Device d2 = Device.builder().macAddress(MAC_ADDRESS_2).build();
        Device d3 = Device.builder().macAddress(MAC_ADDRESS_3).build();

        return Arrays.asList(d1, d2, d3);
    }

    private Set<OnlineDevice> buildOnlineDeviceSet() {
        OnlineDevice d1 = new OnlineDevice(MAC_ADDRESS_1, IP_ADDRESS_1);
        OnlineDevice d2 = new OnlineDevice(MAC_ADDRESS_2, IP_ADDRESS_2);

        Set<OnlineDevice> devices = new LinkedHashSet<>();
        devices.add(d1);
        devices.add(d2);

        return devices;
    }
}