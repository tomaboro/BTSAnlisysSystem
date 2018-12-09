package BTSEvents;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public abstract class BTSEvent implements Serializable {
    private final String id;
    private final double longitude;
    private final double latitude;
    private final LocalDateTime localDateTime;

    public BTSEvent(String id, double longitude, double latitude, LocalDateTime localDateTime) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.localDateTime = localDateTime;
    }
}
