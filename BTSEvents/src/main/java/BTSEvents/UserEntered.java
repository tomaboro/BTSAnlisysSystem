package BTSEvents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserEntered implements BTSEvent, Serializable {
    private final String id;

    public UserEntered(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
