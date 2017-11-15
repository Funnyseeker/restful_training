package fun.trainings.rs.model.impl;

import fun.trainings.rs.model.BindKeys;
import fun.trainings.rs.model.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс представляющий пользователя.
 * Имплементация интерфейса {@link User}
 */
@XmlRootElement(name = BindKeys.USER)
public class UserImpl implements User {
    private int userId;
    private String userName;
    private String userNickname;
    private String userEMail;

    public UserImpl() {
    }

    public UserImpl(int userId, String userName, String userNickname, String userEMail) {
        this.userId = userId;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userEMail = userEMail;
    }

    @Override
    public int getId() {
        return userId;
    }

    @Override
    @XmlElement(name = BindKeys.USER_ID, required = true)
    public void setId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    @XmlElement(name = BindKeys.USER_NAME)
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    @XmlElement(name = BindKeys.USER_NICKNAME)
    public String getUserNickname() {
        return userNickname;
    }

    @Override
    @XmlElement(name = BindKeys.USER_EMAIL)
    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    @Override
    public String getUserEMail() {
        return userEMail;
    }

    @Override
    public void setUserEMail(String userEMail) {
        this.userEMail = userEMail;
    }
}
