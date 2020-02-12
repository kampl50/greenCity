package com.greencity.middleware.controllers;


import com.greencity.middleware.config.ConfigConstants;
import com.greencity.middleware.helpers.WebUtils;
import com.greencity.middleware.models.*;
import com.greencity.middleware.repositories.OnlineDeviceRepository;
import com.greencity.middleware.services.DeviceService;
import com.greencity.middleware.services.MeasurementService;
import com.greencity.middleware.services.MeasurementsDataGradationService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/measurements")
public class MeasurementController {

    private MeasurementService measurementService;
    private DeviceService deviceService;
    private MeasurementsDataGradationService measurementsDataGradationService;
    private OnlineDeviceRepository onlineDeviceRepository;

    @PostMapping("/reduce")
    public List<Measurement> getMeasurementsByDateAndDataGradation(@RequestBody Range range) {
        return measurementsDataGradationService.gradateMeasurementsByMinutes(range);
    }

    @GetMapping(params = {"min", "max"})
    public List<Measurement> getMeasurementsByDate(@RequestParam(name = "min")
                                                   @DateTimeFormat(pattern = ConfigConstants.DATE_PATTERN)
                                                           LocalDateTime min,
                                                   @RequestParam(name = "max")
                                                   @DateTimeFormat(pattern = ConfigConstants.DATE_PATTERN)
                                                           LocalDateTime max) {
        return measurementService.findAllByDate(min, max);
    }

    @GetMapping
    public List<Measurement> getAllMeasurements() {
        return measurementService.findAll();
    }

    @GetMapping("/lightOn")
    public void lightOn() {
       measurementService.lightOn();
    }

    @GetMapping("/lightOff")
    public void lightOff() {
        measurementService.lightOff();
    }

    @GetMapping("/{id}")
    public Measurement getMeasurementById(@PathVariable("id") String id) {
        return measurementService.findById(id);
    }

    @PostMapping
    public void saveMeasurement(@RequestBody MeasurementDTO measurementToConvert, HttpServletRequest request) {
        Measurement measurement = measurementToConvert.convertToMeasurement();
        measurementService.saveMeasurement(measurement);

        OnlineDevice deviceToRegister = new OnlineDevice(measurement.getMacAddress(), WebUtils.getIpAddress(request));

        Device device = new Device(measurement.getMacAddress());

        onlineDeviceRepository.register(deviceToRegister);
        deviceService.save(device);
    }
}
