package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Place {

    public enum LocationType {
        hotel, hostel, monument, airport, busStation, trainStation, club, unknown, error
    }

    private SimpleLocation location;
    private LocationType type;
    private String name;

    public static class ErrorPlace extends Place {
        public ErrorPlace() {
            super(new SimpleLocation(-1,-1),LocationType.error, "");
        }
    }

    @Override
    public String toString() {
        return type.name() + ", " + name;
    }
}
