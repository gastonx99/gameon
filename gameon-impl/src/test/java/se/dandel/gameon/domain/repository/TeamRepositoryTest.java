package se.dandel.gameon.domain.repository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.RepositoryTest;
import se.dandel.gameon.adapter.jpa.PersistenceTestManager;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.TestTeamFactory;

import jakarta.inject.Inject;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RepositoryTest
class TeamRepositoryTest {

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    TeamRepository repository;

    @Inject
    PersistenceTestManager persistManager;

    @Test
    void persist() {
        // Given
        Team persisted = TestTeamFactory.createTeam("Gurka");
        repository.persist(persisted.getCountry());

        // When
        repository.persist(persisted);
        persistManager.reset();

        // Then
        assertThat(persisted.getPk(), is(greaterThan(0L)));
    }

    @Test
    void findAll() {
        // Given
        Team expected = TestTeamFactory.createTeam("Gurka");
        persistManager.deepPersist(expected);
        persistManager.reset();

        // When
        Collection<Team> actuals = repository.findAll();

        // Then
        assertThat(actuals.size(), is(equalTo(1)));
        Team actual = actuals.iterator().next();
        assertThat(actual.getPk(), is(greaterThan(0L)));

    }

}