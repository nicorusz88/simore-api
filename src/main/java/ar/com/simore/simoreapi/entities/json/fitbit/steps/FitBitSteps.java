
package ar.com.simore.simoreapi.entities.json.fitbit.steps;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "activities-steps"
})
public class FitBitSteps {

    @JsonProperty("activities-steps")
    private List<ActivitiesStep> activitiesSteps = null;

    @JsonProperty("activities-steps")
    public List<ActivitiesStep> getActivitiesSteps() {
        return activitiesSteps;
    }

    @JsonProperty("activities-steps")
    public void setActivitiesSteps(List<ActivitiesStep> activitiesSteps) {
        this.activitiesSteps = activitiesSteps;
    }

}
