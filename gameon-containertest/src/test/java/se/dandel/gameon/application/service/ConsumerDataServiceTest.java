package se.dandel.gameon.application.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import se.dandel.gameon.adapter.jpa.PersistenceTestManager;
import se.dandel.gameon.domain.model.*;

import java.time.LocalDateTime;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static se.dandel.gameon.domain.model.TestTeamFactory.createTeam;
import static se.dandel.gameon.domain.model.TestTournamentFactory.createLeagueMatch;

@Integrationstest
@Disabled("TODO")
// TODO: How is service supposed to be invoked, MDB perhaps?
class ConsumerDataServiceTest {

    ConsumerDataService service;

    @Autowired
    private PersistenceTestManager persistenceTestManager;

    @Test
    void saveCountries() {
        // Given

        // When
        persistenceTestManager.reset();
        Collection<Country> countries = asList(
                TestCountryFactory.createCountrySweden(),
                TestCountryFactory.createCountryUnitedKingdom()
        );
        service.saveCountries(countries);

        // Then
        Collection<Country> actuals = persistenceTestManager.findAll(Country.class);
        assertThat(actuals.size(), is(equalTo(2)));
    }

    @Test
    void saveTeams() {
        // Given
        Country countrySweden = TestCountryFactory.createCountrySweden();
        persistenceTestManager.em().persist(countrySweden);

        // When
        persistenceTestManager.reset();
        Collection<Team> teams = asList(
                createTeam("AIK", countrySweden),
                createTeam("Kalmar", countrySweden),
                createTeam("Halmstad", countrySweden)
        );
        service.saveTeams(teams);

        // Then
        Collection<Team> actuals = persistenceTestManager.findAll(Team.class);
        assertThat(actuals.size(), is(equalTo(3)));
    }

    @Test
    void saveTournaments() {
        // Given
        Country countrySweden = TestCountryFactory.createCountrySweden();
        persistenceTestManager.em().persist(countrySweden);
        Collection<Tournament> tournaments = asList(
                TestTournamentFactory.createTournament(countrySweden, TournamentType.LEAGUE, "Allsvenskan"),
                TestTournamentFactory.createTournament(countrySweden, TournamentType.LEAGUE, "Div 1")
        );

        // When
        persistenceTestManager.reset();
        service.saveTournaments(tournaments);

        // Then
        Collection<Tournament> actuals = persistenceTestManager.findAll(Tournament.class);
        assertThat(actuals.size(), is(equalTo(2)));
    }

    @Test
    void saveSeasons() {
        // Given
        Tournament tournament = TestTournamentFactory.createLeague();
        persistenceTestManager.deepPersist(tournament);
        Collection<Season> seasons = asList(
                TestTournamentFactory.createSeason(tournament, "2020/21", null),
                TestTournamentFactory.createSeason(tournament, "2021/22", null));


        // When
        persistenceTestManager.reset();
        service.saveSeasons(tournament, seasons);

        // Then
        Collection<Season> actuals = persistenceTestManager.findAll(Season.class);
        assertThat(actuals.size(), is(equalTo(2)));
    }

    @Test
    void saveMatches() {
        // Given
        Season season = TestTournamentFactory.createSeason();
        persistenceTestManager.deepPersist(season);

        Team team1 = createTeam("FC Union Berlin");
        Team team2 = createTeam("FC Augsburg");
        asList(team1, team2).forEach(team -> persistenceTestManager.deepPersist(team));
        Collection<Match> matches = asList(
                createLeagueMatch(season, "1", LocalDateTime.now(), team1, team2),
                createLeagueMatch(season, "2", LocalDateTime.now(), team2, team1));

        // When
        persistenceTestManager.reset();
        service.saveMatches(season, matches);

        // Then
        Collection<Match> actuals = persistenceTestManager.findAll(Match.class);
        assertThat(actuals.size(), is(equalTo(2)));
    }

    @Test
    void saveMatchesAllowCreationOfTeamsIfTournamentCountryIsContinent() {
        // Given
        Season season = TestTournamentFactory.createSeason();
        season.getTournament().getCountry().setCountryCode(null);
        persistenceTestManager.deepPersist(season);

        Country countrySweden = TestCountryFactory.createCountrySweden();
        Team team1 = createTeam("AIK", countrySweden);
        Team team2 = createTeam("Kalmar", countrySweden);
        persistenceTestManager.deepPersist(team1);
        persistenceTestManager.deepPersist(team2);
        Collection<Match> matches = asList(
                createLeagueMatch(season, "1", LocalDateTime.now(), team1, team2),
                createLeagueMatch(season, "2", LocalDateTime.now(), team2, team1));

        // When
        persistenceTestManager.reset();
        service.saveMatches(season, matches);

        // Then
        Collection<Match> actuals = persistenceTestManager.findAll(Match.class);
        assertThat(actuals.size(), is(equalTo(2)));
    }


}