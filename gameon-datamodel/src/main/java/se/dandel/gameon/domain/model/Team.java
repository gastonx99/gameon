package se.dandel.gameon.domain.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Entity
@Table(name = "TEAM")
public class Team {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    private String key;

    private String name;

    private String countryCode;

    private String logo;

    private Team() {
        // For JPA
    }

    public static Team of(String key, String name) {
        Team team = new Team();
        team.setKey(key);
        team.setName(name);
        return team;
    }

    public static Map<String, Team> map(Collection<Team> teams) {
        return teams.stream().collect(toMap(team -> team.getKey(), identity()));
    }

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append("pk", pk).append("key", key).append("name", name)
                .toString();
    }
}
