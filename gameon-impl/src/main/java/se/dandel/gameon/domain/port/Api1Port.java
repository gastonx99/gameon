package se.dandel.gameon.domain.port;

import se.dandel.gameon.domain.model.*;

import java.util.Collection;
import java.util.Optional;

public interface Api1Port {
    Collection<Team> fetchTeams(RemoteKey remoteKeyCountry);

    Collection<Tournament> fetchLeagues(Optional<RemoteKey> remoteKeyCountry);

    Collection<Country> fetchCountry();

    Collection<Season> fetchSeasons(RemoteKey remoteKeyTournament);

    Collection<Match> fetchMatches(RemoteKey remoteKeySeason);
}
