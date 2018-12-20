package BTSEvents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserEntered extends BTSEvent implements Serializable {

    @Override
    public String getName() {
        return "ENTER";
    }

    public UserEntered(@JsonProperty("id") String id) {
        super(id);
    }
}
