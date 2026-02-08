package com.sebastian.parkinglot.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Car {
    private final String licensePlate;
    private LocalDateTime entryTime;

    public Car(String licensePlate){
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public Duration getTimeIn(){
        return Duration.between(entryTime, LocalDateTime.now());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((licensePlate == null) ? 0 : licensePlate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Car other = (Car) obj;
        if (licensePlate == null) {
            if (other.licensePlate != null)
                return false;
        } else if (!licensePlate.equals(other.licensePlate))
            return false;
        return true;
    }

    
}
