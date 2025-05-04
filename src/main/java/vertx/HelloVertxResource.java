package vertx;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.mutiny.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/vertx")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class HelloVertxResource {

    private static final Logger LOG = LoggerFactory.getLogger(HelloVertxResource.class);

    private final WebClient client;

    @Inject
    public HelloVertxResource(Vertx vertx) {
        this.client = WebClient.create(vertx, new WebClientOptions().setDefaultHost("localhost").setDefaultPort(8081));
    }

    @GET
    public Uni<JsonArray> get() {
        final var item = new JsonArray();
        item.add(new JsonObject().put("id", 1));
        item.add(new JsonObject().put("id", 2));
        return Uni.createFrom().item(item);
    }

    @GET
    @Path("/users")
    public Uni<JsonArray> getFromUsers() {
        return client.get("/users").send()
                .onItem().transform(HttpResponse::bodyAsJsonArray);
    }

}
