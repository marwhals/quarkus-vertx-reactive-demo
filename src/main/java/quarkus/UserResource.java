package quarkus;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

@Path("/users")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @GET
    public Uni<List<Users>> get() {
        LOGGER.info("Getting all users.......");
        return Users.listAll(Sort.by("id"));
    }

    @GET
    @Path("/{id}")
    public Uni<Users> getById(Long id) {
        LOGGER.info("Getting user by id: {}", id);
        return Users.findById(id);
    }

    @POST
    public Uni<Response> create(Users user) {
        LOGGER.info("Creating user: {}", user);
        return Panache.<Users>withTransaction(user::persist)
                .onItem().transform(
                        insertedUser -> Response.created(URI.create("/users/" + insertedUser.id)).build()
                );
    }

}
