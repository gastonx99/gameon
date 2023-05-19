package se.dandel.gameon.adapter;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnvironmentConfig {
    private Config config;

    public String getApi1BaseUrl() {
        return getConfig().getValue("se.dandel.gameon.api1.base.url", String.class);
    }

    public String getAPi1Apikey() {
        return getConfig().getValue("se.dandel.gameon.api1.apikey", String.class);
    }

    public String getApi2BaseUrl() {
        return getConfig().getValue("se.dandel.gameon.api2.base.url", String.class);
    }

    private Config getConfig() {
        if (config == null) {
            config = ConfigProvider.getConfig();
        }
        return config;
    }

}
