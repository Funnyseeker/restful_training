package fun.trainings.rs.model;

import fun.trainings.rs.annotations.FilterFieldGetter;

/**
 * Интерфейс для данных пользователя.
 * Предоставляет набор "getter"-ов и "setter"-ов.
 */
public interface User {
    int getId();

    void setId(int userId);

    @FilterFieldGetter(XMLBindKeys.USER_NAME)
    String getUserName();

    void setUserName(String userName);

    @FilterFieldGetter(XMLBindKeys.USER_NICKNAME)
    String getUserNickname();

    void setUserNickname(String userNickname);

    @FilterFieldGetter(XMLBindKeys.USER_EMAIL)
    String getUserEMail();

    void setUserEMail(String userEMail);
}
