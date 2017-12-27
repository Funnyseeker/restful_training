package fun.trainings.rs.model.factories.impl;

import fun.trainings.rs.model.User;
import fun.trainings.rs.model.factories.UserFactory;
import fun.trainings.rs.model.impl.XMLUserImpl;
import org.springframework.util.StringUtils;

/**
 * Имплементация интерфейса {@link UserFactory}
 */
public class XMLUserFactory implements UserFactory {

    /**
     * @see UserFactory#createNew()
     */
    @Override
    public User createNew() {
        return new XMLUserImpl();
    }

    /**
     * @see UserFactory#setUserFields(User, Integer, String, String, String, String)
     */
    @Override
    public void setUserFields(User user, Integer userId, String password, String userName, String userNickname,
                              String userEMail) {
        if (userId != null) {
            user.setId(userId);
        }
        if (StringUtils.hasText(password)) {
            user.setPassword(password);
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
