
package ar.com.simore.simoreapi.entities.json.fitbit.steps;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.List;

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
