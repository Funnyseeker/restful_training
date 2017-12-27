package fun.trainings.rs.model.factories.impl;

import fun.trainings.rs.model.User;
import fun.trainings.rs.model.factories.UserFactory;
import fun.trainings.rs.model.impl.EntityUserImpl;
import org.springframework.util.StringUtils;

public class EntityUserFactory implements UserFactory {
    @Override
    public User createNew() {
        return new EntityUserImpl();
    }

    @Override
    public void setUserFields(User user, Integer userId, String password, String userName, String userNickname,
                              String userEMail) {
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
