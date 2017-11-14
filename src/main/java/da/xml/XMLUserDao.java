package da.xml;

import da.UserDao;
import model.BindKeys;
import model.User;
import model.filtering.StreamFilter;

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
 * Класс реализующий {@link UserDao} интерфейс.
 * Принцип хранения данных основан на загрузке\выгрузке в файл XML формата.
 */
public class XMLUserDao implements UserDao {
    /**
     * Текущие данные
     */
    private List<User> userList;

    public XMLUserDao() {
        userList = loadList();
    }

    /**
     * @see UserDao#getUserById(int)
     */
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
