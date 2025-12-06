package com.parking.view;

import com.parking.model.Car;
import com.parking.service.CarService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.List;

@Named

@SessionScoped
public class CarListController implements Serializable {

    @Inject
    private CarService carService;

    private List<Car> cars;


    private Car newCar;

    @PostConstruct
    public void init(){
        cars = carService.getAllCars();
        this.newCar = new Car();
    }

    public void initNewCar(){
        this.newCar = new Car();
    }

    public void saveCar(){
        try{
            carService.addCar(newCar);
            cars.add(newCar);

            newCar = new Car();
            PrimeFaces.current().executeScript("PF('carDialogWidget').hide()");
        }catch (IllegalArgumentException e){
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hiba", e.getMessage()));
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    public void deleteCar(Car car){
        try{
            carService.deleteCar(car.getLicensePlate());
            cars.remove(car);
        }catch (IllegalArgumentException e){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hiba", e.getMessage()));
            FacesContext.getCurrentInstance().validationFailed();
        }
    }

    public void onPageLoad(){
        this.cars = carService.getAllCars();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(facesContext != null){
            Object responseObj = facesContext.getExternalContext().getResponse();
            HttpServletResponse response = (HttpServletResponse) responseObj;
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
        }
    }

    public List<Car> getCars(){
        return cars;
    }
    public Car getNewCar() {
        return newCar;
    }


}
