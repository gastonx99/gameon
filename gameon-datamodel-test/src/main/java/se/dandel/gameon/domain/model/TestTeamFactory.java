package se.dandel.gameon.domain.model;

import java.util.concurrent.atomic.AtomicLong;

public class TestTeamFactory {
    private static final AtomicLong REMOTE_KEY = new AtomicLong(1);

    public static Team createTeam(String name) {
        String remoteKey = String.valueOf(REMOTE_KEY.getAndIncrement());
        return createTeam(remoteKey, name);
    }

    public static Team createTeam(String remoteKey, String name) {
        Team team = new Team();
        team.setRemoteKey(RemoteKey.of(remoteKey));
        team.setName(name);
        team.setCountry(TestCountryFactory.createCountry());
        return team;
    }

}
