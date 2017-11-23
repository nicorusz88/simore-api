package ar.com.simore.simoreapi.entities.enums;

/**
 * Indicates the type of notification
 */
public enum NotificationTypeEnum {
    RECOMMENDATION("Rcommendation"),
    MEDICATION("Take your medication"),
    CHECKIN("Complete the survey"),
    APPOINTMENT("You have an appointment");

    /**
     * Is the tittle tht will be shown in the notification
     */
    private String tittle;

    NotificationTypeEnum(String tittle) {
        this.tittle = tittle;
    }

    public String getTitle() {
        return tittle;
    }
}
