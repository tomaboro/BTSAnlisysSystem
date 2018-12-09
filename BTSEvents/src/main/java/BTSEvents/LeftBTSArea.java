package BTSEvents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class LeftBTSArea extends BTSEvent implements Serializable {

    @JsonCreator
    public LeftBTSArea(@JsonProperty("id") String id, @JsonProperty("longitude") double longitude, @JsonProperty("latitude") double latitude) {
        super(id, longitude, latitude);
    }
}
