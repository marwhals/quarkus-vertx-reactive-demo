package vertx;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static vertx.PeriodicUserFetcher.ADDRESS;

@ApplicationScoped
public class EventBusConsumer extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBusConsumer.class.getName());

    @Override
    public Uni<Void> asyncStart() {
        vertx.eventBus().<JsonArray>consumer(ADDRESS, message -> {
            LOGGER.info("Consumed from Event Bus: {}", message.body());
        });
        return Uni.createFrom().voidItem();
    }

}