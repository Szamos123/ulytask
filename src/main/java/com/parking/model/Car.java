package com.parking.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Car implements Serializable {

    private String licensePlate;
    private String brand;
    private String type;
    private String color;
    private boolean isParked;


    private String parkingLotName;
    private LocalDateTime parkingStartDate;
    private LocalDateTime parkingEndDate;

    public Car(){}

    public Car(String licensePlate, String brand, String type, String color, boolean isParked) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.type = type;
        this.color = color;

        this.isParked = false;
    }
    public String getLicensePlate() {return licensePlate;}
    public void setLicensePlate(String licensePlate) {this.licensePlate = licensePlate;}

    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}

    public boolean isParked() {return isParked;}
    public void setParked(boolean parked) {isParked = parked;}

    public String getParkingLotName() {return parkingLotName;}
    public void setParkingLotName(String parkingLotName) {this.parkingLotName = parkingLotName;}

    public LocalDateTime getParkingEndDate() {return parkingEndDate;}
    public void setParkingEndDate(LocalDateTime parkingEndDate) {this.parkingEndDate = parkingEndDate;}

    public LocalDateTime getParkingStartDate() {return parkingStartDate;}
    public void setParkingStartDate(LocalDateTime parkingStartDate) {this.parkingStartDate = parkingStartDate;}

    public boolean isParkingActive(){
        return isParked && LocalDateTime.now().isBefore(parkingEndDate);
    }

    public void resetParkingState(){
        if(isParked && parkingEndDate != null){
            if(LocalDateTime.now().isAfter(parkingEndDate)){
                this.isParked=false;
                this.parkingLotName = null;
                this.parkingStartDate = null;
                this.parkingEndDate = null;
            }
        }
    }

    @Override
    public int hashCode(){
        return licensePlate.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(licensePlate, car.licensePlate);
    }

}
