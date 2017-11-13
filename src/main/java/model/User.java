package model;

import annotation.FilterFieldGetter;

public interface User {
    @FilterFieldGetter("user.id")
    int getId();

    void setId(int userId);

    @FilterFieldGetter("user.name")
    String getUserName();

    void setUserName(String userName);

    @FilterFieldGetter("user.nickname")
    String getNickname();

    void setNickname(String userNickname);

    @FilterFieldGetter("user.email")
    String getUserEMail();

    void setUserEMail(String userEMail);
}
