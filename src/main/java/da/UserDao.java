package da;

import model.User;

import java.util.List;

public interface UserDao {

    /**
     * Получает пользователя по уникальному идентификатору
     *
     * @param id уникальный идентификатор пользователя
     *
     * @return пользователя с заданным уникальным идентификатором
     */
    User getUserById(int id);

    /**
     * Ищет пользователей по списку параметров
     *
     * @param nickname  никнэйм пользователя
     * @param name      имя пользователя
     * @param userEMail адрес электронной почты пользователя
     *
     * @return список пользователей найденных по заданным параметрам
     */
    List<User> searchUsers(String nickname, String name, String userEMail);

}
