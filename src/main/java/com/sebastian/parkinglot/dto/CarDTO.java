package com.sebastian.parkinglot.dto;

import java.time.Duration;
import java.time.LocalDateTime;

import com.sebastian.parkinglot.model.Car;

public class CarDTO {
    private String licensePlate;
    private LocalDateTime entryTime;
    private Duration totalTime;

    public CarDTO(Car car){
        this.licensePlate = car.getLicensePlate();
        this.entryTime = car.getEntryTime();
        this.totalTime = car.getTimeIn();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public Duration getTotalTime() {
        return totalTime;
    }

    
}
