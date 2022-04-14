package se.dandel.gameon.adapter.out.rest.api2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.adapter.EnvironmentConfig;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

public class JsonApi2Controller {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private EnvironmentConfig environmentConfig;

    @Inject
    private JsonApi2Parser parser;

    public AuthResponseDTO connect(String clientId, String clientSecret) {

        WebTarget target = getBaseTarget().path("/oauth2/token");
        LOGGER.debug("Connecting to {}", target);
        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON);
        Entity<Form> entity = Entity.entity(createAuthForm(clientId, clientSecret),
                MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        Response response = request.post(entity);
        String json = response.readEntity(String.class);
        AuthResponseDTO authResponseDTO = parser.parseAuth(json);
        LOGGER.debug("Response status: {}", response.getStatus());
        LOGGER.debug("Response entity string: {}", json);
        return authResponseDTO;
    }

    public Collection<GameDTO> getGamesInSeason(String accessToken, String season) {
        WebTarget target = getBaseTarget().path("/seasons/" + season + "/games");
        LOGGER.debug("Connecting to {}", target);
        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + accessToken);
        Response response = request.get();
        LOGGER.debug("Response status: {}", response.getStatus());

        String json = response.readEntity(String.class);
        LOGGER.debug("Response entity string: {}", json);
        return parser.parseGamesInSeason(json);
    }

    private WebTarget getBaseTarget() {
        return ClientBuilder.newClient().target(environmentConfig.getApi2BaseUrl());
    }

    private Form createAuthForm(String clientId, String clientSecret) {
        Form form = new Form();
        form.param("client_id", clientId);
        form.param("client_secret", clientSecret);
        form.param("grant_type", "client_credentials");
        return form;
    }

}
