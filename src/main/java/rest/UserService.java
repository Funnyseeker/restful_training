package rest;

import da.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
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
    public Response getUsersByName(@QueryParam("name") String name) {
        return Response.status(Response.Status.OK).entity(userDao.getUsersByName(name)).build();
    }
}
