package se.dandel.gameon.adapter.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import se.dandel.gameon.application.service.FetchDataFromApi1Service;
import se.dandel.gameon.domain.GameonRuntimeException;
import se.dandel.gameon.domain.model.RemoteKey;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "FetchDataFromApi1", mixinStandardHelpOptions = true)
public class FetchDataFromApi1Cli implements Callable<Integer> {

    private static final String TYPE = "se.dandel.gameon.cli.fetch.type";

    private static final String PARAM_PREFIX = "se.dandel.gameon.cli.fetch.param";

    public enum FetchType {
        team, league, season, match, country
    }

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @CommandLine.Option(names = {"-t", "--type"}, description = "team, league, country", required = true)
    private FetchType type;

    @CommandLine.Option(names = {"-p", "--properties"}, description = "path to properties file", required = true)
    private File propertiesFile;

    @CommandLine.Option(names = {"-a", "--args"}, description = "Arguments for fetching data, e.g. -a countrycode=en")
    private Map<String, String> arguments;

    @Override
    public Integer call() throws Exception {
        try {
            Properties properties = new Properties();
            if (arguments != null) {
                arguments.entrySet().forEach(a -> properties.put(PARAM_PREFIX + "." + a.getKey(), a.getValue()));
            }
            properties.put(TYPE, type);
            try (FileInputStream fis = new FileInputStream(propertiesFile)) {
                properties.load(fis);
            }
            properties.entrySet().forEach(p -> System.setProperty(p.getKey().toString(), p.getValue().toString()));

            SpringApplication.run(Runner.class);

            return 0;
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);
            return -1;
        }
    }

    @SpringBootApplication
    public static class Runner {
        private final Logger LOGGER = LoggerFactory.getLogger(getClass());

        @Autowired
        private FetchDataFromApi1Service service;

        @Value(TYPE)
        private FetchType type;

        @Value(PARAM_PREFIX + ".countryid")
        private Optional<String> countryId;

        @Value(PARAM_PREFIX + ".leagueid")
        private String leagueId;

        @Value(PARAM_PREFIX + ".seasonid")
        private String seasonId;


        public void start() {
            callService();
        }

        private void callService() {
            LOGGER.atDebug().log("Do some good using service {}", service);
            switch (type) {
                case country:
                    service.fetchCountries();
                    break;
                case team:
                    service.fetchTeams(getCountryId().get());
                    break;
                case league:
                    service.fetchLeagues(getCountryId());
                    break;
                case season:
                    service.fetchSeasons(RemoteKey.of(leagueId));
                    break;
                case match:
                    service.fetchMatches(RemoteKey.of(seasonId));
                    break;
                default:
                    throw new GameonRuntimeException("Unsupported type %s", type);
            }
        }

        private Optional<RemoteKey> getCountryId() {
            return countryId.isPresent() ? Optional.of(RemoteKey.of(countryId.get())) : Optional.empty();
        }

    }

    public static void main(String... args) {
        mainInner(args);
    }

    static int mainInner(String... args) {
        int execute = new CommandLine(new FetchDataFromApi1Cli()).execute(args);
        return execute;
    }

}
