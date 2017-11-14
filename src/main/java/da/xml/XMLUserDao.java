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
        if (userList == null) {
            return loadList();
        }
        return userList;
    }

    /**
     * Загружает данные из XML файла.
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
