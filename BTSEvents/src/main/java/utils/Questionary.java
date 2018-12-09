package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Questionary {
    Boolean isTourist;
    Boolean isBuissness;
    TransportType arrivalType;
    Boolean wasPartying;

    public enum TransportType {
        car, train, bus, plane
    }
}
