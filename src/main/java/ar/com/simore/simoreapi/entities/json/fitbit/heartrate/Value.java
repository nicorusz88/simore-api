
package ar.com.simore.simoreapi.entities.json.fitbit.heartrate;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.codehaus.jackson.annotate.JsonIgnore;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "customHeartRateZones",
    "heartRateZones",
    "restingHeartRate"
})
public class Value implements Serializable
{
    private final static long serialVersionUID = -116518925873014584L;

    @JsonIgnore
    @JsonProperty("customHeartRateZones")
    private List<Object> customHeartRateZones = null;
    @JsonProperty("heartRateZones")
    private List<HeartRateZone> heartRateZones = null;
    @JsonProperty("restingHeartRate")
    private Long restingHeartRate;


    @JsonProperty("customHeartRateZones")
    public List<Object> getCustomHeartRateZones() {
        return customHeartRateZones;
    }

    @JsonProperty("customHeartRateZones")
    public void setCustomHeartRateZones(List<Object> customHeartRateZones) {
        this.customHeartRateZones = customHeartRateZones;
    }

    @JsonProperty("heartRateZones")
    public List<HeartRateZone> getHeartRateZones() {
        return heartRateZones;
    }

    @JsonProperty("heartRateZones")
    public void setHeartRateZones(List<HeartRateZone> heartRateZones) {
        this.heartRateZones = heartRateZones;
    }

    @JsonProperty("restingHeartRate")
    public Long getRestingHeartRate() {
        return restingHeartRate;
    }

    @JsonProperty("restingHeartRate")
    public void setRestingHeartRate(Long restingHeartRate) {
        this.restingHeartRate = restingHeartRate;
    }

}
