package ar.com.simore.simoreapi.entities.resources;

import ar.com.simore.simoreapi.entities.FitBitHeartRateMeasurement;

import java.util.Date;
import java.util.List;

public class FitBitHeartRateMeasurementResource {

    private Date date;
    private List<FitBitHeartRateMeasurement> fitBitHeartRateMeasurementList;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<FitBitHeartRateMeasurement> getFitBitHeartRateMeasurementList() {
        return fitBitHeartRateMeasurementList;
    }

    public void setFitBitHeartRateMeasurementList(List<FitBitHeartRateMeasurement> fitBitHeartRateMeasurementList) {
        this.fitBitHeartRateMeasurementList = fitBitHeartRateMeasurementList;
    }
}
