package fun.trainings.rs.model.factories;

import fun.trainings.rs.da.xml.XMLUserDao;
import fun.trainings.rs.model.User;

/**
 * Фабрика для класса пользователя.
 */
public interface UserFactory {
    int fisrtId = 1;

    /**
     * Создает новый экземпляр пользователя.
     *
     * @return новый(без каких либо данных) экземпляр пользователя.
     */
    User createNew();

    /**
     * Задает поля пользователя.
     * Принцип работы метода: указать значения полей, которые нужно "посетить"(для остальных указать "null").
     * Пример использования: {@link XMLUserDao#registerUser(String, String, String, String)}
     *
     * @param user         ссылка на пользователя, в которого будут записываться значения
     * @param userId       уникальный идентификатор, если не нужно задавать, то "null"
     * @param password     хэш пароля пользователя
     * @param userName     имя пользователя, если не нужно задавать, то "null"
     * @param userNickname никнэйм пользователя, если не нужно задавать, то "null"
     * @param userEMail    адрес электронной почты пользователя, если не нужно задавать, то "null"
     */
    void setUserFields(User user, Integer userId, String password, String userName, String userNickname,
                       String userEMail);
}
