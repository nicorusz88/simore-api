
package ar.com.simore.simoreapi.entities.json.fitbit.heartrate;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.io.Serializable;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dateTime",
    "value"
})
public class ActivitiesHeart implements Serializable
{
    private final static long serialVersionUID = -5954226526320115589L;
    @JsonProperty("dateTime")
    private String dateTime;
    @JsonProperty("value")
    private Value value;


    @JsonProperty("dateTime")
    public String getDateTime() {
        return dateTime;
    }

    @JsonProperty("dateTime")
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @JsonProperty("value")
    public Value getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Value value) {
        this.value = value;
    }

}
