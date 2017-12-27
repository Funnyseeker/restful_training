package fun.trainings.rs.da;

import fun.trainings.rs.model.User;

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
     * @param userName     имя пользователя
     * @param userNickname никнэйм пользователя
     * @param userEMail    адрес электронной почты пользователя
     *
     * @return список пользователей найденных по заданным параметрам
     */
    List<User> searchUsers(String userName, String userNickname, String userEMail);

    /**
     * Регистрация нового пользователя
     *
     * @param password     хэш пароля пользователя
     * @param userName     имя пользователя
     * @param userNickname никнэйм пользователя
     * @param userEMail    адрес электронной почты пользователя
     *
     * @return зарегистрированный пользователь
     */
    User registerUser(String password, String userName, String userNickname, String userEMail);

    /**
     * Обновляет данные пользователя с указанным уникальным идентификатором
     *
     * @param userId       уникальный идентификатор пользователя
     * @param userName     имя пользователя
     * @param userNickname никнэйм пользователя
     * @param userEMail    адрес электронной почты пользователя
     */
    void updateUser(int userId, String userName, String userNickname, String userEMail);

    /**
     * Удаляет пользователя с заданным уникальным идентификатором
     *
     * @param userId уникальный идентификатор пользователя
     */
    void deleteUser(int userId);
}
