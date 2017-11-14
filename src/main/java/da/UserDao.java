package da;

import model.User;

import java.util.List;

/**
 * Интерфейс для DAO объекта, осуществляющего работу с информацией о пользователях.
 */
public interface UserDao {

    /**
     * Получает пользователя по уникальному идентификатору
     *
     * @param userId уникальный идентификатор пользователя
     *
     * @return пользователя с заданным уникальным идентификатором
     */
    User getUserById(int userId);

    /**
     * Ищет пользователей по списку параметров
     *
     * @param userName      имя пользователя
     * @param userNickname  никнэйм пользователя
     * @param userEMail адрес электронной почты пользователя
     *
     * @return список пользователей найденных по заданным параметрам
     */
    List<User> searchUsers(String userName, String userNickname, String userEMail);

}
