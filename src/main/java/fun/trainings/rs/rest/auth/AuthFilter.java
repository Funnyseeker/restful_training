package fun.trainings.rs.rest.auth;

import fun.trainings.rs.annotations.Secured;
import fun.trainings.rs.da.UserDao;
import fun.trainings.rs.model.User;
import fun.trainings.rs.utils.PasswordUtil;
import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.StringTokenizer;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Basic";
    @Autowired
    private UserDao userDao;

    /**
     * Setter для DAO объекта пользователей
     *
     * @param userDao DAO объект пользователей
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (isValidBasedAuthentication(authorizationHeader)) {
            String authToken = authorizationHeader.replaceFirst(AUTHENTICATION_SCHEME, "").trim();
            String decodedString = Base64.decodeAsString(authToken);
            StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
            String userId = tokenizer.nextToken();
            String password = tokenizer.nextToken();

            if (requestContext.getUriInfo().getPath().contains(userId)) {

                User user = userDao.getUserById(Integer.parseInt(userId));


                if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
                    return;
                }
            }
        }
        abortWithUnauthorized(requestContext);
    }

    private boolean isValidBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .build());
    }
}
