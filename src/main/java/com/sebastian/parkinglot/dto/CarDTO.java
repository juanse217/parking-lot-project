package com.sebastian.parkinglot.dto;

import java.time.Duration;
import java.time.LocalDateTime;


public class CarDTO {
    private String licensePlate;
    private LocalDateTime entryTime;
    private Duration totalTime;

    public CarDTO(){}

    public CarDTO(String plate, LocalDateTime entryTime, Duration totalTime){
        this.licensePlate = plate;
        this.entryTime = entryTime;
        this.totalTime = totalTime;
    }//We do not pass the Car class to avoid tight coupling

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
