
package ar.com.simore.simoreapi.entities.json.fitbit.heartrate;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "customHeartRateZones",
    "dateTime",
    "heartRateZones",
    "value"
})
public class ActivitiesHeart {

    @JsonProperty("customHeartRateZones")
    private List<Object> customHeartRateZones = null;
    @JsonProperty("dateTime")
    private String dateTime;
    @JsonProperty("heartRateZones")
    private List<HeartRateZone> heartRateZones = null;
    @JsonProperty("value")
    private String value;

    @JsonProperty("customHeartRateZones")
    public List<Object> getCustomHeartRateZones() {
        return customHeartRateZones;
    }

    @JsonProperty("customHeartRateZones")
    public void setCustomHeartRateZones(List<Object> customHeartRateZones) {
        this.customHeartRateZones = customHeartRateZones;
    }

    @JsonProperty("dateTime")
    public String getDateTime() {
        return dateTime;
    }

    @JsonProperty("dateTime")
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @JsonProperty("heartRateZones")
    public List<HeartRateZone> getHeartRateZones() {
        return heartRateZones;
    }

    @JsonProperty("heartRateZones")
    public void setHeartRateZones(List<HeartRateZone> heartRateZones) {
        this.heartRateZones = heartRateZones;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

}
