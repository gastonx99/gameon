package se.dandel.gameon.domain.repository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import se.dandel.gameon.adapter.jpa.PersistenceTestManager;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.TestTeamFactory;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@DataJpaTest
@Import(PersistenceTestManager.class)
class TeamRepositoryTest {

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    TeamRepository repository;

    @Autowired
    PersistenceTestManager persistManager;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void persist() {
        // Given
        Team persisted = TestTeamFactory.createTeam("Gurka");
        entityManager.persist(persisted.getCountry());

        // When
        repository.save(persisted);

        // Then
        assertThat(persisted.getPk(), is(greaterThan(0L)));
        assertNotSame(persisted, repository.findById(persisted.getPk()));
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