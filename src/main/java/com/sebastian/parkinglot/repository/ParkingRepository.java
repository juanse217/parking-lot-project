package com.sebastian.parkinglot.repository;

import java.util.Collection;
import java.util.Optional;

import com.sebastian.parkinglot.model.Car;

public interface ParkingRepository {
    Car save(Car car); //No need to return optional since it's already a valid object
    Collection<Car> findAll();
    Optional<Car> findByPlate(String plate);
    void deleteCar(String plate);
}
