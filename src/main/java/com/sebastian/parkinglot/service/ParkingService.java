package com.sebastian.parkinglot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sebastian.parkinglot.dto.CarDTO;
import com.sebastian.parkinglot.dto.CreateDTO;
import com.sebastian.parkinglot.exceptions.CarAlreadyExistsException;
import com.sebastian.parkinglot.exceptions.CarNotFoundException;
import com.sebastian.parkinglot.exceptions.InvalidLicensePlateException;
import com.sebastian.parkinglot.model.Car;
import com.sebastian.parkinglot.repository.ParkingRepository;

@Service
public class ParkingService {

    private final ParkingRepository repo; 

    public ParkingService(ParkingRepository repo){
        this.repo = repo;
    }

    private void validatePlate(String plate) {
        if (plate == null || plate.isBlank()) {
            throw new InvalidLicensePlateException("License plate cannot be null or empty");
        }
    }

    public CarDTO addCar(CreateDTO dto){
        validatePlate(dto.getPlate());

        if(repo.findByPlate(dto.getPlate()).isPresent()) throw new CarAlreadyExistsException("The car" + dto.getPlate() + " is already parked"); //not using the findByPlate method since it throws an exception if the car doesn't exist. 

        Car car = new Car(dto.getPlate());
        repo.save(car);

        return new CarDTO(car);
    }

    public CarDTO findByPlate(String plate){
        validatePlate(plate);

        Car car = repo.findByPlate(plate).orElseThrow(() -> new CarNotFoundException("Car with plate " + plate + " not found"));

        return new CarDTO(car);
    }

    public List<CarDTO> findAll(){
        return repo.findAll().stream().map(CarDTO::new).toList();
    }

    public void deleteCar(String plate){
        validatePlate(plate);
        //findByPlate(plate); //Just using it for validating existence. 

        repo.findByPlate(plate).orElseThrow(() -> new CarNotFoundException("Car with plate " + plate + " not found")); //better approach. Not creating DTOs that are unnecesary and more explicit. 

        repo.deleteCar(plate);
    }
}
