package fun.trainings.rs.da.db;

import fun.trainings.rs.da.UserDao;
import fun.trainings.rs.model.HibernateBindKeys;
import fun.trainings.rs.model.User;
import fun.trainings.rs.model.factories.UserFactory;
import fun.trainings.rs.model.filtering.HQLFilter;
import fun.trainings.rs.model.impl.EntityUserImpl;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * @see UserDao#getUserById(int)
     */
    @Override
    public User getUserById(int userId) {
        Session session = this.sessionFactory.openSession();
        User user = null;
        try {
            Transaction tx = session.beginTransaction();
            HQLFilter<EntityUserImpl> filter = new HQLFilter<>(EntityUserImpl.class,
                                                               sessionFactory.getCriteriaBuilder());
            filter.putFilterAttribute(HibernateBindKeys.USER_ID_COL, userId);
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

    /**
     * @see UserDao#searchUsers(String, String, String)
     */
    @Override
    public List<User> searchUsers(String userName, String userNickname, String userEMail) {
        Session session = this.sessionFactory.openSession();
        List<? extends User> users = null;
        try {
            Transaction tx = session.beginTransaction();
            HQLFilter<EntityUserImpl> filter = new HQLFilter<>(EntityUserImpl.class,
                                                               sessionFactory.getCriteriaBuilder());
            filter.putFilterAttribute(HibernateBindKeys.USER_NAME_COL, userName);
            filter.putFilterAttribute(HibernateBindKeys.USER_NICKNAME_COL, userNickname);
            filter.putFilterAttribute(HibernateBindKeys.USER_EMAIL_COL, userEMail);
            users = session.createQuery(filter.getCriteriaQuery()).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users != null ? users.stream().map(elem -> (User) elem).collect(Collectors.toList())
                : Collections.emptyList();
    }

    /**
     * @see UserDao#registerUser(String, String, String, String)
     */
    @Override
    public User registerUser(String password, String userName, String userNickname, String userEMail) {
        User user = userFactory.createNew();
        userFactory.setUserFields(user, null, password, userName, userNickname, userEMail);
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

    /**
     * @see UserDao#updateUser(int, String, String, String)
     */
    @Override
    public void updateUser(int userId, String userName, String userNickname, String userEMail) {
        Session session = this.sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            HQLFilter<EntityUserImpl> filter = new HQLFilter<>(EntityUserImpl.class,
                                                               sessionFactory.getCriteriaBuilder());
            filter.putFilterAttribute(HibernateBindKeys.USER_ID_COL, userId);
            filter.addSetAttribute(HibernateBindKeys.USER_NAME_COL, userName);
            filter.addSetAttribute(HibernateBindKeys.USER_NICKNAME_COL, userNickname);
            filter.addSetAttribute(HibernateBindKeys.USER_EMAIL_COL, userEMail);
            session.createQuery(filter.getCriteriaUpdate()).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * @see UserDao#deleteUser(int)
     */
    @Override
    public void deleteUser(int userId) {
        Session session = this.sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            HQLFilter<EntityUserImpl> filter = new HQLFilter<>(EntityUserImpl.class,
                                                               sessionFactory.getCriteriaBuilder());
            filter.putFilterAttribute(HibernateBindKeys.USER_ID_COL, userId);
            session.createQuery(filter.getCriteriaDelete()).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
