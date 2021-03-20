package se.dandel.gameon.adapter.out.rest.api2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import java.util.ArrayList;
import java.util.Collection;

public class JsonApi2Parser {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final JsonbConfig JSONB_CONFIG = new JsonbConfig().withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);

    private static final Jsonb jsonb = JsonbBuilder.create(JSONB_CONFIG);

    public Collection<GameDTO> parseGamesInSeason(String json) {
        Collection<GameDTO> result = jsonb.fromJson(json, new ArrayList<GameDTO>() {

        }.getClass().getGenericSuperclass());
        LOGGER.debug("Parsed {} elements", result.size());
        return result;
    }

    public AuthResponseDTO parseAuth(String json) {
        LOGGER.debug("Parsing auth: {}", json);
        AuthResponseDTO result = jsonb.fromJson(json, AuthResponseDTO.class);
        LOGGER.debug("Parsed auth: {}", result);
        return result;
    }
}
