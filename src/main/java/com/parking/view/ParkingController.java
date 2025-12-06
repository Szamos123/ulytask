package com.parking.view;

import com.parking.model.Car;
import com.parking.service.CarService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;

@Named

@ViewScoped
public class ParkingController implements Serializable {
    @Inject
    private CarService carService;

    private String selectedLicensePlate;
    private Car selectedCar;


    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public void loadSelectedCar(){
        if(selectedLicensePlate != null){
            this.selectedCar = carService.getCarByPlate(selectedLicensePlate);
        }
    }
    public String parkCar(String parkingLotName){
        if(startDate.isAfter(endDate)){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hiba:", "A parkolás kezdetének dátuma nem lehet későbbi dátum, mint a parkolás végének dátuma!"));
            return null;
        }
        if(selectedCar.isParked()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hiba:", "Ez az autó már a parkolóban van!"));
            return null;
        }
        try{
            selectedCar.setParked(true);
            selectedCar.setParkingStartDate(startDate);
            selectedCar.setParkingEndDate(endDate);
            selectedCar.setParkingLotName(parkingLotName);


        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hiba:", e.getMessage()));
            
        }

      return null;
    }
    public String getSelectedLicensePlate() {return selectedLicensePlate;}
    public void setSelectedLicensePlate(String selectedLicensePlate) {this.selectedLicensePlate = selectedLicensePlate;}

    public Car getSelectedCar() {return selectedCar;}
    public void setSelectedCar(Car selectedCar) {this.selectedCar = selectedCar;}

    public LocalDateTime getStartDate() {return startDate;}
    public void setStartDate(LocalDateTime startDate) {this.startDate = startDate;}

    public LocalDateTime getEndDate() {return endDate;}
    public void setEndDate(LocalDateTime endDate) {this.endDate = endDate;}

}
