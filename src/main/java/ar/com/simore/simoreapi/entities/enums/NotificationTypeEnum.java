package ar.com.simore.simoreapi.entities.enums;

/**
 * Indicates the type of notification
 */
public enum NotificationTypeEnum {
    RECOMMENDATION("Rcommendation", ""),
    MEDICATION("Take your medication", "Remember to take %s Mg of %s"),
    CHECKIN("Complete the survey", ""),
    APPOINTMENT("You have an appointment","You have to see doctor %s in %s at %s");

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
