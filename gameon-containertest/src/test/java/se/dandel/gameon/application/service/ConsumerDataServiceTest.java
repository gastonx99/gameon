package se.dandel.gameon.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import se.dandel.gameon.ContainerTest;
import se.dandel.gameon.adapter.jpa.PersistenceTestManager;
import se.dandel.gameon.domain.model.*;
import se.dandel.gameon.domain.repository.AllPurposeTestRepository;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static se.dandel.gameon.domain.model.TestTeamFactory.createTeam;
import static se.dandel.gameon.domain.model.TestTournamentFactory.createLeagueMatch;

@ContainerTest
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {9999})
class ConsumerDataServiceTest {

    @Inject
    ConsumerDataService service;

    @Inject
    PersistenceTestManager persistManager;

    @Inject
    AllPurposeTestRepository allPurposeTestRepository;

    @Test
    void saveCountries() {
        // Given

        // When
        persistManager.reset();
        Collection<Country> countries = asList(
                TestCountryFactory.createCountrySweden(),
                TestCountryFactory.createCountryUnitedKingdom()
        );
        service.saveCountries(countries);

        // Then
        Collection<Country> actuals = allPurposeTestRepository.findAll(Country.class);
        assertThat(actuals.size(), is(equalTo(2)));
    }

    @Test
    void saveTeams() {
        // Given
        Country countrySweden = TestCountryFactory.createCountrySweden();
        allPurposeTestRepository.persist(countrySweden);

        // When
        persistManager.reset();
        Collection<Team> teams = asList(
                createTeam("AIK", countrySweden),
                createTeam("Kalmar", countrySweden),
                createTeam("Halmstad", countrySweden)
        );
        service.saveTeams(teams);

        // Then
        Collection<Team> actuals = allPurposeTestRepository.findAll(Team.class);
        assertThat(actuals.size(), is(equalTo(3)));
    }

    @Test
    void saveTournaments() {
        // Given
        Country countrySweden = TestCountryFactory.createCountrySweden();
        allPurposeTestRepository.persist(countrySweden);
        Collection<Tournament> tournaments = asList(
                TestTournamentFactory.createTournament(countrySweden, TournamentType.LEAGUE, "Allsvenskan"),
                TestTournamentFactory.createTournament(countrySweden, TournamentType.LEAGUE, "Div 1")
        );

        // When
        persistManager.reset();
        service.saveTournaments(tournaments);

        // Then
        Collection<Tournament> actuals = allPurposeTestRepository.findAll(Tournament.class);
        assertThat(actuals.size(), is(equalTo(2)));
    }

    @Test
    void saveSeasons() {
        // Given
        Tournament tournament = TestTournamentFactory.createLeague();
        persistManager.deepPersist(tournament);
        Collection<Season> seasons = asList(
                TestTournamentFactory.createSeason(tournament, "2020/21", null),
                TestTournamentFactory.createSeason(tournament, "2021/22", null));


        // When
        persistManager.reset();
        service.saveSeasons(tournament, seasons);

        // Then
        Collection<Season> actuals = allPurposeTestRepository.findAll(Season.class);
        assertThat(actuals.size(), is(equalTo(2)));
    }

    @Test
    void saveMatches() {
        // Given
        Season season = TestTournamentFactory.createSeason();
        persistManager.deepPersist(season);

        Team team1 = createTeam("FC Union Berlin");
        Team team2 = createTeam("FC Augsburg");
        asList(team1, team2).forEach(team -> persistManager.deepPersist(team));
        Collection<Match> matches = asList(
                createLeagueMatch(season, "1", LocalDateTime.now(), team1, team2),
                createLeagueMatch(season, "2", LocalDateTime.now(), team2, team1));

        // When
        persistManager.reset();
        service.saveMatches(season, matches);

        // Then
        Collection<Match> actuals = allPurposeTestRepository.findAll(Match.class);
        assertThat(actuals.size(), is(equalTo(2)));
    }

    @Test
    void saveMatchesAllowCreationOfTeamsIfTournamentCountryIsContinent() {
        // Given
        Season season = TestTournamentFactory.createSeason();
        season.getTournament().getCountry().setCountryCode(null);
        persistManager.deepPersist(season);

        Country countrySweden = TestCountryFactory.createCountrySweden();
        Team team1 = createTeam("AIK", countrySweden);
        Team team2 = createTeam("Kalmar", countrySweden);
        persistManager.deepPersist(team1);
        persistManager.deepPersist(team2);
        Collection<Match> matches = asList(
                createLeagueMatch(season, "1", LocalDateTime.now(), team1, team2),
                createLeagueMatch(season, "2", LocalDateTime.now(), team2, team1));

        // When
        persistManager.reset();
        service.saveMatches(season, matches);

        // Then
        Collection<Match> actuals = allPurposeTestRepository.findAll(Match.class);
        assertThat(actuals.size(), is(equalTo(2)));
    }


}