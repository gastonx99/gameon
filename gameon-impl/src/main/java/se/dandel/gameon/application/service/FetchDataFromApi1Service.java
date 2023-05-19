package se.dandel.gameon.application.service;

import se.dandel.gameon.domain.model.*;
import se.dandel.gameon.domain.port.Api1Port;
import se.dandel.gameon.domain.port.MessageProducerPort;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

public class FetchDataFromApi1Service {

    @Inject
    private Api1Port api1Port;

    @Inject
    private MessageProducerPort messageProducerPort;

    public void fetchTeams(RemoteKey remoteKeyCountry) {
        Collection<Team> teams = api1Port.fetchTeams(remoteKeyCountry);
        teams.forEach(team -> messageProducerPort.produceMessage(team));
    }

    public void fetchLeagues(Optional<RemoteKey> countryRemoteKey) {
        Collection<Tournament> tournaments = api1Port.fetchLeagues(countryRemoteKey);
        tournaments.forEach(tournament -> messageProducerPort.produceMessage(tournament));
    }

    public void fetchSeasons(RemoteKey remoteKeyTournament) {
        Collection<Season> seasons = api1Port.fetchSeasons(remoteKeyTournament);
        seasons.forEach(season -> messageProducerPort.produceMessage(season));
    }

    public void fetchMatches(RemoteKey remoteKeySeason) {
        Collection<Match> matches = api1Port.fetchMatches(remoteKeySeason);
        matches.forEach(match -> messageProducerPort.produceMessage(match));
    }

    public void fetchCountries() {
        Collection<Country> countries = api1Port.fetchCountry();
        countries.forEach(country -> messageProducerPort.produceMessage(country));
    }
}
