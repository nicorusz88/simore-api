package ar.com.simore.simoreapi.entities.utils;

public enum VitalsEnum {
    HEART_RATE("Hearth Rate", "BPM"),
    STEPS("Steps", "Steps"),
    BLOOD_OXYGEN("Blood Oxygen", "SO2"),
    BLOOD_PRESSURE("Blood Pressure", "mmHg"),
    WEIGHT("Weight", "KG"),
    DISTANCE("Distance", "KM"),
    BURNT_CALORIES("Burnt Calories", "Cal."),
    SLEEP_TRACKING("Sleep Tracking", "Hour");

   private String name;
   private String unit;

    VitalsEnum(String name, String unit) {
        this.name = name;
        this.unit = unit;
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
