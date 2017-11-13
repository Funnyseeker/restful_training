package rest;

import da.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/UserService")
public class UserService {

    @Autowired
    UserDao userDao;

    @GET
    @Path("/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userid") int userId) {
        return Response.status(Response.Status.OK).entity(userDao.getUserById(userId)).build();
    }

    @GET
    @Path("/usersearch")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchResult(@QueryParam("nickname") String nickname, @QueryParam("name") String name,
                                   @QueryParam("email") String email) {
        return Response.status(Response.Status.OK).entity(userDao.searchUsers(nickname, name, email)).build();
    }
}
