package BTSEvents;

import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class BTSEvent implements Serializable {
    private final String id;
    private final double longitude;
    private final double latitude;

    public BTSEvent(String id, double longitude, double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
