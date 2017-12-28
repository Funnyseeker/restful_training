package fun.trainings.rs;

import fun.trainings.rs.rest.UserService;
import fun.trainings.rs.rest.auth.AuthFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class Main extends ResourceConfig {

    public Main() {
        register(RequestContextFilter.class);
        register(AuthFilter.class);
        register(UserService.class);
        register(JacksonFeature.class);
    }

    public static void main(String[] args) {
    }
}
