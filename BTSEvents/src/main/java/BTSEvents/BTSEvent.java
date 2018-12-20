package BTSEvents;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public abstract class BTSEvent implements Serializable {
    public static final String separator = ", ";
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

    public abstract String getName();

    @Override
    public String toString() {
        return id + separator + localDateTime.getDayOfMonth() + "." + localDateTime.getMonthValue() + "." + localDateTime.getYear() + separator + localDateTime.getHour() + ":" + localDateTime.getMinute() +  separator + longitude + separator + latitude + separator + getName();
    }
}
