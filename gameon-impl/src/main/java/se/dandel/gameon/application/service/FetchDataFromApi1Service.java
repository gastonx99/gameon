package se.dandel.gameon.application.service;

import se.dandel.gameon.domain.GameonRuntimeException;
import se.dandel.gameon.domain.model.*;
import se.dandel.gameon.domain.port.Api1Port;
import se.dandel.gameon.domain.repository.CountryRepository;
import se.dandel.gameon.domain.repository.TeamRepository;
import se.dandel.gameon.domain.repository.TournamentRepository;

import jakarta.inject.Inject;
import java.util.*;

import static java.util.stream.Collectors.toList;

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
            Collection<Match> matches = api1Port.fetchMatches(season.get());

            List<Match> matchesHavingTeams = matches.stream().filter(match -> match.getHomeTeam() != null && match.getAwayTeam() != null).collect(toList());

            if (season.get().getTournament().getCountry().isContinent()) {
                persistMissingTeams(matchesHavingTeams);
            }
            matchesHavingTeams.forEach(match -> createOrUpdateMatch(season.get(), match));
        } else {
            throw new GameonRuntimeException("Unable to find season with remote key %s", seasonRemoteKey);
        }
    }

    private void persistMissingTeams(Collection<Match> matches) {
        Map<RemoteKey, Team> teams = new HashMap<>();
        for (Match match : matches) {
            if (!teams.containsKey(match.getHomeTeam().getRemoteKey())) {
                teams.put(match.getHomeTeam().getRemoteKey(), match.getHomeTeam());
            }
            if (!teams.containsKey(match.getAwayTeam().getRemoteKey())) {
                teams.put(match.getAwayTeam().getRemoteKey(), match.getAwayTeam());
            }
        }
        teams.values().forEach(team -> {
            Optional<Team> persisted = teamRepository.findByRemoteKey(team.getRemoteKey());
            if (!persisted.isPresent()) {
                team.setCountry(getPersistedCountry(team.getCountry()));
                teamRepository.persist(team);
            }
        });
    }

    public void fetchAndSaveCountries() {
        Collection<Country> countries = api1Port.fetchCountry();
        countries.forEach(country -> createOrUpdateCountry(country));
    }

    private void createOrUpdateMatch(Season season, Match match) {
        Optional<Match> persisted = tournamentRepository.findMatchByRemoteKey(match.getRemoteKey());
        if (persisted.isPresent()) {
            applyMatch(match, persisted.get());
        } else {
            match.setVenue(null);
            match.setHomeTeam(getPersistedTeam(match.getHomeTeam()));
            match.setAwayTeam(getPersistedTeam(match.getAwayTeam()));
            match.setSeason(season);
            season.addMatch(match);
            tournamentRepository.persist(match);
        }
    }

    private void applyMatch(Match source, Match target) {
        target.setStatus(source.getStatus());
        target.setStatustext(source.getStatustext());
        target.setMatchStart(source.getMatchStart());
        target.setStage(source.getStage());
        target.setGroup(source.getGroup());
        target.setRound(source.getRound());
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
        Optional<Team> persisted = teamRepository.findByRemoteKey(team.getRemoteKey());
        if (persisted.isPresent()) {
            applyTeam(team, persisted.get());
        } else {
            team.setCountry(getPersistedCountry(team.getCountry()));
            teamRepository.persist(team);
        }
    }

    private void applyTeam(Team source, Team target) {
        target.setName(source.getName());
        target.setShortCode(source.getShortCode());
        target.setCountry(getPersistedCountry(source.getCountry()));
        target.setLogo(source.getLogo());
    }

    private void createOrUpdateTournament(Tournament tournament) {
        Optional<Tournament> persisted = tournamentRepository.find(tournament);
        if (persisted.isPresent()) {
            applyTournament(tournament, persisted.get());
        } else {
            tournament.setCountry(getPersistedCountry(tournament.getCountry()));
            tournamentRepository.persist(tournament);
        }
    }

    private void applyTournament(Tournament source, Tournament target) {
        target.setName(source.getName());
        target.setCountry(getPersistedCountry(source.getCountry()));
    }

    private Country getPersistedCountry(Country country) {
        Optional<Country> persisted = countryRepository.findByRemoteKey(country.getRemoteKey());
        if (!persisted.isPresent()) {
            throw new GameonRuntimeException("Persisted country with remote key %s does not exist", country.getRemoteKey());
        }
        return persisted.get();
    }

    private Team getPersistedTeam(Team team) {
        Optional<Team> persisted = teamRepository.findByRemoteKey(team.getRemoteKey());
        if (!persisted.isPresent()) {
            throw new GameonRuntimeException("Persisted team with remote key %s does not exist", team.getRemoteKey());
        }
        return persisted.get();
    }


}
