
package ar.com.simore.simoreapi.entities.json.fitbit.distance;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "activities-distance"
})
public class FitBitDistance {

    @JsonProperty("activities-distance")
    private List<ActivitiesDistance> activitiesDistance = null;

    @JsonProperty("activities-distance")
    public List<ActivitiesDistance> getActivitiesDistance() {
        return activitiesDistance;
    }

    @JsonProperty("activities-distance")
    public void setActivitiesDistance(List<ActivitiesDistance> activitiesDistance) {
        this.activitiesDistance = activitiesDistance;
    }

}
