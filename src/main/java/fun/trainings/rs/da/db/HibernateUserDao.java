package fun.trainings.rs.da.db;

import fun.trainings.rs.da.UserDao;
import fun.trainings.rs.model.BindKeys;
import fun.trainings.rs.model.User;
import fun.trainings.rs.model.factories.UserFactory;
import fun.trainings.rs.model.filtering.HQLFilter;
import fun.trainings.rs.model.impl.EntityUserImpl;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HibernateUserDao implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserFactory userFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setUserFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public User getUserById(int userId) {
        Session session = this.sessionFactory.openSession();
        User user = null;
        try {
            Transaction tx = session.beginTransaction();
            HQLFilter<EntityUserImpl> filter = new HQLFilter(EntityUserImpl.class, sessionFactory.getCriteriaBuilder());
            filter.putFilterAttribute(BindKeys.USER_ID, userId);
//            user = session.createQuery("from EntityUserImpl users where users.id = :userId", EntityUserImpl.class)
//                    .setParameter("userId", userId).getSingleResult();
            user = session.createQuery(filter.getCriteriaQuery()).getSingleResult();
            tx.commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public List<User> searchUsers(String userName, String userNickname, String userEMail) {
        return null;
    }

    @Override
    public User registerUser(String userName, String userNickname, String userEMail) {
        User user = userFactory.createNew();
        userFactory.setUserFields(user, null, userName, userNickname, userEMail);
        Session session = this.sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public void updateUser(int userId, String userName, String userNickname, String userEMail) {
//        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
//        builder.createCriteriaUpdate(EntityUserImpl.class).where()
    }

    @Override
    public void deleteUser(int userId) {

    }
}
