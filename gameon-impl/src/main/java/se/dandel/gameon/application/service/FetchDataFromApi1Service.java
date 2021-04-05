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

    public void fetchAndSaveTeams(String countryCode) {
        Optional<Country> country = countryRepository.findByCountryCode(countryCode);
        if (country.isPresent()) {
            Collection<Team> teams = api1Port.fetchTeams(country.get());
            teams.forEach(team -> createOrUpdate(team));
        } else {
            throw new GameonRuntimeException("Unable to find country with code %s", countryCode);
        }
    }

    public void fetchAndSaveLeagues(String countryCode) {
        Optional<Country> country = countryRepository.findByCountryCode(countryCode);
        if (country.isPresent()) {
            Collection<Tournament> tournaments = api1Port.fetchLeagues(country.get());
            tournaments.forEach(tournament -> createOrUpdate(tournament));
        } else {
            throw new GameonRuntimeException("Unable to find country with code %s", countryCode);
        }
    }

    public void fetchAndSaveSeasons(RemoteKey remoteKey) {
        Optional<Tournament> tournament = tournamentRepository.findByRemoteKey(remoteKey);
        if (tournament.isPresent()) {
            Collection<Season> seasons = api1Port.fetchSeasons(tournament.get());
            seasons.forEach(season -> createOrUpdate(tournament.get(), season));
        } else {
            throw new GameonRuntimeException("Unable to find tournament with remote key %s", remoteKey);
        }
    }

    public void fetchAndSaveMatches(RemoteKey seasonRemoteKey) {
        Optional<Season> season = tournamentRepository.findSeasonByRemoteKey(seasonRemoteKey);
        if (season.isPresent()) {
            Collection<Match> fixtures = api1Port.fetchMatches(season.get());
            fixtures.forEach(fixture -> createOrUpdate(season.get(), fixture));
        } else {
            throw new GameonRuntimeException("Unable to find season with remote key %s", seasonRemoteKey);
        }
    }

    public void fetchAndSaveCountries() {
        Collection<Country> countries = api1Port.fetchCountry();
        countries.forEach(country -> createOrUpdate(country));
    }

    private void createOrUpdate(Season season, Match fixture) {
        Optional<Match> persisted = tournamentRepository.findMatchByRemoteKey(fixture.getRemoteKey());
        if (persisted.isPresent()) {
            apply(fixture, persisted.get());
        } else {
            fixture.setVenue(null);
            Team homeTeam = fixture.getHomeTeam();
            fixture.setHomeTeam(getPersistedTeam(fixture.getHomeTeam()));
            fixture.setAwayTeam(getPersistedTeam(fixture.getAwayTeam()));
            fixture.setSeason(season);
            season.addMatch(fixture);
            tournamentRepository.persist(fixture);
        }
    }

    private void apply(Match source, Match target) {
        target.setMatchStart(source.getMatchStart());
        target.setFinalScore(source.getFinalScore());
        target.setHomeTeam(getPersistedTeam(source.getHomeTeam()));
        target.setAwayTeam(getPersistedTeam(source.getAwayTeam()));
    }


    private void createOrUpdate(Tournament tournament, Season season) {
        Optional<Season> persisted = tournamentRepository.find(tournament, season);
        if (persisted.isPresent()) {
            apply(season, persisted.get());
        } else {
            season.setTournament(tournament);
            tournament.addSeason(season);
            tournamentRepository.persist(season);
        }
    }

    private void apply(Season source, Season target) {
        target.setName(source.getName());
    }

    private void createOrUpdate(Country country) {
        Optional<Country> persisted = countryRepository.find(country);
        if (persisted.isPresent()) {
            apply(country, persisted.get());
        } else {
            countryRepository.persist(country);
        }
    }

    private void apply(Country source, Country target) {
        target.setName(source.getName());
        target.setCountryCode(source.getCountryCode());
        target.setContinent(source.getContinent());
        target.setRemoteKey(source.getRemoteKey());
    }

    private void createOrUpdate(Team team) {
        Optional<Team> persisted = teamRepository.find(team);
        if (persisted.isPresent()) {
            apply(team, persisted.get());
        } else {
            teamRepository.persist(team);
        }
    }

    private void apply(Team source, Team target) {
        target.setName(source.getName());
        target.setCountryCode(source.getCountryCode());
        target.setLogo(source.getLogo());
    }

    private void createOrUpdate(Tournament tournament) {
        Optional<Tournament> persisted = tournamentRepository.find(tournament);
        if (persisted.isPresent()) {
            apply(tournament, persisted.get());
        } else {
            teamRepository.persist(tournament);
        }
    }

    private void apply(Tournament source, Tournament target) {
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
