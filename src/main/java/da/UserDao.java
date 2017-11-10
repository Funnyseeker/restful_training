package da;

import model.User;
import model.filtering.Filter;

import java.util.List;

public interface UserDao {

    User getUserById(int id);

    List<User> getUsersByName(String name);
}
