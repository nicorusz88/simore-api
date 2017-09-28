
package ar.com.simore.simoreapi.entities.json.fitbit.weight;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "bmi",
    "date",
    "fat",
    "logId",
    "time",
    "weight",
    "source"
})
public class Weight {

    @JsonProperty("bmi")
    private Double bmi;
    @JsonProperty("date")
    private String date;
    @JsonProperty("fat")
    private Double fat;
    @JsonProperty("logId")
    private BigInteger logId;
    @JsonProperty("time")
    private String time;
    @JsonProperty("weight")
    private Double weight;
    @JsonProperty("source")
    private String source;

    @JsonProperty("bmi")
    public Double getBmi() {
        return bmi;
    }

    @JsonProperty("bmi")
    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("fat")
    public Double getFat() {
        return fat;
    }
    @JsonProperty("fat")
    public void setFat(Double fat) {
        this.fat = fat;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("logId")
    public BigInteger getLogId() {
        return logId;
    }

    @JsonProperty("logId")
    public void setLogId(BigInteger logId) {
        this.logId = logId;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("weight")
    public Double getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

}
