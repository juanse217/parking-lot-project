package com.sebastian.parkinglot.service.fee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

import org.springframework.stereotype.Component;

@Component
public class FeeCalculatorImpl implements FeeCalculator {

    private static final BigDecimal MINUTE_RATE = new BigDecimal("0.9");

    @Override
    public BigDecimal calculateFee(Duration parkingDuration) {
        
        BigDecimal timeInMins = new BigDecimal(parkingDuration.toMinutes());

        return MINUTE_RATE.multiply(timeInMins).setScale(2, RoundingMode.HALF_UP);//specifies how many decimal places and how to round. We can have only the decimal places
    }

    @Override
    public String formatDuration(Duration duration) {
        long hrs = duration.toHours();
        int mins = duration.toMinutesPart();
        return String.format("%dh %dm", hrs, mins);
    }

}
