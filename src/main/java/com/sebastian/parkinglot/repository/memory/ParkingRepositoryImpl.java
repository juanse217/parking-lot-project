package com.sebastian.parkinglot.repository.memory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sebastian.parkinglot.model.Car;
import com.sebastian.parkinglot.repository.ParkingRepository;

@Repository
public class ParkingRepositoryImpl implements ParkingRepository{
    
    private final Map<String, Car> carMap = new HashMap<>();

    @Override
    public Car save(Car car) {
        carMap.put(car.getLicensePlate(), car);
        return car;
    }

    @Override
    public Collection<Car> findAll() {
        return Collections.unmodifiableCollection(carMap.values());
    }

    @Override
    public Optional<Car> findByPlate(String plate) {
        return Optional.ofNullable(carMap.get(plate));
    }

    @Override
    public void deleteCar(String plate) {
        carMap.remove(plate);
    }

}
