package se.dandel.gameon.domain.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIXTURE")
public class Fixture {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;
    private String name;
    @Enumerated(EnumType.STRING)
    private TournamentType type;

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
