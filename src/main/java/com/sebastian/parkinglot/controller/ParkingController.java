package com.sebastian.parkinglot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sebastian.parkinglot.dto.CarDTO;
import com.sebastian.parkinglot.dto.CreateDTO;
import com.sebastian.parkinglot.dto.FeeResponseDTO;
import com.sebastian.parkinglot.service.ParkingService;



@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService service;

    public ParkingController(ParkingService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{plate}")
    public ResponseEntity<CarDTO> getCarByPlate(@PathVariable String plate) {
        return ResponseEntity.ok(service.findByPlate(plate));
    }

    @GetMapping("/{plate}/fee") //method to calculate fee (view only)
    public ResponseEntity<FeeResponseDTO> getFee(@PathVariable String plate) {
        return ResponseEntity.ok(service.calculateFee(plate));
    }
    
    @PostMapping("/{plate}/checkout")//method for checkout, deletes car and shows fee. 
    public ResponseEntity<FeeResponseDTO> checkout(@PathVariable String plate) {
        return ResponseEntity.ok(service.checkout(plate));
    }
    
    @PostMapping
    public ResponseEntity<CarDTO> createCar(@RequestBody CreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCar(dto));
    }

    @DeleteMapping("/{plate}")//an admin-for method. The way users remove cars is via checkout
    public ResponseEntity<Void> deleteCar(@PathVariable String plate){
        service.deleteCar(plate);
        return ResponseEntity.noContent().build();
    }
}
