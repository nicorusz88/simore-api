package ar.com.simore.simoreapi.entities.enums;

/**
 * It contains the wearable brands we will be using
 */
public enum WearableTypeEnum {
    FITBIT("Fitbit"), WITHINGS("Withings");

    private String name;

    WearableTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
