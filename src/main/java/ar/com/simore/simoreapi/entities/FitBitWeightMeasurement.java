package ar.com.simore.simoreapi.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "fitbitweightmeasurement")
public class FitBitWeightMeasurement extends Measurement {

    private Double bmi;
    private Double weight;
    private Double fat;

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    @Override
    public String toString() {
        return "FitBitWeightMeasurement{" +
                "bmi=" + bmi +
                ", weight=" + weight +
                ", fat=" + fat +
                '}';
    }
}
