package com.motek.btsAnalisys.api;

import akka.actor.ActorRef;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import utils.Questionary;
import com.motek.btsAnalisys.actors.questionary.commands.GetAll;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Endpoint extends AllDirectives {
    private ActorRef questionary;

    public Endpoint(ActorRef questionary) {
        super();
        this.questionary = questionary;
    }

    public Route createRoute() {
        return route(
                path("hello", () ->
                        get(() ->
                                complete("<h1>Say hello to akka-http</h1>"))),
                path("questionaries", () ->
                        get(() -> {
                            final Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
                            final Future<Object> future = Patterns.ask(questionary, new GetAll(), timeout);
                            try {
                                List<Questionary> questionary = (List<Questionary>) Await.result(future, timeout.duration());
                                return complete(questionary.toString());
                            } catch(Exception e) {
                                return complete("failed");
                            }})));
    }
}
