package se.dandel.gameon.infrastructure.jpa;

import java.util.Collection;

import javax.persistence.TypedQuery;

import se.dandel.gameon.domain.model.Game;

public class JpaGameRepository extends JpaAbstractRepository {

    public Collection<Game> findAll() {
        TypedQuery<Game> query = getEntityManager().createQuery("select g from Game g", Game.class);
        return query.getResultList();
    }
}
