package com.sebastian.parkinglot.service;

import java.math.BigDecimal;
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
import com.sebastian.parkinglot.service.fee.FeeCalculator;

// REMOVED these to comply with SRP:
    // - private static final BigDecimal MINUTE_RATE
    // - private BigDecimal calculateFeeAmount(Duration timeIn)
    // - private String formatDuration(Duration duration)

@Service
public class ParkingService {
    
    private final ParkingRepository repo; 
    private final FeeCalculator feeCalculator;

    public ParkingService(ParkingRepository repo, FeeCalculator feeCalculator){
        this.repo = repo;
        this.feeCalculator = feeCalculator;
    }

    private void validatePlate(String plate) {
        if (plate == null || plate.isBlank()) {
            throw new InvalidLicensePlateException("License plate cannot be null or empty");
        }
    }

    //Helper method for DRY, no repetition.
    private CarDTO toCarDTO(Car car){
        return new CarDTO(car.getLicensePlate(), car.getEntryTime(), car.getTimeIn()); 
    }

    public CarDTO addCar(CreateDTO dto){
        validatePlate(dto.getPlate());

        if(repo.exists(dto.getPlate())){ 
            throw new CarAlreadyExistsException("The car " + dto.getPlate() + " is already parked");
        } //not using the findByPlate method since it throws an exception if the car doesn't exist. 

        Car car = new Car(dto.getPlate());
        repo.save(car);

        return toCarDTO(car);
    }

    private Car findCarOrThrow(String plate){
        return repo.findByPlate(plate).orElseThrow(() -> new CarNotFoundException("Car with plate " + plate + " not found"));
    }

    public CarDTO findByPlate(String plate){
        validatePlate(plate);

        Car car = findCarOrThrow(plate);

        return toCarDTO(car);
    }

    public List<CarDTO> findAll(){
        return repo.findAll().stream().map(c -> toCarDTO(c)).toList();
    }

    public void deleteCar(String plate){
        validatePlate(plate);
        findCarOrThrow(plate); 

        repo.deleteCar(plate);
    }

    public FeeResponseDTO checkout(String plate){
        FeeResponseDTO fee = calculateFee(plate);

        repo.deleteCar(plate);
        
        return fee;
    }

    public FeeResponseDTO calculateFee(String plate){
        validatePlate(plate);

        Car car = findCarOrThrow(plate);
        Duration timeInLot = car.getTimeIn();//To avoid calling same method twice. 
        BigDecimal fee = feeCalculator.calculateFee(timeInLot);

        return new FeeResponseDTO(plate, feeCalculator.formatDuration(timeInLot), fee);
    }
}
