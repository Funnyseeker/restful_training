package fun.trainings.rs.model.impl;

import fun.trainings.rs.annotations.FilterFieldGetter;
import fun.trainings.rs.model.User;
import fun.trainings.rs.model.XMLBindKeys;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс представляющий пользователя.
 * Имплементация интерфейса {@link User}
 */
@XmlRootElement(name = XMLBindKeys.USER)
public class XMLUserImpl implements User {
    private int userId;
    private String userName;
    private String userNickname;
    private String userEMail;
    private String userPassword;

    public XMLUserImpl() {
    }

    public XMLUserImpl(int userId, String userName, String userNickname, String userEMail) {
        this.userId = userId;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userEMail = userEMail;
    }

    @Override
    @FilterFieldGetter(XMLBindKeys.USER_ID)
    public int getId() {
        return userId;
    }

    @Override
    @XmlElement(name = XMLBindKeys.USER_ID, required = true)
    public void setId(int userId) {
        this.userId = userId;
    }

    @Override
    @FilterFieldGetter(XMLBindKeys.USER_NAME)
    public String getUserName() {
        return userName;
    }

    @Override
    @XmlElement(name = XMLBindKeys.USER_NAME)
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    @FilterFieldGetter(XMLBindKeys.USER_NICKNAME)
    public String getUserNickname() {
        return userNickname;
    }

    @Override
    @XmlElement(name = XMLBindKeys.USER_NICKNAME)
    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    @Override
    @FilterFieldGetter(XMLBindKeys.USER_EMAIL)
    public String getUserEMail() {
        return userEMail;
    }

    @Override
    @XmlElement(name = XMLBindKeys.USER_EMAIL)
    public void setUserEMail(String userEMail) {
        this.userEMail = userEMail;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    @XmlElement(name = XMLBindKeys.USER_PASSWORD)
    public void setPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
