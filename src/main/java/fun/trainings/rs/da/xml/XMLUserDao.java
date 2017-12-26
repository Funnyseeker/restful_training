package fun.trainings.rs.da.xml;

import fun.trainings.rs.da.UserDao;
import fun.trainings.rs.model.BindKeys;
import fun.trainings.rs.model.User;
import fun.trainings.rs.model.factories.UserFactory;
import fun.trainings.rs.model.filtering.StreamFilter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton-класс, реализующий {@link UserDao} интерфейс.
 * Принцип хранения данных основан на загрузке\выгрузке в файл XML формата.
 */
public class XMLUserDao implements UserDao {

    private UserFactory userFactory;
    /**
     * Текущие данные
     */
    private List<User> userList = null;

    public List<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
        }
        return userList;
    }

    /**
     * @see UserDao#getUserById(int)
     */

    public void setUserFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public User getUserById(int userId) {
        StreamFilter<User> filter = new StreamFilter<>();
        filter.putFilterAttribute(BindKeys.USER_ID, userId);
        return userList.stream().filter(filter.getPredicate()).findFirst().get();
    }

    /**
     * @see UserDao#searchUsers(String, String, String)
     */
    @Override
    public List<User> searchUsers(String userName, String userNickname, String userEMail) {
        StreamFilter<User> filter = new StreamFilter<>();
        filter.putFilterAttribute(BindKeys.USER_NAME, userName);
        filter.putFilterAttribute(BindKeys.USER_NICKNAME, userNickname);
        filter.putFilterAttribute(BindKeys.USER_EMAIL, userEMail);
        return userList.stream().filter(filter.getPredicate()).collect(Collectors.toList());
    }

    /**
     * @see UserDao#registerUser(String, String, String)
     */
    @Override
    public User registerUser(String userName, String userNickname, String userEMail) {
        User user = userFactory.createNew();
        int newUserId = UserFactory.fisrtId;
        if (getUserList().size() != 0) {
            userList.sort((u1, u2) -> Integer.compare(u1.getId(), u2.getId()));
            newUserId = userList.get(userList.size() - 1).getId() + 1;
        }

        userFactory.setUserFields(user, newUserId, userName, userNickname, userEMail);
        getUserList().add(user);
        saveList();
        return user;
    }

    /**
     * @see UserDao#updateUser(int, String, String, String)
     */
    @Override
    public void updateUser(int userId, String userName, String userNickname, String userEMail) {
        User user = getUserById(userId);
        userFactory.setUserFields(user, userId, userName, userNickname, userEMail);
        userList.replaceAll(userInList -> {
            if (userInList.getId() == userId) {
                return user;
            }
            return userInList;
        });
        saveList();
    }

    /**
     * @see UserDao#deleteUser(int)
     */
    @Override
    public void deleteUser(int userId) {
        getUserList().remove(getUserById(userId));
        saveList();
    }

    /**
     * Загружает данных из XML файла.
     *
     * @return userList загруженный из XML файла
     */
    private void loadList() {
        List<User> userList = new ArrayList<>();

        //Создаем поток для чтения из файла
        URL url = getClass().getClassLoader().getResource("usersxml/userlist.xml");
        try (FileInputStream stream = new FileInputStream(url.getFile())) {
            // Создаем JAXBContext, который создаст "unmarshaller" для чтения данных из файла
            JAXBContext context = JAXBContext.newInstance(XMLUserImplListWrapper.class);

            userList = (List<User>) ((XMLUserImplListWrapper) context.createUnmarshaller().unmarshal(stream))
                    .getUserList();

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        this.userList = userList;
    }

    /**
     * Сохраняет userList в XML файл.
     */
    private void saveList() {
        //Создаем поток для записи в файл
        URL url = getClass().getClassLoader().getResource("usersxml/userlist.xml");
        try (FileOutputStream stream = new FileOutputStream(url.getFile())) {
            // Создаем JAXBContext, который создаст "marshaller" для записи данных в поток
            JAXBContext context = JAXBContext.newInstance(XMLUserImplListWrapper.class);

            XMLUserImplListWrapper wrapper = new XMLUserImplListWrapper();
            wrapper.setUserList(userList);

            // Создаем JAXBElement
            // Подаем в него враппер для списка объектов типа User
            JAXBElement jaxbElement = new JAXBElement(new QName(BindKeys.USERS), XMLUserImplListWrapper.class,
                                                      wrapper);

            // Маршалим jaxbElement содержащий информацию по данному "user"-у
            context.createMarshaller().marshal(jaxbElement, stream);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
