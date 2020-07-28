package se.dandel.gameon.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import se.dandel.gameon.domain.model.Team;

public class JpaTeamRepository extends JpaAbstractRepository {

    public List<Team> findAll() {
        TypedQuery<Team> query = getEntityManager().createQuery("select t from Team t", Team.class);
        return query.getResultList();
    }
}
