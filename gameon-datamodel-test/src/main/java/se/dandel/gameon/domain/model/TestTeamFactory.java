package se.dandel.gameon.domain.model;

import java.util.concurrent.atomic.AtomicLong;

public class TestTeamFactory {
    private static final AtomicLong REMOTE_KEY = new AtomicLong(1);

    public static Team createTeam(String name) {
        String remoteKey = String.valueOf(REMOTE_KEY.getAndIncrement());
        return createTeam(remoteKey, name);
    }

    public static Team createTeam(String name, Country country) {
        RemoteKey remoteKey = RemoteKey.of(REMOTE_KEY.getAndIncrement());
        return createTeam(remoteKey, name, country);
    }

    public static Team createTeam(String remoteKey, String name) {
        Country country = TestCountryFactory.createCountry();
        return createTeam(RemoteKey.of(remoteKey), name, country);
    }

    private static Team createTeam(RemoteKey remoteKey, String name, Country country) {
        Team team = new Team();
        team.setRemoteKey(remoteKey);
        team.setName(name);
        team.setCountry(country);
        return team;
    }

}
