package se.dandel.gameon.infrastructure.jpa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.Collection;

import javax.inject.Inject;

import org.jboss.weld.junit5.EnableWeld;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.dandel.gameon.datamodel.test.jpa.JpaTestManager;
import se.dandel.gameon.datamodel.test.jpa.JpaTestManagerExtension;
import se.dandel.gameon.domain.model.Game;

@ExtendWith(JpaTestManagerExtension.class)
@EnableWeld
class JpaGameRepositoryTest {

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    JpaGameRepository repository;

    @Test
    void persist(JpaTestManager jpaTestManager) {
        // Given
        Game persisted = new Game();
        persisted.setName("Gurka");

        // When
        repository.persist(persisted);
        jpaTestManager.getEntityManager().flush();

        // Then
        assertThat(persisted.getPk(), is(greaterThan(0L)));
    }

    @Test
    void findAll(JpaTestManager jpaTestManager) {
        // Given
        Game expected = new Game();
        expected.setName("Gurka");
        repository.persist(expected);

        // When
        Collection<Game> actuals = repository.findAll();

        // Then
        assertThat(actuals.size(), is(equalTo(1)));
        Game actual = actuals.iterator().next();
        assertThat(actual.getPk(), is(greaterThan(0L)));

    }

}