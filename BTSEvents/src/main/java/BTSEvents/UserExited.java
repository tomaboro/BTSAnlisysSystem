package BTSEvents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserExited extends BTSEvent implements Serializable {

    @Override
    public String getName() {
        return "LEFT";
    }

    public UserExited(@JsonProperty("id") String id) {
        super(id);
    }
}
