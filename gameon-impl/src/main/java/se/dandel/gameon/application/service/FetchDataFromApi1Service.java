package se.dandel.gameon.application.service;

import se.dandel.gameon.domain.GameonRuntimeException;
import se.dandel.gameon.domain.model.*;
import se.dandel.gameon.domain.port.Api1Port;
import se.dandel.gameon.domain.repository.CountryRepository;
import se.dandel.gameon.domain.repository.TeamRepository;
import se.dandel.gameon.domain.repository.TournamentRepository;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

public class FetchDataFromApi1Service {

    @Inject
    private Api1Port api1Port;

    @Inject
    private TeamRepository teamRepository;

    @Inject
    private TournamentRepository tournamentRepository;

    @Inject
    private CountryRepository countryRepository;

    public void fetchAndSaveTeams(RemoteKey countryRemoteKey) {
        Optional<Country> country = countryRepository.findByRemoteKey(countryRemoteKey);
        if (country.isPresent()) {
            Collection<Team> teams = api1Port.fetchTeams(country.get());
            teams.forEach(team -> createOrUpdateTeam(team));
        } else {
            throw new GameonRuntimeException("Unable to find country with remote key %s", countryRemoteKey);
        }
    }

    public void fetchAndSaveLeagues(Optional<RemoteKey> countryRemoteKey) {
        Optional<Country> country;
        if (countryRemoteKey.isPresent()) {
            country = countryRepository.findByRemoteKey(countryRemoteKey.get());
            if (!country.isPresent()) {
                throw new GameonRuntimeException("Unable to find country with remote key %s", countryRemoteKey);
            }
        } else {
            country = Optional.empty();
        }
        Collection<Tournament> tournaments = api1Port.fetchLeagues(country);
        tournaments.forEach(tournament -> createOrUpdateTournament(tournament));
    }

    public void fetchAndSaveSeasons(RemoteKey remoteKey) {
        Optional<Tournament> tournament = tournamentRepository.findByRemoteKey(remoteKey);
        if (tournament.isPresent()) {
            Collection<Season> seasons = api1Port.fetchSeasons(tournament.get());
            seasons.forEach(season -> createOrUpdateSeason(tournament.get(), season));
        } else {
            throw new GameonRuntimeException("Unable to find tournament with remote key %s", remoteKey);
        }
    }

    public void fetchAndSaveMatches(RemoteKey seasonRemoteKey) {
        Optional<Season> season = tournamentRepository.findSeasonByRemoteKey(seasonRemoteKey);
        if (season.isPresent()) {
            Collection<Match> fixtures = api1Port.fetchMatches(season.get());
            fixtures.forEach(fixture -> createOrUpdateFixture(season.get(), fixture));
        } else {
            throw new GameonRuntimeException("Unable to find season with remote key %s", seasonRemoteKey);
        }
    }

    public void fetchAndSaveCountries() {
        Collection<Country> countries = api1Port.fetchCountry();
        countries.forEach(country -> createOrUpdateCountry(country));
    }

    private void createOrUpdateFixture(Season season, Match fixture) {
        Optional<Match> persisted = tournamentRepository.findMatchByRemoteKey(fixture.getRemoteKey());
        if (persisted.isPresent()) {
            applyFixture(fixture, persisted.get());
        } else {
            fixture.setVenue(null);
            fixture.setHomeTeam(getPersistedTeam(fixture.getHomeTeam()));
            fixture.setAwayTeam(getPersistedTeam(fixture.getAwayTeam()));
            fixture.setSeason(season);
            season.addMatch(fixture);
            tournamentRepository.persist(fixture);
        }
    }

    private void applyFixture(Match source, Match target) {
        target.setMatchStart(source.getMatchStart());
        target.setFinalScore(source.getFinalScore());
        target.setHomeTeam(getPersistedTeam(source.getHomeTeam()));
        target.setAwayTeam(getPersistedTeam(source.getAwayTeam()));
    }


    private void createOrUpdateSeason(Tournament tournament, Season season) {
        Optional<Season> persisted = tournamentRepository.find(tournament, season);
        if (persisted.isPresent()) {
            applySeason(season, persisted.get());
        } else {
            season.setTournament(tournament);
            tournament.addSeason(season);
            tournamentRepository.persist(season);
        }
    }

    private void applySeason(Season source, Season target) {
        target.setName(source.getName());
    }

    private void createOrUpdateCountry(Country country) {
        Optional<Country> persisted = countryRepository.find(country);
        if (persisted.isPresent()) {
            applyCountry(country, persisted.get());
        } else {
            countryRepository.persist(country);
        }
    }

    private void applyCountry(Country source, Country target) {
        target.setName(source.getName());
        target.setCountryCode(source.getCountryCode());
        target.setContinent(source.getContinent());
        target.setRemoteKey(source.getRemoteKey());
    }

    private void createOrUpdateTeam(Team team) {
        Optional<Team> persisted = teamRepository.find(team);
        if (persisted.isPresent()) {
            applyTeam(team, persisted.get());
        } else {
            teamRepository.persist(team);
        }
    }

    private void applyTeam(Team source, Team target) {
        target.setName(source.getName());
        target.setCountryCode(source.getCountryCode());
        target.setLogo(source.getLogo());
    }

    private void createOrUpdateTournament(Tournament tournament) {
        Optional<Tournament> persisted = tournamentRepository.find(tournament);
        if (persisted.isPresent()) {
            applyTournament(tournament, persisted.get());
        } else {
            teamRepository.persist(tournament);
        }
    }

    private void applyTournament(Tournament source, Tournament target) {
        target.setName(source.getName());
        target.setCountryCode(source.getCountryCode());
    }

    private Team getPersistedTeam(Team team) {
        Optional<Team> persistedTeam = teamRepository.find(team);
        if (!persistedTeam.isPresent()) {
            throw new GameonRuntimeException("Expected persisted team with remote key %s was not found", team.getRemoteKey());
        }
        return persistedTeam.get();
    }


}
