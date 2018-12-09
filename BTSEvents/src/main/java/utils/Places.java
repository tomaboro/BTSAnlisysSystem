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

    static {
        hotels = new HashMap<>();
        SimpleLocation hotelLocation = new SimpleLocation(0,0);
        hotels.put(hotelLocation,new Place(hotelLocation, Place.LocationType.hotel,"Hotel Krakowiak"));
        hostels = new HashMap<>();
        SimpleLocation hostelLocation = new SimpleLocation(0,1);
        hotels.put(hostelLocation,new Place(hostelLocation, Place.LocationType.hotel,"Bison Hostel"));
        trainStations = new HashMap<>();
        SimpleLocation trainStationLocation = new SimpleLocation(0,2);
        hotels.put(trainStationLocation,new Place(trainStationLocation, Place.LocationType.hotel,"Dworzec Kolejowy Kraków"));
        busStations = new HashMap<>();
        SimpleLocation busStationLocation = new SimpleLocation(0,3);
        hotels.put(busStationLocation,new Place(busStationLocation, Place.LocationType.hotel,"Dworzec Autobusowy Kraków"));
        airports = new HashMap<>();
        SimpleLocation airportLocation = new SimpleLocation(0,4);
        hotels.put(airportLocation,new Place(airportLocation, Place.LocationType.hotel,"Lotnisko Balice"));
        monuments = new HashMap<>();
        SimpleLocation monumentLocation = new SimpleLocation(0,5);
        hotels.put(monumentLocation,new Place(monumentLocation, Place.LocationType.hotel,"Wawel"));
    }
}
