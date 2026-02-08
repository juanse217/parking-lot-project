package com.sebastian.parkinglot.dto;

import java.math.BigDecimal;

public class FeeResponseDTO {
    private String plate;
    private String duration;
    private BigDecimal fee;

    public FeeResponseDTO(String plate, String duration, BigDecimal fee){
        this.plate = plate;
        this.duration = duration;
        this.fee = fee;
    }

    public String getPlate() {
        return plate;
    }

    public String getDuration() {
        return duration;
    }

    public BigDecimal getFee() {
        return fee;
    }

    
}
