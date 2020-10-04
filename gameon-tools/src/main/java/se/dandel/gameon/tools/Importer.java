package se.dandel.gameon.tools;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.io.IOUtils;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.model.TournamentType;
import se.dandel.gameon.infrastructure.jpa.JpaTeamRepository;
import se.dandel.gameon.infrastructure.jpa.JpaTournamentRepository;
import se.dandel.gameon.infrastructure.json.JsonMatchParser;

public class Importer {

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private JsonMatchParser matchParser;

    @Inject
    private JpaTournamentRepository tournamentRepository;

    @Inject
    private JpaTeamRepository teamRepository;

    @Inject
    private EntityManager entityManager;

    public static void main(String[] args) {
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            Importer importer = container.select(Importer.class).get();
            importer.doImport(args);
        }
    }

    private void doImport(String[] args) {
        String json;
        try {
            json = IOUtils.toString(getClass().getResource("/WorldCup2018.json"), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        JSONObject jsonObject = new JSONObject(json);
        LOGGER.debug("Hello");
        try {
            entityManager.getTransaction().begin();
            handleTournament(jsonObject);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            entityManager.getTransaction().rollback();
            entityManager.clear();
        }
    }

    private void handleTournament(JSONObject jsonObject) {
        Collection<Match> matches = matchParser.parseMatches(jsonObject);
        Set<Team> teams = Match.getDistinctTeams(matches);
        Map<String, Team> persistedTeamMap =
                teamRepository.findMapped(teams.stream().map(team -> team.getKey()).collect(toList()));
        teams.stream().filter(team -> !persistedTeamMap.containsKey(team.getKey())).forEach(team -> {
            teamRepository.persist(team);
            persistedTeamMap.put(team.getKey(), team);
        });

        Tournament tournament = getOrCreateTournament("Men's Football World Cup");
        Season season = getOrCreateSeason(tournament, "2018");

        for (Match match : matches) {
            Optional<Match> persistedMatchOptional =
                    season.getMatch(match.getMatchStart(), match.getHomeTeam(), match.getAwayTeam());
            Match persistedMatch;
            if (persistedMatchOptional.isPresent()) {
                persistedMatch = persistedMatchOptional.get();
            } else {
                Team homeTeam = persistedTeamMap.get(match.getHomeTeam().getKey());
                Team awayTeam = persistedTeamMap.get(match.getAwayTeam().getKey());
                persistedMatch = new Match(season);
                persistedMatch.setTeams(homeTeam, awayTeam);
                persistedMatch.setMatchStart(match.getMatchStart());
                tournamentRepository.persist(persistedMatch);
            }
            persistedMatch.setVenue(match.getVenue());
            persistedMatch.getFinalScore().setHome(match.getFinalScore().getHome());
            persistedMatch.getFinalScore().setAway(match.getFinalScore().getAway());
        }
    }

    private Season getOrCreateSeason(Tournament tournament, String seasonName) {
        Optional<Season> seasonOptional = tournament.getSeason(seasonName);
        Season season;
        if (seasonOptional.isPresent()) {
            season = seasonOptional.get();
        } else {
            season = new Season(tournament);
            season.setName(seasonName);
        }
        return season;
    }

    private Tournament getOrCreateTournament(String tournamentName) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findTournament(tournamentName);
        Tournament tournament;
        if (tournamentOptional.isPresent()) {
            tournament = tournamentOptional.get();
        } else {
            tournament = new Tournament(TournamentType.CUP);
            tournament.setName(tournamentName);
            tournamentRepository.persist(tournament);
        }
        return tournament;
    }
}
