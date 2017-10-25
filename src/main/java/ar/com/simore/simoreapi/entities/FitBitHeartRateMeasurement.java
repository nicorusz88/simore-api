package ar.com.simore.simoreapi.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="fitbitheartratemeasurement")
public class FitBitHeartRateMeasurement extends Measurement {

    private String name;
    private long caloriesOut;
    private long max;
    private long min;
    private long minutes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCaloriesOut() {
        return caloriesOut;
    }

    public void setCaloriesOut(long caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "FitBitHeartRateMeasurement{" +
                "name='" + name + '\'' +
                ", caloriesOut=" + caloriesOut +
                ", max=" + max +
                ", min=" + min +
                ", minutes=" + minutes +
                '}';
    }
}
