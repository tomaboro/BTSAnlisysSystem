package BTSEvents;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public abstract class BTSEvent implements Serializable {
    private final String id;
    private double longitude;
    private double latitude;
    private LocalDateTime localDateTime;

    public BTSEvent(String id){
        this.id = id;
    }

    public BTSEvent(String id, double longitude, double latitude, LocalDateTime localDateTime) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.localDateTime = localDateTime;
    }
}
