package se.dandel.gameon.domain.port;

import se.dandel.gameon.domain.model.*;

import java.util.Collection;

public interface Api1Port {
    Collection<Team> fetchTeams(Country country);

    Collection<Tournament> fetchLeagues(Country country);

    Collection<Country> fetchCountry();

    Collection<Season> fetchSeasons(Tournament tournament);

    Collection<Match> fetchMatches(Season season);
}
