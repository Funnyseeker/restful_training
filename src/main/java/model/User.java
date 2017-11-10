package model;

public interface User {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    UserInfo getUserInfo();

    void setUserInfo(UserInfo userInfo);
}
