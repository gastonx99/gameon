package se.dandel.gameon.adapter.out.rest.api2;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.Collection;

class JsonApi2ControllerMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonApi2ControllerMain.class);

    private static JsonApi2Controller controller = new JsonApi2Controller();

    public static class Connect {
        public static void main(String[] args) {
            String clientId = JOptionPane.showInputDialog("Client id:");
            String clientSecret = JOptionPane.showInputDialog("Client secret:");
            if (StringUtils.isNoneBlank(clientId, clientSecret)) {
                AuthResponseDTO authResponseDTO = controller.connect(clientId, clientSecret);
                LOGGER.debug("AuthResponseDTO: {}", authResponseDTO);
                System.out.println("AuthResponseDTO: " + authResponseDTO);
            }
            LOGGER.debug("Exiting");
        }
    }

    public static class GamesInSeason {
        public static void main(String[] args) {
            String accessToken = JOptionPane.showInputDialog("Access token:");
            if (StringUtils.isNotBlank(accessToken)) {
                String season = "2020";
                Collection<GameDTO> gamesInSeason = controller.getGamesInSeason(accessToken, season);
                LOGGER.debug("Games in season {}: {}", season, gamesInSeason);
            }
            LOGGER.debug("Exiting");
        }
    }
}