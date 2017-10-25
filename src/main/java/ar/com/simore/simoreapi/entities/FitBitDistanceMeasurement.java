package ar.com.simore.simoreapi.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "fitbitdistancemeasurement")
public class FitBitDistanceMeasurement extends Measurement {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FitBitDistanceMeasurement{" +
                "value=" + value +
                '}';
    }
}
