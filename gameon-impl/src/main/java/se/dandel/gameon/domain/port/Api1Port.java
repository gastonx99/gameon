package se.dandel.gameon.domain.port;

import se.dandel.gameon.domain.model.Country;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.Tournament;

import java.util.Collection;

public interface Api1Port {
    Collection<Team> fetchTeams(Country country);

    Collection<Tournament> fetchLeagues();

    Collection<Country> fetchCountry();
}
