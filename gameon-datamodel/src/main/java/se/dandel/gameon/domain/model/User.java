package se.dandel.gameon.domain.model;

import javax.persistence.*;

@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    private String username;

    @Version
    private long version;

    @Embedded
    private Audit audit = new Audit();

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
