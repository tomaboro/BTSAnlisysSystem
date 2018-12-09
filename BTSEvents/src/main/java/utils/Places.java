package utils;

import java.util.HashMap;
import java.util.Map;

public abstract class Places {
    public static final Map<SimpleLocation,Place> hotels;
    public static final Map<SimpleLocation,Place> hostels;
    public static final Map<SimpleLocation,Place> trainStations;
    public static final Map<SimpleLocation,Place> busStations;
    public static final Map<SimpleLocation,Place> airports;
    public static final Map<SimpleLocation,Place> monuments;
    public static final Map<SimpleLocation,Place> clubs;

    static {
        hotels = new HashMap<>();
        hostels = new HashMap<>();
        trainStations = new HashMap<>();
        busStations = new HashMap<>();
        airports = new HashMap<>();
        monuments = new HashMap<>();
        clubs = new HashMap<>();
    }
}
