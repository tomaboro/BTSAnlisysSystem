package BTSEvents;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public abstract class BTSEvent {
    private final String id;
    private final double longitude;
    private final double latitude;

    public BTSEvent(String id, double longitude, double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
