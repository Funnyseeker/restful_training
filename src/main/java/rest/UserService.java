package rest;

import da.UserDao;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Класс restful сервиса для работы с данными пользователя\пользователей.
 */
@Path("/UserService")
public class UserService {

    @Autowired
    UserDao userDao;

    /**
     * Получает пользователя по уникальному идентификатору
     *
     * @param userId уникальный идентификатор пользователя
     *
     * @return пользователя с заданным идентификатором
     */
    @GET
    @Path("/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userid") int userId) {
        return Response.status(Response.Status.OK).entity(userDao.getUserById(userId)).build();
    }

    /**
     * Ищет пользователей по списку параметров
     *
     * @param nickname {@link FormParam} никнэйм пользователя
     * @param name     {@link FormParam} имя пользователя
     * @param email    {@link FormParam} адрес электронной почты пользователя
     *
     * @return список пользователей с заданными параметрами
     */
    @GET
    @Path("/usersearch")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchResult(@FormParam("nickname") String nickname, @FormParam("name") String name,
                                    @FormParam("email") String email) {
        return Response.status(Response.Status.OK).entity(userDao.searchUsers(nickname, name, email)).build();
    }

    @PUT
    @Path("/reguser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(@FormParam("nickname") String nickname, @FormParam("name") String name,
                                 @FormParam("email") String email) {
        if (!StringUtils.hasText(email)) {
            return Response.noContent().build();
        } else {
            User newUser = userDao.registerUser(nickname, name, email);
            try {
                return Response.created(new URI("UserService/" + newUser.getId())).entity(newUser).build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return Response.noContent().build();
            }
        }
    }
}
