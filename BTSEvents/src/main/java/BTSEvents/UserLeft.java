package BTSEvents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserLeft implements BTSEvent, Serializable {
    private final String id;

    public UserLeft(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
