package fun.trainings.rs.da.xml;

import fun.trainings.rs.model.BindKeys;
import fun.trainings.rs.model.User;
import fun.trainings.rs.model.impl.UserImpl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-обертка для списка объектов типа {@link User}.
 * Нужен для задания уникального корневого тега в xml файле.
 */
@XmlRootElement(name = BindKeys.USERS)
public class UserImplListWrapper {

    @XmlElement(name = BindKeys.USER, type = UserImpl.class)
    private List<UserImpl> userList = null;

    public List<? extends User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = new ArrayList<>();
        for (User user : userList) {
            UserImpl impl = (UserImpl) user;
            if (impl != null) {
                this.userList.add(impl);
            }
        }
    }
}
