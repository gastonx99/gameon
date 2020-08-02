package se.dandel.gameon.infrastructure.jpa;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.TypedQuery;

import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Tournament;

public class JpaTournamentRepository extends JpaAbstractRepository {

    public Collection<Match> findAllMatches() {
        TypedQuery<Match> query = getEntityManager().createQuery("select m from Match m", Match.class);
        return query.getResultList();
    }

    public Collection<Tournament> findAllTournaments() {
        TypedQuery<Tournament> query = getEntityManager().createQuery("select t from Tournament t", Tournament.class);
        return query.getResultList();
    }

    public Tournament getTournament(long pk) {
        return getEntityManager().find(Tournament.class, pk);
    }

    public Optional<Tournament> findTournament(String name) {
        TypedQuery<Tournament> query =
                getEntityManager().createQuery("select t from Tournament t where t.name = :name", Tournament.class);
        query.setParameter("name", name);
        return getSingleResult(query);
    }
}
