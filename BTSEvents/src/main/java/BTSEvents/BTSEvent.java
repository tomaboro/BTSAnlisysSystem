package BTSEvents;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class BTSEvent {
    private final String id;
    private final double longitude;
    private final double latitude;

}
