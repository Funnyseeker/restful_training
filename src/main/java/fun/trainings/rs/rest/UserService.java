package fun.trainings.rs.rest;

import fun.trainings.rs.da.UserDao;
import fun.trainings.rs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
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
        return Response.status(Response.Status.OK).entity(userDao.getUserById(userId)).build();
    }

    /**
     * Ищет пользователей по списку параметров
     *
     * @param name     {@link MatrixParam} имя пользователя
     * @param nickname {@link MatrixParam} никнэйм пользователя
     * @param email    {@link MatrixParam} адрес электронной почты пользователя
     *
     * @return список пользователей с заданными параметрами
     */
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchResult(@MatrixParam("name") String name, @MatrixParam("nickname") String nickname,
                                    @MatrixParam("email") String email) {
        return Response.status(Response.Status.OK).entity(userDao.searchUsers(name, nickname, email)).build();
    }

    /**
     * Регистрирует нового пользователя
     *
     * @param name     {@link MatrixParam} имя пользователя
     * @param nickname {@link MatrixParam} никнэйм пользователя
     * @param email    {@link MatrixParam} адрес электронной почты пользователя
     * @param uriInfo  {@link Context} информация о запросе
     *
     * @return ссылку на зарегестрированного пользователя
     */
    @PUT
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(@MatrixParam("name") String name, @MatrixParam("nickname") String nickname,
                                 @MatrixParam("email") String email, @Context UriInfo uriInfo) {
        if (!StringUtils.hasText(email)) {
            return Response.noContent().build();
        } else {
            User newUser = userDao.registerUser(name, nickname, email);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            String newPath = uriInfo.getPath().replace("/register", "_" + newUser.getId());
            builder = builder.replacePath(newPath);
            builder = builder.replaceMatrix("");
            return Response.created(builder.build()).build();
        }
    }

    /**
     * Обновляет данные пользователя с указанным уникальным идентификатором
     *
     * @param userId
     * @param name     {@link MatrixParam} имя пользователя
     * @param nickname {@link MatrixParam} никнэйм пользователя
     * @param email    {@link MatrixParam} адрес электронной почты пользователя
     *
     * @return положительный ответ с обновленным пользователем
     */
    @POST
    @Path("/user_{userid}/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(
            @PathParam("userid") int userId, @MatrixParam("name") String name, @MatrixParam("nickname") String nickname,
            @MatrixParam("email") String email) {
        userDao.updateUser(userId, name, nickname, email);
        return Response.ok().entity(userDao.getUserById(userId)).build();
    }

    @DELETE
    @Path("/user_{userid}/delete")
    public Response deleteUser(@PathParam("userid") int userId) {
        userDao.deleteUser(userId);
        return Response.ok().build();
    }
}
