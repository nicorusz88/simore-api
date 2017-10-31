
package ar.com.simore.simoreapi.entities.json.fitbit.heartrate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "caloriesOut",
    "max",
    "min",
    "minutes",
    "name"
})
public class HeartRateZone implements Serializable
{
    private final static long serialVersionUID = 3253021762247925560L;
    @JsonProperty("caloriesOut")
    private Long caloriesOut;
    @JsonProperty("max")
    private Long max;
    @JsonProperty("min")
    private Long min;
    @JsonProperty("minutes")
    private Long minutes;
    @JsonProperty("name")
    private String name;


    @JsonProperty("caloriesOut")
    public Long getCaloriesOut() {
        return caloriesOut;
    }

    @JsonProperty("caloriesOut")
    public void setCaloriesOut(Long caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    @JsonProperty("max")
    public Long getMax() {
        return max;
    }

    @JsonProperty("max")
    public void setMax(Long max) {
        this.max = max;
    }

    @JsonProperty("min")
    public Long getMin() {
        return min;
    }

    @JsonProperty("min")
    public void setMin(Long min) {
        this.min = min;
    }

    @JsonProperty("minutes")
    public Long getMinutes() {
        return minutes;
    }

    @JsonProperty("minutes")
    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

}
