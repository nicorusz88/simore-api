
package ar.com.simore.simoreapi.entities.json.fitbit.weight;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "weight"
})
public class FitBitWeight {

    @JsonProperty("weight")
    private List<Weight> weight = null;

    @JsonProperty("weight")
    public List<Weight> getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(List<Weight> weight) {
        this.weight = weight;
    }

}
