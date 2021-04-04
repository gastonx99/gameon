package se.dandel.gameon.adapter.cli;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import se.dandel.gameon.application.service.FetchDataFromApi1Service;
import se.dandel.gameon.domain.GameonRuntimeException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "FetchDataFromApi1", mixinStandardHelpOptions = true)
public class FetchDataFromApi1Cli implements Callable<Integer> {

    private static final String TYPE = "se.dandel.gameon.cli.fetch.type";

    private static final String PARAM_PREFIX = "se.dandel.gameon.cli.fetch.param";

    public enum FetchType {
        team, league, country
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
            Weld weld = new Weld().addBeanClasses().enableDiscovery();
            WeldContainer container = weld.initialize();
            Runner runner = container.select(Runner.class).get();
            runner.start();
            return 0;
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);
            return -1;
        }
    }

    public static class Runner {
        private final Logger LOGGER = LoggerFactory.getLogger(getClass());

        @Inject
        private FetchDataFromApi1Service service;

        @Inject
        private EntityManager entityManager;

        @Inject
        @ConfigProperty(name = TYPE)
        private FetchType type;

        @Inject
        @ConfigProperty(name = PARAM_PREFIX + ".countrycode")
        private String countryCode;

        public void start() {
            entityManager.getTransaction().begin();
            try {
                callService();
                entityManager.getTransaction().commit();
            } catch (Throwable t) {
                entityManager.getTransaction().rollback();
                throw t;
            }
        }

        private void callService() {
            LOGGER.debug("Do some good using service {}", service);
            switch (type) {
                case team:
                    service.fetchAndSaveTeams(countryCode);
                    break;
                case league:
                    service.fetchAndSaveLeagues(countryCode);
                    break;
                case country:
                    service.fetchAndSaveCountries();
                    break;
                default:
                    throw new GameonRuntimeException("Unsupported type %s", type);
            }
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
