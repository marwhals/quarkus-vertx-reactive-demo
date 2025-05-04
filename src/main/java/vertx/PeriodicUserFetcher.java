package vertx;

import io.smallrye.mutiny.Uni;

import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.logging.Logger;

@ApplicationScoped
public class PeriodicUserFetcher extends AbstractVerticle {

    private static final Logger LOGGER = Logger.getLogger(PeriodicUserFetcher.class.getName());

    @Override
    public Uni<Void> asyncStart() {
        vertx.periodicStream(Duration.ofSeconds(5).toMillis())
                .toMulti()
                .subscribe()
                .with(item -> {
                    LOGGER.info("Periodic fetcher triggered");
                });

        return Uni.createFrom().voidItem();
    }


}
