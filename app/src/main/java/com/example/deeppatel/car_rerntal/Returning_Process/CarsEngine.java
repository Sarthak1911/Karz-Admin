package com.example.deeppatel.car_rerntal.Returning_Process;

import com.example.deeppatel.car_rerntal.Returning_Process.Car;

import java.util.ArrayList;
import java.util.List;

public class CarsEngine {

    private List<Car> carList = new ArrayList<>();

    public CarsEngine(List<Car> carList) {
        this.carList = carList;
    }

    public CarsEngine() {

    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public int getCount(){

        return this.carList.size();

    }

    public Car getCar(int position){

        if(carList.size() > 0){

            return carList.get(position);

        }

        return null;

    }

    public void addCars(int count){

        for(int i = 0; i < count; i++){

            carList.add(new Car("Car " + i, "Model " + i, "Booked by", "Booked on", "Available on"));

        }

    }


}
