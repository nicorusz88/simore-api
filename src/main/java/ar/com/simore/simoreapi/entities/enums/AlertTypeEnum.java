package ar.com.simore.simoreapi.entities.enums;

public enum AlertTypeEnum {
    FITBIT_HEART_RATE_RESTING_HEART("Umbral de Frecuencia cardiaca", "Alerta cuando se sobrepasa el umbral de frecuencia cardiaca de descanso, medidos con dispositivos Fitbit", "El usuario %s ha sobrepasado el umbral de frecuencia cardiaca de decanso. Umbral %s, obtenido %s."),
    FITBIT_WEIGHT("Umbral de peso", "Alerta cuando se sobrepasa el umbral de peso, medidos con dispositivos Fitbit", "El usuario %s ha sobrepasado el umbral de peso. Umbral %s, obtenido %s."),
    FITBIT_BURNT_CALORIES("Umbral de calorias quemadas", "Alerta cuando se encuentra debajo del umbral de calorias quemadas, medidos con dispositivos Fitbit", "El usuario %s se encuentra por debajo del umbral de calorias quemadas. Umbral %s, obtenido %s."),
    FITBIT_DISTANCE("Umbral de distancia", "Alerta cuando se encuentra debajo del umbral de distancia recorrida, medidos con dispositivos Fitbit", "El usuario %s se encuentra por debajo del umbral de distancia recorrida. Umbral %s, obtenido %s."),
    WITHINGS_BLOOD_OXYGEN("Umbral de oxigenacion en sangre", "Alerta cuando se encuentra debajo del umbral de oxigenacion en sangre, medidos con dispositivos Withings","El usuario %s se encuentra por debajo del umbral de oxigenacion de sangre. Umbral %s, obtenido %s.");

    private String tittle;
    private String description;
    private String alertMessageForProfessional;

    AlertTypeEnum(String tittle, String description, String alertMessageForProfessional) {
        this.tittle = tittle;
        this.description = description;
        this.alertMessageForProfessional = alertMessageForProfessional;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlertMessageForProfessional() {
        return alertMessageForProfessional;
    }

    public void setAlertMessageForProfessional(String alertMessageForProfessional) {
        this.alertMessageForProfessional = alertMessageForProfessional;
    }
}
