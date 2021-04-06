package se.dandel.gameon.domain.model;

import java.util.concurrent.atomic.AtomicLong;

public class TestCountryFactory {
    private static final AtomicLong REMOTE_KEY = new AtomicLong(1);

    public static Country createCountry() {
        return createCountry("se", "Sweden", "Europe", RemoteKey.of(REMOTE_KEY.getAndIncrement()));
    }

    public static Country createCountry(RemoteKey remoteKey) {
        return createCountry("se", "Sweden", "Europe", remoteKey);
    }

    private static Country createCountry(String countryCode, String name, String continent, RemoteKey remoteKey) {
        Country country = new Country();
        country.setCountryCode(countryCode);
        country.setName(name);
        country.setContinent(continent);
        country.setRemoteKey(remoteKey);
        return country;
    }
}
