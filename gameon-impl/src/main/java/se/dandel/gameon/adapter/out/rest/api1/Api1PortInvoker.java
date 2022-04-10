package se.dandel.gameon.adapter.out.rest.api1;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.adapter.EnvironmentConfig;
import se.dandel.gameon.domain.GameonRuntimeException;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.config.PropertyNamingStrategy;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class Api1PortInvoker {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final JsonbConfig JSONB_CONFIG = new JsonbConfig().withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);

    private static final Jsonb jsonb = JsonbBuilder.create(JSONB_CONFIG);

    @Inject
    private EnvironmentConfig environmentConfig;

    <T> Collection<T> invoke(Type envelopeClazz, String path, Map<String, String> queryParams) {
        String json = invoke(path, queryParams);
        ParameterizedType parameterizedType = ParameterizedType.class.cast(envelopeClazz);
        ParameterizedType actualTypeArgument = ParameterizedType.class.cast(parameterizedType.getActualTypeArguments()[0]);
        if (actualTypeArgument.getRawType() == Map.class) {
            return (Collection<T>) getMapData(json, envelopeClazz).values();
        } else {
            return getCollectionData(json, envelopeClazz);
        }
    }

    private <R, T> Map<R, T> getMapData(String json, Type envelopeClazz) {
        return ((EnvelopeDTO<Map<R, T>>) jsonb.fromJson(json, envelopeClazz)).getData();
    }

    private <T> Collection<T> getCollectionData(String json, Type envelopeClazz) {
        return ((EnvelopeDTO<Collection<T>>) jsonb.fromJson(json, envelopeClazz)).getData();
    }

    private String invoke(String path, Map<String, String> queryParams) {
        WebTarget target = getBaseTarget().path(path);
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            target = target.queryParam(entry.getKey(), entry.getValue());
        }
        LOGGER.debug("Connecting to {}", target);
        Invocation.Builder request = target
                .request(MediaType.APPLICATION_JSON)
                .header("apikey", environmentConfig.getAPi1Apikey());
        Response response = request.get();
        LOGGER.debug("Response status: {}", response.getStatus());
        if (response.getStatus() != 200) {
            throw new GameonRuntimeException("Unable to connect to target %s, got response status %d", target, response.getStatus());
        }
        String json = response.readEntity(String.class);
        LOGGER.debug("Response JSON: {}", StringUtils.abbreviate(json, 500));
        return json;
    }

    private WebTarget getBaseTarget() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.HEADERS_ONLY);
        return ClientBuilder.newClient(clientConfig).target(environmentConfig.getApi1BaseUrl());
    }

}
