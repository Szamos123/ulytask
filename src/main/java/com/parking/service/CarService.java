package com.parking.service;

import com.parking.model.Car;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped //minden felhasznalo ugyanazt latja, szerver indulastol leallasig tart
public class CarService {
    private final Map<String, Car> carMap = new ConcurrentHashMap<>();

    public CarService() {}

    //AUTÓ HOZZÁADÁSA
    public void addCar(Car newCar){
        if(carMap.containsKey(newCar.getLicensePlate())){
            throw new IllegalArgumentException("Ilyen rendszámmal már létezik autó a rendszerben!");
        }
        carMap.put(newCar.getLicensePlate(), newCar);
    }
    //AUTÓ TÖRLÉSE
    public void deleteCar(String licensePlate){
        Car car = carMap.get(licensePlate);

        if(car != null){
            if(car.isParked()){
                throw new IllegalArgumentException("Ez az autó nem törölhető, mert éppen parkol!");
            }
            carMap.remove(licensePlate);
        }
    }

    public List<Car> getAllCars(){
        List<Car> allCars = new ArrayList<>(carMap.values());

        for(Car car:allCars){
            car.resetParkingState();
        }
        return allCars;
    }

    public Car getCarByPlate(String licensePlate){
        return carMap.get(licensePlate);
    }

}
