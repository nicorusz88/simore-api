package ar.com.simore.simoreapi.entities;

import ar.com.simore.simoreapi.entities.enums.AlertTypeEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "alerts")
public class Alert extends BaseTreatmentComponent  {

    @NotNull
    @Enumerated(EnumType.STRING)
    private AlertTypeEnum alertTypeEnum;

    private String description;

    private Long threshold;

    /**
     * Indicates the date that the alert has been processed and triggered
     * If it was already processed and triggered for the current day
     * then it should not be triggered again.
     */
    private Date triggeredDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTriggeredDate() {
        return triggeredDate;
    }

    public void setTriggeredDate(Date triggeredDate) {
        this.triggeredDate = triggeredDate;
    }

    public AlertTypeEnum getAlertTypeEnum() {
        return alertTypeEnum;
    }

    public void setAlertTypeEnum(AlertTypeEnum alertTypeEnum) {
        this.alertTypeEnum = alertTypeEnum;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "alertTypeEnum=" + alertTypeEnum +
                ", description='" + description + '\'' +
                ", threshold=" + threshold +
                ", triggeredDate=" + triggeredDate +
                '}';
    }
}
