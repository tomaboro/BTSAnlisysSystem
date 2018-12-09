package BTSEvents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserExited extends BTSEvent implements Serializable {

    public UserExited(@JsonProperty("id") String id) {
        super(id);
    }
}
