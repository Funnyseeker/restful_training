package fun.trainings.rs.model.impl;

import fun.trainings.rs.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"USERS\"", schema = "rest")
public class EntityUserImpl implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(name = "\"ID\"", updatable = false, nullable = false)
    private int userId;

    @Column(name = "\"USERNAME\"")
    private String userName;

    @Column(name = "\"USERNICKNAME\"")
    private String userNickname;

    @Column(name = "\"USEREMAIL\"")
    private String userEMail;

    @Override
    public int getId() {
        return userId;
    }

    @Override
    public void setId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserNickname() {
        return userNickname;
    }

    @Override
    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    @Override
    public String getUserEMail() {
        return userEMail;
    }

    @Override
    public void setUserEMail(String userEMail) {
        this.userEMail = userEMail;
    }
}
