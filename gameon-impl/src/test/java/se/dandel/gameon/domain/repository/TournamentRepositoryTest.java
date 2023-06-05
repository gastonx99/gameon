package se.dandel.gameon.domain.repository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.dandel.gameon.domain.model.*;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@DataJpaTest
class TournamentRepositoryTest {

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    TournamentRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void persist() {
        // Given
        Country country = TestCountryFactory.createCountrySweden();
        entityManager.persist(country);
        Tournament expected = new Tournament(TournamentType.LEAGUE);
        expected.setName("My league");
        expected.setRemoteKey(RemoteKey.of(1));
        expected.setCountry(country);
        repository.save(expected);

        // When
        Collection<Tournament> actuals = repository.findAll();

        // Then
        Tournament actual = actuals.iterator().next();
        assertThat(actual.getPk(), is(greaterThan(0L)));

    }

}