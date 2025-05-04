package vertx;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.logging.Logger;

@ApplicationScoped
public class PeriodicUserFetcher extends AbstractVerticle {

    static final String ADDRESS = PeriodicUserFetcher.class.getName();
    private static final Logger LOGGER = Logger.getLogger(PeriodicUserFetcher.class.getName());

    @Override
    public Uni<Void> asyncStart() {
        var client = WebClient.create(vertx,
                new WebClientOptions().setDefaultHost("localhost").setDefaultPort(8080));

        vertx.periodicStream(Duration.ofSeconds(5).toMillis())
                .toMulti()
                .subscribe()
                .with(item -> {
                    LOGGER.info("Fetch all users....");
                    client.get("/users").send().subscribe().with(result -> {
                        var body = result.bodyAsJsonArray();
                        LOGGER.info("Got users: {}" + body);
                        vertx.eventBus().publish(ADDRESS, body);
                    });
                });

        return Uni.createFrom().voidItem();
    }
}
