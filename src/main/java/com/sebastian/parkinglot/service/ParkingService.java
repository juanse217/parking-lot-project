package com.sebastian.parkinglot.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sebastian.parkinglot.dto.CarDTO;
import com.sebastian.parkinglot.dto.CreateDTO;
import com.sebastian.parkinglot.dto.FeeResponseDTO;
import com.sebastian.parkinglot.exceptions.CarAlreadyExistsException;
import com.sebastian.parkinglot.exceptions.CarNotFoundException;
import com.sebastian.parkinglot.exceptions.InvalidLicensePlateException;
import com.sebastian.parkinglot.model.Car;
import com.sebastian.parkinglot.repository.ParkingRepository;

@Service
public class ParkingService {
    private static final BigDecimal MINUTE_RATE = new BigDecimal("0.9");
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

        if(repo.findByPlate(dto.getPlate()).isPresent()){ 
            throw new CarAlreadyExistsException("The car " + dto.getPlate() + " is already parked");
        } //not using the findByPlate method since it throws an exception if the car doesn't exist. 

        Car car = new Car(dto.getPlate());
        repo.save(car);

        return new CarDTO(car);
    }

    private Car findCarOrThrow(String plate){
        return repo.findByPlate(plate).orElseThrow(() -> new CarNotFoundException("Car with plate " + plate + " not found"));
    }

    public CarDTO findByPlate(String plate){
        validatePlate(plate);

        Car car = findCarOrThrow(plate);

        return new CarDTO(car);
    }

    public List<CarDTO> findAll(){
        return repo.findAll().stream().map(CarDTO::new).toList();
    }

    public void deleteCar(String plate){
        validatePlate(plate);
        findCarOrThrow(plate); 

        repo.deleteCar(plate);
    }

    public FeeResponseDTO checkout(String plate){
        validatePlate(plate);

        Car car = findCarOrThrow(plate);
        BigDecimal fee = calculateFeeAmount(car.getTimeIn());

        repo.deleteCar(plate);
        
        return new FeeResponseDTO(plate, formatDuration(car.getTimeIn()), fee);
    }

    public FeeResponseDTO calculateFee(String plate){
        validatePlate(plate);

        Car car = findCarOrThrow(plate);
        BigDecimal fee = calculateFeeAmount(car.getTimeIn());

        return new FeeResponseDTO(plate, formatDuration(car.getTimeIn()), fee);
    }

    private BigDecimal calculateFeeAmount(Duration timeIn){
        BigDecimal timeInMins = new BigDecimal(timeIn.toMinutes());
        return MINUTE_RATE.multiply(timeInMins).setScale(2, RoundingMode.HALF_UP);//specifies how many decimal places and how to round. We can have only the decimal places
    }

    private String formatDuration(Duration duration){
        long hrs = duration.toHours();
        int mins = duration.toMinutesPart();
        return String.format("\\dh \\dm", hrs, mins);
    }
}
