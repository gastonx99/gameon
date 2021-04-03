package se.dandel.gameon.domain.repository;

import se.dandel.gameon.domain.model.Team;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TeamRepository extends AbstractRepository {

    public List<Team> findAll() {
        TypedQuery<Team> query = getEntityManager().createQuery("select t from Team t", Team.class);
        return query.getResultList();
    }

    public Optional<Team> find(Team team) {
        TypedQuery<Team> query =
                getEntityManager().createQuery("select t from Team t where t.name = :name", Team.class);
        query.setParameter("name", team.getName());
        return getSingleResult(query);
    }
}
