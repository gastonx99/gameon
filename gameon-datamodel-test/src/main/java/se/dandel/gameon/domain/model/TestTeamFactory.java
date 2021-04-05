package se.dandel.gameon.domain.model;

import java.util.concurrent.atomic.AtomicLong;

public class TestTeamFactory {
    private static final AtomicLong remoteKey = new AtomicLong(1);

    public static Team createTeam(String key, String name) {
        Team team = new Team();
        team.setRemoteKey(RemoteKey.of(remoteKey.getAndIncrement()));
        team.setName(name);
        return team;
    }

}
