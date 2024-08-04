package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.TimeoutException;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.kafka.reply.KafkaRequestReply;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestStreamElementType;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Path("/")
@ApplicationScoped
public class GreetingResource {

    @Inject
    @Channel("question-out")
    KafkaRequestReply<String, String> reply;
    @Inject
    @Channel("question-out-2")
    MutinyEmitter<String> emitter;
    @Inject
    @Channel("answer-in")
    Multi<String> answers;


    @GET
    @Path("/question")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> question() {
        return reply.request("Hello").ifNoItem()
                    .after(Duration.ofSeconds(5))
                    .recoverWithItem("NO ITEM");
    }

    @GET
    @Path("/question-2")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Void> question2() throws ExecutionException, InterruptedException {
        return emitter.send("Hi").ifNoItem().after(Duration.ofSeconds(4)).failWith(new TimeoutException());
    }

    @GET
    @Path("/answers")
    @RestStreamElementType(MediaType.TEXT_PLAIN)
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> answers() {
        return answers;
    }

}
