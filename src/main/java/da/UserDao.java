package da;

import model.User;

import java.util.List;

public interface UserDao {

    User getUserById(int id);

    List<User> searchUsers(String nickname, String name, String userEMail);
}
