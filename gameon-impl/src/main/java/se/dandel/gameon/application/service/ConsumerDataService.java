package se.dandel.gameon.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.dandel.gameon.domain.GameonRuntimeException;
import se.dandel.gameon.domain.model.*;
import se.dandel.gameon.domain.repository.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Component
public class ConsumerDataService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CountryRepository countryRepository;

    public void saveTeams(Collection<Team> teams) {
        teams.forEach(team -> createOrUpdateTeam(team));
    }

    public void saveTournaments(Collection<Tournament> tournaments) {
        tournaments.forEach(tournament -> createOrUpdateTournament(tournament));
    }

    public void saveSeasons(Tournament tournament, Collection<Season> seasons) {
        seasons.forEach(season -> createOrUpdateSeason(tournament, season));
    }

    public void saveMatches(Season season, Collection<Match> matches) {

        List<Match> matchesHavingTeams = matches.stream().filter(match -> match.getHomeTeam() != null && match.getAwayTeam() != null).collect(toList());

        if (season.getTournament().getCountry().isContinent()) {
            persistMissingTeams(matchesHavingTeams);
        }
        matchesHavingTeams.forEach(match -> createOrUpdateMatch(season, match));
    }

    public void saveCountries(Collection<Country> countries) {
        countries.forEach(country -> createOrUpdateCountry(country));
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
                teamRepository.save(team);
            }
        });
    }

    private void createOrUpdateMatch(Season season, Match match) {
        Optional<Match> persisted = matchRepository.findByRemoteKey(match.getRemoteKey());
        if (persisted.isPresent()) {
            applyMatch(match, persisted.get());
        } else {
            match.setVenue(null);
            match.setHomeTeam(getPersistedTeam(match.getHomeTeam()));
            match.setAwayTeam(getPersistedTeam(match.getAwayTeam()));
            match.setSeason(season);
            season.addMatch(match);
            matchRepository.save(match);
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
        Optional<Season> persisted = seasonRepository.findByTournament(tournament);
        if (persisted.isPresent()) {
            applySeason(season, persisted.get());
        } else {
            season.setTournament(tournament);
            tournament.addSeason(season);
            seasonRepository.save(season);
        }
    }

    private void applySeason(Season source, Season target) {
        target.setName(source.getName());
    }

    private void createOrUpdateCountry(Country country) {
        Optional<Country> persisted = countryRepository.findByRemoteKey(country.getRemoteKey());
        if (persisted.isPresent()) {
            applyCountry(country, persisted.get());
        } else {
            countryRepository.save(country);
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
            teamRepository.save(team);
        }
    }

    private void applyTeam(Team source, Team target) {
        target.setName(source.getName());
        target.setShortCode(source.getShortCode());
        target.setCountry(getPersistedCountry(source.getCountry()));
        target.setLogo(source.getLogo());
    }

    private void createOrUpdateTournament(Tournament tournament) {
        Optional<Tournament> persisted = tournamentRepository.findByRemoteKey(tournament.getRemoteKey());
        if (persisted.isPresent()) {
            applyTournament(tournament, persisted.get());
        } else {
            tournament.setCountry(getPersistedCountry(tournament.getCountry()));
            tournamentRepository.save(tournament);
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
