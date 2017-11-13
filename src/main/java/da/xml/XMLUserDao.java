package da.xml;

import da.UserDao;
import model.User;
import model.filtering.StreamFilter;

import java.util.ArrayList;
import java.util.List;

public class XMLUserDao implements UserDao {
    private List<User> userList;

    @Override
    public User getUserById(int id) {
        StreamFilter<User> filter = new StreamFilter<>();
        return null;
    }

    @Override
    public List<User> searchUsers(String nickname, String name, String userEMail) {
        return null;
    }

    public List<User> getUserList() {
        if (userList == null){
            return userList;
        }
        return userList;
    }

    /*
     *
     */
    public List<User> loadList() {
        List<User> userList = new ArrayList<>();
        return userList;
    }

    /*
     * Put userList into XML file.
     */
    public void saveList(){

    }
}
