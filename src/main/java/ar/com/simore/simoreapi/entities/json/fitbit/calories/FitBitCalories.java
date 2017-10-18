
package ar.com.simore.simoreapi.entities.json.fitbit.calories;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "activities-calories"
})
public class FitBitCalories {

    @JsonProperty("activities-calories")
    private List<ActivitiesCalory> activitiesCalories = null;

    @JsonProperty("activities-calories")
    public List<ActivitiesCalory> getActivitiesCalories() {
        return activitiesCalories;
    }

    @JsonProperty("activities-calories")
    public void setActivitiesCalories(List<ActivitiesCalory> activitiesCalories) {
        this.activitiesCalories = activitiesCalories;
    }

}
