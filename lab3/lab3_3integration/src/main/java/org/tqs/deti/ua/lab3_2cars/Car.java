package org.tqs.deti.ua.lab3_2cars;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long carId;

    private String maker;
    private String model;

    public Car(){
        this.maker = "Undefined";
        this.model = "Undefined";
    }

    public Car(String maker, String model){
        this.maker = maker;
        this.model = model;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {

        this.carId = carId;
    }

    public String getMaker() {

        return maker;
    }

    public void setMaker(String maker) {

        this.maker = maker;
    }

    public String getModel() {

        return model;
    }

    public void setModel(String model) {

        this.model = model;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (carId ^ (carId >>> 32));
        result = prime * result + ((maker == null) ? 0 : maker.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Car other = (Car) obj;
        if (carId != other.carId)
            return false;
        if (maker == null) {
            if (other.maker != null)
                return false;
        } else if (!maker.equals(other.maker))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Car [carId=" + carId + ", maker=" + maker + ", model=" + model + "]";
    }

    
}
