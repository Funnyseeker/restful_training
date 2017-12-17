package fun.trainings.rs.rest;

import fun.trainings.rs.da.UserDao;
import fun.trainings.rs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;


/**
 * Класс restful сервиса для работы с данными пользователей.
 */
@Path("/UserService")
public class UserService {
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

    /**
     * Получает пользователя по уникальному идентификатору
     *
     * @param userId уникальный идентификатор пользователя
     *
     * @return пользователя с заданным идентификатором
     */
    @GET
    @Path("/user_{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userid") int userId) {
        System.out.println("start getUser()");
        return Response.status(Response.Status.OK).entity(userDao.getUserById(userId)).build();
    }

    /**
     * Ищет пользователей по списку параметров
     *
     * @param nickname {@link MatrixParam} никнэйм пользователя
     * @param name     {@link MatrixParam} имя пользователя
     * @param email    {@link MatrixParam} адрес электронной почты пользователя
     *
     * @return список пользователей с заданными параметрами
     */
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchResult(@MatrixParam("nickname") String nickname, @MatrixParam("name") String name,
                                    @MatrixParam("email") String email) {
        System.out.println("start getSearchResult()");
        return Response.status(Response.Status.OK).entity(userDao.searchUsers(nickname, name, email)).build();
    }

    /**
     * @param nickname
     * @param name
     * @param email
     *
     * @return
     */
    @PUT
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(@MatrixParam("nickname") String nickname, @MatrixParam("name") String name,
                                 @MatrixParam("email") String email, @Context UriInfo uriInfo) {
        System.out.println("start registerUser()");
        if (!StringUtils.hasText(email)) {
            return Response.noContent().build();
        } else {
            User newUser = userDao.registerUser(nickname, name, email);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.replaceMatrix("");
            return Response.created(builder.build(newUser.getId())).entity(newUser).build();
        }
    }
}
