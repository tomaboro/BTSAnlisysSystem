package utils;

import java.util.*;

public abstract class Places {
    public static final List<Place> hotels;
    public static final List<Place> hostels;
    public static final List<Place> trainStations;
    public static final List<Place> busStations;
    public static final List<Place> airports;
    public static final List<Place> monuments;
    public static final List<Place> clubs;

    static {
        hotels = new ArrayList<>();
        SimpleLocation hotelLocation = new SimpleLocation(0,0);
        hotels.add(new Place(hotelLocation, Place.LocationType.hotel,"Hotel Krakowiak"));
        hostels = new ArrayList<>();
        SimpleLocation hostelLocation = new SimpleLocation(0,1);
        hostels.add(new Place(hostelLocation, Place.LocationType.hostel,"Bison Hostel"));
        trainStations = new ArrayList<>();
        SimpleLocation trainStationLocation = new SimpleLocation(0,2);
        trainStations.add(new Place(trainStationLocation, Place.LocationType.trainStation,"Dworzec Kolejowy Kraków"));
        busStations = new ArrayList<>();
        SimpleLocation busStationLocation = new SimpleLocation(0,3);
        busStations.add(new Place(busStationLocation, Place.LocationType.busStation,"Dworzec Autobusowy Kraków"));
        airports = new ArrayList<>();
        SimpleLocation airportLocation = new SimpleLocation(0,4);
        airports.add(new Place(airportLocation, Place.LocationType.airport,"Lotnisko Balice"));
        monuments = new ArrayList<>();
        SimpleLocation monumentLocation = new SimpleLocation(0,5);
        monuments.add(new Place(monumentLocation, Place.LocationType.monument,"Wawel"));
        clubs = new ArrayList<>();
        SimpleLocation clubLocation = new SimpleLocation(0,6);
        clubs.add(new Place(monumentLocation, Place.LocationType.club,"Karlik"));
    }

    // TODO change boundaries when more places will be available
    public static Place RandomEntryPlace() {
        Random random = new Random();
        switch (random.nextInt(3)){
            case 0:
                return trainStations.get(random.nextInt(1));
            case 1:
                return busStations.get(random.nextInt(1));
            case 2:
                return airports.get(random.nextInt(1));
        }
        return null;
    }

    public static Place RandomPlace(){
        Random random = new Random();
        switch (random.nextInt(4)){
            case 0:
                return hotels.get(random.nextInt(1));
            case 1:
                return hostels.get(random.nextInt(1));
            case 2:
                return monuments.get(random.nextInt(1));
            case 3:
                return busStations.get(random.nextInt(1));
            case 4:
                return clubs.get(random.nextInt(1));
        }
        return null;
    }
}
