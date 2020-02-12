package com.greencity.middleware.repositories;

import com.greencity.middleware.controllers.WebSocketPublisher;
import com.greencity.middleware.models.OnlineDevice;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

public class OnlineDeviceRepositoryTest {

    private static final String MAC_ADDRESS_1 = "ma:ca:dd:re";
    private static final String MAC_ADDRESS_2 = "ma:ca:23:12";
    private static final String MAC_ADDRESS_3 = "ma:35:23:12";
    private static final String IP_1 = "192.168.0.196";
    private static final String IP_2 = "192.168.0.194";

    @InjectMocks
    private OnlineDeviceRepository repository;

    @Mock
    private Set<OnlineDevice> devices;

    @Mock
    private WebSocketPublisher webSocketPublisher;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        Mockito.reset(webSocketPublisher, devices);
    }

    @Test
    public void shouldSaveOnlineDeviceToRepositoryWhenRepositoryIsEmpty() {
        // given
        OnlineDevice onlineDevice = new OnlineDevice(MAC_ADDRESS_1, IP_1);

        // when
        repository.register(onlineDevice);

        // then
        Assertions.assertThat(repository.getDevices().size()).isEqualTo(1);
        Assertions.assertThat(repository.getDevices()).isEqualTo(Collections.singleton(onlineDevice));
    }

    @Test
    public void shouldUpdateDeviceIpInRepositoryWhenRepositoryHasOnlineDeviceWithTheSameMacAddress() {
        // given
        OnlineDevice device = new OnlineDevice(MAC_ADDRESS_1, IP_1);
        OnlineDevice deviceWithOtherIp = new OnlineDevice(MAC_ADDRESS_1, IP_2);
        repository.save(device);

        // when
        repository.register(deviceWithOtherIp);

        // then
        Assertions.assertThat(repository.getDevices().size()).isEqualTo(1);
        Assertions.assertThat(repository.getDevices().stream()
                .findFirst()
                .get()
                .getIp()).isEqualTo(deviceWithOtherIp.getIp());
    }

    @Test
    public void shouldAddSecondDeviceWhenOneDeviceAlreadyInRepository() {
        // given
        OnlineDevice device_1 = new OnlineDevice(MAC_ADDRESS_1, IP_1);
        OnlineDevice device_2 = new OnlineDevice(MAC_ADDRESS_2, IP_2);
        repository.save(device_1);

        // when
        repository.register(device_2);

        // then
        Assertions.assertThat(repository.getDevices().size()).isEqualTo(2);
        Assertions.assertThat(repository.getDevices()).isEqualTo(Set.of(device_1, device_2));
    }

    @Test
    public void shouldThrowNoSuchElementExceptionWhenNoDeviceWithSpecifiedMacAddress() {
        // given
        OnlineDevice device_1 = new OnlineDevice(MAC_ADDRESS_1, IP_1);
        OnlineDevice device_2 = new OnlineDevice(MAC_ADDRESS_2, IP_2);
        repository.save(device_1);
        repository.save(device_2);

        // when
        Throwable exception = Assertions.catchThrowable(() -> repository.getDeviceBy(MAC_ADDRESS_3));

        // then
        Assertions.assertThat(exception).isExactlyInstanceOf(NoSuchElementException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Device: " + MAC_ADDRESS_3 + "  not found.");
    }

    @Test
    public void shouldReturnDeviceWhenFoundViaMacAddress() {
        // given
        OnlineDevice device_1 = new OnlineDevice(MAC_ADDRESS_1, IP_1);
        OnlineDevice device_2 = new OnlineDevice(MAC_ADDRESS_2, IP_2);
        repository.save(device_1);
        repository.save(device_2);

        // when
        OnlineDevice foundDevice = repository.getDeviceBy(MAC_ADDRESS_1);

        // then
        Assertions.assertThat(foundDevice).isEqualTo(device_1);
    }

    @Test
    public void shouldDeleteDeviceWithTheSameMacAddress() {
        // given
        OnlineDevice device = new OnlineDevice(MAC_ADDRESS_1, IP_1);
        repository.save(device);

        // when
        repository.delete(MAC_ADDRESS_1);

        // then
        Assertions.assertThat(repository.getDevices().size()).isEqualTo(0);
    }

    @Test
    public void shouldLeaveTheDeviceWhenMacAddressesAreDifferent() {
        // given
        OnlineDevice device = new OnlineDevice(MAC_ADDRESS_1, IP_1);
        repository.save(device);

        // when
        repository.delete(MAC_ADDRESS_2);

        // then
        Assertions.assertThat(repository.getDevices().size()).isEqualTo(1);

    }

}
