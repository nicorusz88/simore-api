package ar.com.simore.simoreapi.entities.utils;

public enum VitalTypesEnum {
    HEART_RATE("Hearth Rate"),
    STEPS("Steps"),
    BLOOD_OXYGEN("Blood Oxygen"),
    BLOOD_PRESSURE("Blood Pressure"),
    WEIGHT("Weight"),
    DISTANCE("Distance"),
    BURNT_CALORIES("Burnt Calories"),
    SLEEP_TRACKING("Sleep Tracking");

   private String name;

    VitalTypesEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
