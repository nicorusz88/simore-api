package ar.com.simore.simoreapi.entities.enums;

public enum VitalsEnum {
    //TODO: Add real URLs
    HEART_RATE("Hearth Rate", "BPM", "/activities/heart/date/today/1d.json", "/activities/heart/date/today/1d.json"),
    STEPS("Steps", "Steps", "/activities/heart/date/[base-date]/[end-date].json", "/activities/heart/date/[base-date]/[end-date].json"),
    BLOOD_OXYGEN("Blood Oxygen", "SO2", "/activities/heart/date/[base-date]/[end-date].json", "/activities/heart/date/[base-date]/[end-date].json"),
    BLOOD_PRESSURE("Blood Pressure", "mmHg", "/activities/heart/date/[base-date]/[end-date].json", "/activities/heart/date/[base-date]/[end-date].json"),
    WEIGHT("Weight", "KG", "/body/log/weight/date/today.json", "/activities/heart/date/[base-date]/[end-date].json"),
    DISTANCE("Distance", "KM", "/activities/heart/date/[base-date]/[end-date].json", "/activities/heart/date/[base-date]/[end-date].json"),
    BURNT_CALORIES("Burnt Calories", "Cal.", "/activities/heart/date/[base-date]/[end-date].json", "/activities/heart/date/[base-date]/[end-date].json"),
    SLEEP_TRACKING("Sleep Tracking", "Hour", "/activities/heart/date/[base-date]/[end-date].json", "/activities/heart/date/[base-date]/[end-date].json");

   private String name;
   private String unit;
   private String fitbitURL;
   private String withingsURL;

    VitalsEnum(String name, String unit, String fitbitURL, String withingsURL) {
        this.name = name;
        this.unit = unit;
        this.fitbitURL =  fitbitURL;
        this.withingsURL = withingsURL;
    }

    public String getFitbitURL() {
        return fitbitURL;
    }

    public void setFitbitURL(String fitbitURL) {
        this.fitbitURL = fitbitURL;
    }

    public String getWithingsURL() {
        return withingsURL;
    }

    public void setWithingsURL(String withingsURL) {
        this.withingsURL = withingsURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
