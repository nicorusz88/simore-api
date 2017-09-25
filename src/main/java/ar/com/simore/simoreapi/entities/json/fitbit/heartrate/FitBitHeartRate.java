
package ar.com.simore.simoreapi.entities.json.fitbit.heartrate;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "activities-heart"
})
public class FitBitHeartRate implements Serializable
{
    private final static long serialVersionUID = 4983464079728668475L;
    @JsonProperty("activities-heart")
    private List<ActivitiesHeart> activitiesHeart = null;

    @JsonProperty("activities-heart")
    public List<ActivitiesHeart> getActivitiesHeart() {
        return activitiesHeart;
    }

    @JsonProperty("activities-heart")
    public void setActivitiesHeart(List<ActivitiesHeart> activitiesHeart) {
        this.activitiesHeart = activitiesHeart;
    }

}
