package com.motek.btsAnalisys;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.util.Timeout;
import com.motek.btsAnalisys.actors.manager.ManagingActor;
import com.motek.btsAnalisys.actors.manager.commands.GetInfo;
import com.motek.btsAnalisys.api.Endpoint;
import com.motek.btsAnalisys.kafka.KafkaEventConsumer;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class Application {

    public void start(){
        ActorSystem system = ActorSystem.create("BTS-data-analisys-system");
        ActorRef managingAgent = system.actorOf(ManagingActor.props(),ManagingActor.managingAgentID);

        KafkaEventConsumer kafkaEventConsumer = new KafkaEventConsumer(managingAgent);
        try {
            kafkaEventConsumer.runEventConsumer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
        final Future<Object> future = Patterns.ask(managingAgent, new GetInfo(), timeout);
        try {
            ActorRef questionary = (ActorRef) Await.result(future, timeout.duration());
            final Http http = Http.get(system);
            final ActorMaterializer materializer = ActorMaterializer.create(system);

            final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = new Endpoint(questionary).createRoute().flow(system, materializer);
            final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                    ConnectHttp.toHost("localhost", 8080), materializer);

            System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
            try {
                System.in.read(); // let it run until user presses return
            } catch (IOException e) {
                e.printStackTrace();
            }

            binding
                    .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                    .thenAccept(unbound -> system.terminate()); // and shutdown when done
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
