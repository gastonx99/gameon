package se.dandel.gameon.domain.repository;

import se.dandel.gameon.domain.model.RemoteKey;
import se.dandel.gameon.domain.model.Team;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TeamRepository extends AbstractRepository {

    public List<Team> findAll() {
        TypedQuery<Team> query = getEntityManager().createQuery("select t from Team t", Team.class);
        return query.getResultList();
    }

    public Optional<Team> findByRemoteKey(RemoteKey remoteKey) {
        TypedQuery<Team> query =
                getEntityManager().createQuery("select t from Team t where t.remoteKey = :remoteKey", Team.class);
        query.setParameter("remoteKey", remoteKey);
        return getSingleResult(query);
    }
}
