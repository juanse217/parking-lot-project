package com.sebastian.parkinglot.service.fee;

import java.math.BigDecimal;
import java.time.Duration;
//Interface to comply with OCP, in case, in the future we want to add more ways to calculate.
public interface FeeCalculator {
    BigDecimal calculateFee(Duration parkingDuration);
    String formatDuration(Duration duration);
}
