package se.dandel.gameon.domain.repository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.RepositoryTest;
import se.dandel.gameon.adapter.jpa.PersistenceTestManager;
import se.dandel.gameon.domain.model.*;

import jakarta.inject.Inject;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RepositoryTest
class TournamentRepositoryTest {

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    TournamentRepository repository;

    @Inject
    PersistenceTestManager persistManager;

    @Test
    void persist() {
        // Given
        Country country = TestCountryFactory.createCountry();
        repository.persist(country);
        Tournament expected = new Tournament(TournamentType.LEAGUE);
        expected.setName("My league");
        expected.setRemoteKey(RemoteKey.of(1));
        expected.setCountry(country);
        repository.persist(expected);

        // When
        persistManager.reset();
        Collection<Tournament> actuals = repository.findAllTournaments();

        // Then
        Tournament actual = actuals.iterator().next();
        assertThat(actual.getPk(), is(greaterThan(0L)));

    }

}