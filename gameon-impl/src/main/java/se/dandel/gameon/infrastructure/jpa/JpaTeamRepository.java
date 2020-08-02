package se.dandel.gameon.infrastructure.jpa;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import se.dandel.gameon.domain.model.Team;

public class JpaTeamRepository extends JpaAbstractRepository {

    public List<Team> findAll() {
        TypedQuery<Team> query = getEntityManager().createQuery("select t from Team t", Team.class);
        return query.getResultList();
    }

    public Map<String, Team> findMapped(Collection<String> keys) {
        TypedQuery<Team> query =
                getEntityManager().createQuery("select t from Team t where t.key in :keys", Team.class);
        query.setParameter("keys", keys);
        return Team.map(query.getResultList());
    }
}
