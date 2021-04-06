package se.dandel.gameon.adapter.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.model.Venue;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class PersistenceTestManager {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private EntityManager entityManager;

    private void commitAndBegin() {
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.getTransaction().begin();
    }


    public EntityManager em() {
        return entityManager;
    }

    public void reset() {
        commitAndBegin();
    }

    public void deepPersist(Object entity) {
        if (Season.class.isAssignableFrom(entity.getClass())) {
            deepPersist(Season.class.cast(entity).getTournament());
        } else if (Tournament.class.isAssignableFrom(entity.getClass())) {
            Tournament tournament = Tournament.class.cast(entity);
            entityManager.persist(tournament.getCountry());
            entityManager.persist(tournament);
        } else if (Venue.class.isAssignableFrom(entity.getClass())) {
            Venue venue = Venue.class.cast(entity);
            entityManager.persist(venue.getCountry());
            entityManager.persist(venue);
        } else if (Team.class.isAssignableFrom(entity.getClass())) {
            Team team = Team.class.cast(entity);
            entityManager.persist(team.getCountry());
            entityManager.persist(team);
        }
    }
}
