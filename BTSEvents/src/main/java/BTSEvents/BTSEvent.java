package BTSEvents;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BTSEvent {
    private final String id;
    private final double longitude;
    private final double latitude;
    private final LocalDateTime time;

    public BTSEvent(String id, double longitude, double latitude, LocalDateTime time) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
    }
}
