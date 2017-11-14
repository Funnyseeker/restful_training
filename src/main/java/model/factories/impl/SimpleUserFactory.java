package model.factories.impl;

import model.User;
import model.factories.UserFactory;
import model.impl.UserImpl;
import org.springframework.util.StringUtils;

/**
 * Имплементация интерфейса {@link UserFactory}
 */
public class SimpleUserFactory implements UserFactory {

    /**
     * @see UserFactory#createNew()
     */
    @Override
    public User createNew() {
        return new UserImpl();
    }

    /**
     * @see UserFactory#setUserFields(User, Integer, String, String, String)
     */
    @Override
    public void setUserFields(User user, Integer userId, String userName, String userNickname, String userEMail) {
        if (userId != null) {
            user.setId(userId);
        }
        if (StringUtils.hasText(userName)) {
            user.setUserName(userName);
        }
        if (StringUtils.hasText(userNickname)) {
            user.setUserNickname(userNickname);
        }
        if (StringUtils.hasText(userEMail)) {
            user.setUserEMail(userEMail);
        }
    }
}
