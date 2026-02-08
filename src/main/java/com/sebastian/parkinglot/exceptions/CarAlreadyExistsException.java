package com.sebastian.parkinglot.exceptions;

public class CarAlreadyExistsException extends RuntimeException{
    public CarAlreadyExistsException(String msg){
        super(msg);
    }
}
