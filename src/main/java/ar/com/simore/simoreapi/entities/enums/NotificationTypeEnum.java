package ar.com.simore.simoreapi.entities.enums;

/**
 * Indicates the type of notification
 */
public enum NotificationTypeEnum {
    RECOMMENDATION("Recomendación", "%s"),
    MEDICATION("Tome su medicina", "Acordate de tomar %s Mg de %s"),
    CHECKIN("Complete la encuesta", "%s"),
    APPOINTMENT("Tienes una cita","Tienes que ver al doctor %s en %s a las %s");

    /**
     * Is the tittle tht will be shown in the notification
     */
    private String tittle;
    private String body;

    NotificationTypeEnum(String tittle, String body) {
        this.tittle = tittle;
        this.body = body;
    }

    public String getTitle() {
        return tittle;
    }

    public String getBody() {
        return body;
    }
}
