
package ar.com.simore.simoreapi.entities.json.fitbit.heartrate;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "activities-heart",
        "activities-heart-intraday"
})
public class FitBitHeartRate {

    @JsonProperty("activities-heart")
    private List<ActivitiesHeart> activitiesHeart = null;
    @JsonProperty("activities-heart-intraday")
    private ActivitiesHeartIntraday activitiesHeartIntraday;

    @JsonProperty("activities-heart")
    public List<ActivitiesHeart> getActivitiesHeart() {
        return activitiesHeart;
    }

    @JsonProperty("activities-heart")
    public void setActivitiesHeart(List<ActivitiesHeart> activitiesHeart) {
        this.activitiesHeart = activitiesHeart;
    }

    @JsonProperty("activities-heart-intraday")
    public ActivitiesHeartIntraday getActivitiesHeartIntraday() {
        return activitiesHeartIntraday;
    }

    @JsonProperty("activities-heart-intraday")
    public void setActivitiesHeartIntraday(ActivitiesHeartIntraday activitiesHeartIntraday) {
        this.activitiesHeartIntraday = activitiesHeartIntraday;
    }

}

