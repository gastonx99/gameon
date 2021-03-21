package se.dandel.gameon.adapter;

import org.eclipse.microprofile.config.Config;

import javax.inject.Inject;

public class EnvironmentConfig {
    @Inject
    private Config config;

    public String getApi1BaseUrl() {
        return config.getValue("se.dandel.gameon.api1.base.url", String.class);
    }

    public String getAPi1Apikey() {
        return config.getValue("se.dandel.gameon.api1.apikey", String.class);
    }

    public String getApi2BaseUrl() {
        return config.getValue("se.dandel.gameon.api2.base.url", String.class);
    }
}
