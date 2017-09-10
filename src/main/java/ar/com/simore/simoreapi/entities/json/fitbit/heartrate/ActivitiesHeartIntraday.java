
package ar.com.simore.simoreapi.entities.json.fitbit.heartrate;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dataset",
    "datasetInterval",
    "datasetType"
})
public class ActivitiesHeartIntraday {

    @JsonProperty("dataset")
    private List<Dataset> dataset = null;
    @JsonProperty("datasetInterval")
    private Long datasetInterval;
    @JsonProperty("datasetType")
    private String datasetType;

    @JsonProperty("dataset")
    public List<Dataset> getDataset() {
        return dataset;
    }

    @JsonProperty("dataset")
    public void setDataset(List<Dataset> dataset) {
        this.dataset = dataset;
    }

    @JsonProperty("datasetInterval")
    public Long getDatasetInterval() {
        return datasetInterval;
    }

    @JsonProperty("datasetInterval")
    public void setDatasetInterval(Long datasetInterval) {
        this.datasetInterval = datasetInterval;
    }

    @JsonProperty("datasetType")
    public String getDatasetType() {
        return datasetType;
    }

    @JsonProperty("datasetType")
    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

}
