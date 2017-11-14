package model;

import annotation.FilterFieldGetter;

public interface User {
    @FilterFieldGetter(BindKeys.USER_ID)
    int getId();

    void setId(int userId);

    @FilterFieldGetter(BindKeys.USER_NAME)
    String getUserName();

    void setUserName(String userName);

    @FilterFieldGetter(BindKeys.USER_NICKNAME)
    String getNickname();

    void setNickname(String userNickname);

    @FilterFieldGetter(BindKeys.USER_EMAIL)
    String getUserEMail();

    void setUserEMail(String userEMail);
}
