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
        if (userList.size() != 0) {
            userList.sort((u1, u2) -> Integer.compare(u1.getId(), u2.getId()));
            newUserId = userList.get(0).getId() + 1;
        }

        userFactory.setUserFields(user, newUserId, userName, userNickname, userEMail);
        return user;
    }

    /**
     * Загружает данных из XML файла.
     *
     * @return userList загруженный из XML файла
     */
    private List<User> loadList() {
        List<User> userList = new ArrayList<>();

        //Создаем поток для чтения из файла
        try (FileInputStream stream = new FileInputStream("usersxml/userlist.xml")) {
            // Создаем JAXBContext, который создаст "unmarshaller" для чтения данных из файла
            JAXBContext context = JAXBContext.newInstance(User.class);

            userList = (List<User>) context.createUnmarshaller().unmarshal(stream);

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * Сохраняет userList в XML файл.
     */
    private void saveList() {
        //Создаем поток для записи в файл
        try (FileOutputStream stream = new FileOutputStream("usersxml/userlist.xml")) {
            for (User user : userList) {
                // Создаем JAXBContext, который создаст "marshaller" для записи данных в поток
                JAXBContext context = JAXBContext.newInstance(User.class);

                // Создаем JAXBElement
                // Подаем в него объект типа User
                JAXBElement jaxbElement = new JAXBElement(new QName(BindKeys.USER), User.class, user);

                // Маршалим jaxbElement содержащий информацию по данному "user"-у
                context.createMarshaller().marshal(jaxbElement, stream);
            }
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
