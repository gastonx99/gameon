package se.dandel.gameon.domain.model;

import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

@Entity
public class Country {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    private String name;

    @Column(name = "COUNTRYCODE")
    private String countryCode;

    private String continent;

    @Embedded
    private RemoteKey remoteKey;

    @Version
    private long version;

    @Embedded
    private Audit audit = new Audit();

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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public RemoteKey getRemoteKey() {
        return remoteKey;
    }

    public void setRemoteKey(RemoteKey remoteKey) {
        this.remoteKey = remoteKey;
    }

    public boolean isContinent() {
        return StringUtils.isBlank(countryCode);
    }

}
