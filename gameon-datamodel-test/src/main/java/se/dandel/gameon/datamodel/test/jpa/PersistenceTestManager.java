package se.dandel.gameon.datamodel.test.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
