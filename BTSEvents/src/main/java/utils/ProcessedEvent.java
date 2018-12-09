package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ProcessedEvent {
    private Place place;
    private LocalDateTime time;
}
