package org.acme;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class MessagingService {

    @Incoming("question-in")
    @Outgoing("answer-out")
    @Blocking
    public String consume(String in) throws InterruptedException {
        return in + ": response";
    }
}
