package se.dandel.gameon.domain.port;

import se.dandel.gameon.domain.model.*;

import java.util.Collection;
import java.util.Optional;

public interface Api1Port {
    Collection<Team> fetchTeams(Country country);

    Collection<Tournament> fetchLeagues(Optional<Country> country);

    Collection<Country> fetchCountry();

    Collection<Season> fetchSeasons(Tournament tournament);

    Collection<Match> fetchMatches(Season season);
}
