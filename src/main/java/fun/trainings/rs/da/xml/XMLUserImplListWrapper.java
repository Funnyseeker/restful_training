package fun.trainings.rs.da.xml;

import fun.trainings.rs.model.XMLBindKeys;
import fun.trainings.rs.model.User;
import fun.trainings.rs.model.impl.XMLUserImpl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-обертка для списка объектов типа {@link User}.
 * Нужен для задания уникального корневого тега в xml файле.
 */
@XmlRootElement(name = XMLBindKeys.USERS)
public class XMLUserImplListWrapper {

    @XmlElement(name = XMLBindKeys.USER, type = XMLUserImpl.class)
    private List<XMLUserImpl> userList = null;

    public List<? extends User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = new ArrayList<>();
        for (User user : userList) {
            XMLUserImpl impl = (XMLUserImpl) user;
            if (impl != null) {
                this.userList.add(impl);
            }
        }
    }
}
