package se.dandel.gameon.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;
    private String username;

    private User() {
        super();
    }

    public User(String username) {
        this.username = username;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public String getUsername() {
        return username;
    }
}
