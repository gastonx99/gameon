package se.dandel.gameon.domain.model;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Table(name = "VENUE")
public class Venue {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    private String name;

    private String capacity;

    private String city;

    private Locale.IsoCountryCode country;

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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Locale.IsoCountryCode getCountry() {
        return country;
    }

    public void setCountry(Locale.IsoCountryCode country) {
        this.country = country;
    }
}
