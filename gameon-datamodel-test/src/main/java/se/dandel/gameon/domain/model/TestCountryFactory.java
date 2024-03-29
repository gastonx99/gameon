package se.dandel.gameon.domain.model;

import java.util.concurrent.atomic.AtomicLong;

public class TestCountryFactory {
    private static final AtomicLong REMOTE_KEY = new AtomicLong(1);

    public static Country createCountrySweden() {
        return createCountry("se", "Sweden", "Europe", RemoteKey.of(REMOTE_KEY.getAndIncrement()));
    }

    public static Country createCountryUnitedKingdom() {
        return createCountry("uk", "United Kingdom", "Europe", RemoteKey.of(REMOTE_KEY.getAndIncrement()));
    }

    public static Country createCountry(RemoteKey remoteKey) {
        return createCountry("se", "Sweden", "Europe", remoteKey);
    }

    public static Country createCountry(String countryCode, String name, String continent) {
        return createCountry(countryCode, name, continent, RemoteKey.of(REMOTE_KEY.getAndIncrement()));
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
