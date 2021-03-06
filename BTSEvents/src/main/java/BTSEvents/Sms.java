package BTSEvents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Sms extends BTSEvent implements Serializable {

    @Override
    public String getName() {
        return "ENTER";
    }

    @JsonCreator
    public Sms(@JsonProperty("id") String id,@JsonProperty("longitude") double longitude,@JsonProperty("latitude") double latitude, @JsonProperty("localDateTime") LocalDateTime localDateTime) {
        super(id, longitude, latitude, localDateTime);
    }
}
