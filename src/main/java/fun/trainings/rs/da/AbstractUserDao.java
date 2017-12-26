package fun.trainings.rs.da;

import fun.trainings.rs.model.User;
import fun.trainings.rs.model.factories.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractUserDao implements UserDao {

    @Autowired
    private UserFactory userFactory;

    public void setUserFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public List<User> searchUsers(String userName, String userNickname, String userEMail) {
        return null;
    }

    @Override
    public User registerUser(String userName, String userNickname, String userEMail) {
        return null;
    }

    @Override
    public void updateUser(int userId, String userName, String userNickname, String userEMail) {

    }

    @Override
    public void deleteUser(int userId) {

    }
}
