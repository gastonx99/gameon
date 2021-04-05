package se.dandel.gameon.domain.model;

import java.util.concurrent.atomic.AtomicLong;

public class TestTeamFactory {
    private static final AtomicLong remoteKey = new AtomicLong(1);

    public static Team createTeam(String name) {
        String remoteKey = String.valueOf(TestTeamFactory.remoteKey.getAndIncrement());
        return createTeam(remoteKey, name);
    }

    public static Team createTeam(String remoteKey, String name) {
        Team team = new Team();
        team.setRemoteKey(RemoteKey.of(remoteKey));
        team.setName(name);
        return team;
    }

}
