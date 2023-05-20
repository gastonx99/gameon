package se.dandel.gameon.adapter.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.model.Venue;

import java.util.Collection;

@Component
public class PersistenceTestManager {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
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

    public <T> Collection<T> findAll(Class<T> clazz) {
        Query query = entityManager.createQuery("select o from Object o where o.type = :clazz");
        query.setParameter("clazz", clazz);
        return query.getResultList();
    }
}
