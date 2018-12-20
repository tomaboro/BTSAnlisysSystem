package com.motek.btsAnalisys.actors.questionary;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.actors.questionary.commands.GetAll;
import com.motek.btsAnalisys.actors.questionary.commands.PrepareQuestionary;
import utils.Place;
import utils.ProcessedEvent;
import utils.Questionary;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QuestionaryActor extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private List<Questionary> questionaries = new ArrayList<>();

    @Override
    public void preStart() { }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(GetAll.class, get -> {
                    getSender().tell(questionaries,getSelf());
                })
                .match(PrepareQuestionary.class, request -> {
                    List<Place> places = request.getEvents().stream().map(ProcessedEvent::getPlace).collect(Collectors.toList());
                    Questionary questionary = new Questionary(isTourist(places),!isTourist(places),findArrivalType(places),isParyting(request.getEvents()));
                    questionaries.add(questionary);
                    log.info("Q, " + "[Processed Events List], " + questionary.getIsTourist() + ";");
                    log.info("Q, " + "[Processed Events List], " +  questionary.getIsTourist() + ", " + questionary.getArrivalType() + ";");
                    log.info("Q, " + "[Processed Events List], " + questionary.getIsTourist() + ", " + questionary.getArrivalType() + ", " + questionary.getWasPartying() + ";");
                    log.error(String.valueOf(System.currentTimeMillis()));
                })
                .build();
    }

    private Questionary.TransportType findArrivalType(List<Place> eventList) {
        return eventList.stream().filter(place -> {
            Place.LocationType type = place.getType();
            return type == Place.LocationType.airport || type == Place.LocationType.busStation || type == Place.LocationType.trainStation;
        }).findFirst().map(locationType -> {
            switch (locationType.getType()) {
                case airport:
                    return Questionary.TransportType.plane;
                case busStation:
                    return Questionary.TransportType.bus;
                default:
                    return Questionary.TransportType.train;
            }
        }).orElse(Questionary.TransportType.car);
    }

    private Boolean isTourist(List<Place> eventList) {
        return eventList.stream().filter(place -> {
            Place.LocationType type = place.getType();
            return type == Place.LocationType.monument;
        }).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).size() > 5;
    }

    private Boolean isParyting(List<ProcessedEvent> eventList) {
        return eventList.stream().filter(event ->
            event.getTime().getHour() > 0 && event.getTime().getHour() < 4 && event.getPlace().getType() == Place.LocationType.club
        ).count() > 40;
    }

    public static Props props() {
        return Props.create(QuestionaryActor.class);
    }
}
