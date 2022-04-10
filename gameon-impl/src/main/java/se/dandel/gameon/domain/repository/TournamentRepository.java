package se.dandel.gameon.domain.repository;

import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.RemoteKey;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Tournament;

import jakarta.persistence.TypedQuery;
import java.util.Collection;
import java.util.Optional;

public class TournamentRepository extends AbstractRepository {

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

    public Optional<Tournament> find(Tournament tournament) {
        TypedQuery<Tournament> query =
                getEntityManager().createQuery("select t from Tournament t where t.name = :name", Tournament.class);
        query.setParameter("name", tournament.getName());
        return getSingleResult(query);
    }

    public Optional<Tournament> findByRemoteKey(RemoteKey remoteKey) {
        TypedQuery<Tournament> query =
                getEntityManager().createQuery("select t from Tournament t where t.remoteKey = :remoteKey", Tournament.class);
        query.setParameter("remoteKey", remoteKey);
        return getSingleResult(query);
    }

    public Optional<Season> find(Tournament tournament, Season season) {
        TypedQuery<Season> query =
                getEntityManager().createQuery("select s from Season s where s.tournament = :tournament and s.remoteKey = :remoteKey", Season.class);
        query.setParameter("tournament", tournament);
        query.setParameter("remoteKey", season.getRemoteKey());
        return getSingleResult(query);
    }

    public Optional<Season> findSeasonByRemoteKey(RemoteKey remoteKey) {
        TypedQuery<Season> query =
                getEntityManager().createQuery("select s from Season s where s.remoteKey = :remoteKey", Season.class);
        query.setParameter("remoteKey", remoteKey);
        return getSingleResult(query);
    }

    public Optional<Match> findMatchByRemoteKey(RemoteKey remoteKey) {
        TypedQuery<Match> query =
                getEntityManager().createQuery("select m from Match m where m.remoteKey = :remoteKey", Match.class);
        query.setParameter("remoteKey", remoteKey);
        return getSingleResult(query);
    }
}
