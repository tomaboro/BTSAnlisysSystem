package BTSEvents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LeftBTSArea extends BTSEvent implements Serializable {

    @Override
    public String getName() {
        return "LEFT";
    }

    @JsonCreator
    public LeftBTSArea(@JsonProperty("id") String id, @JsonProperty("longitude") double longitude, @JsonProperty("latitude") double latitude, @JsonProperty("localDateTime") LocalDateTime localDateTime) {
        super(id, longitude, latitude, localDateTime);
    }
}
